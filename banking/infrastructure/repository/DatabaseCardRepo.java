package banking.infrastructure.repository;

import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import banking.core.domain.model.Card;
import banking.core.domain.repository.CardRepo;

public class DatabaseCardRepo implements CardRepo {
    private final String url;
    private SQLiteDataSource dataSource;

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS card(" +
            "id INTEGER PRIMARY KEY, " +
            "number TEXT NOT NULL, " +
            "pin TEXT NOT NULL, " +
            "balance INTEGER DEFAULT 0);";
    private static final String SELECT_CARD_QUERY = "SELECT * FROM card WHERE number = ?;";
    private static final String SELECT_ALL_CARDS_QUERY = "SELECT * FROM card;";
    private static final String INSERT_CARD_QUERY = "INSERT INTO card (id, number, pin, balance) VALUES (?, ?, ?, ?);";
    private static final String ADD_BALANCE_QUERY = "UPDATE card SET balance = balance + ? WHERE number = ?";
    private static final String SUBTRACT_BALANCE_QUERY = "UPDATE card SET balance = balance - ? WHERE number = ?";
    private static final String DELETE_CARD_QUERY = "DELETE FROM card WHERE number = ?";

    public DatabaseCardRepo(String dbFileName) {
        this.url = "jdbc:sqlite:" + dbFileName;
        this.dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
    }

    public void initiateDb() {
        try (Connection connection = this.dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_QUERY);
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void saveCard(Card card) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CARD_QUERY)) {
            statement.setInt(1, card.getId());
            statement.setString(2, card.getNumber());
            statement.setString(3, card.getPin());
            statement.setInt(4, card.getBalance());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public Card getCard(String cardNumber) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CARD_QUERY)) {
            statement.setString(1, cardNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getCardFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public Card[] getCards() {
        try (Connection connection = this.dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_CARDS_QUERY);

            List<Card> cards = new LinkedList<>();
            while (resultSet.next()) {
                cards.add(getCardFromResultSet(resultSet));
            }
            return cards.toArray(new Card[0]);

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void topUpBalance(String cardNumber, int income) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_BALANCE_QUERY)) {
            statement.setInt(1, income);
            statement.setString(2, cardNumber);
            int affectedRowsNum = statement.executeUpdate();
            if (affectedRowsNum == 0) {
                throw new DBException("No rows affected while updating card balance. Should not happen!");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void transfer(String fromCardNumber, String toCardNumber, int amount) {
        try (Connection connection = this.dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement subtractStatement = connection.prepareStatement(SUBTRACT_BALANCE_QUERY);
                 PreparedStatement addStatement = connection.prepareStatement(ADD_BALANCE_QUERY)) {
                subtractStatement.setInt(1, amount);
                subtractStatement.setString(2, fromCardNumber);
                int affected = subtractStatement.executeUpdate();
                if (affected == 0) {
                    throw new DBException("Sender not updated. Should not happen.");
                }

                addStatement.setInt(1, amount);
                addStatement.setString(2, toCardNumber);
                affected = addStatement.executeUpdate();
                if (affected == 0) {
                    throw new DBException("Receiver not updated. Should not happen.");
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DBException("Transfer failed, rolled back. Reason: " + e.getMessage());
            }

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void deleteCard(String cardNumber) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CARD_QUERY)) {
            statement.setString(1, cardNumber);
            int affectedRowsNum = statement.executeUpdate();

            if (affectedRowsNum == 0) {
                throw new DBException("No rows affected when trying to delete card. Should not happen!");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    private static Card getCardFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String number = resultSet.getString("number");
        String pin = resultSet.getString("pin");
        int balance = resultSet.getInt("balance");
        return new Card(id, number, pin, balance);
    }
}

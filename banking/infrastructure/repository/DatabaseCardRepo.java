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

    public DatabaseCardRepo(String dbFileName) {
        this.url = "jdbc:sqlite:" + dbFileName;
        this.dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
    }

    public void initiateDb() {
        try (Connection connection = this.dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS card(" +
                    "id INTEGER PRIMARY KEY, " +
                    "number TEXT NOT NULL, " +
                    "pin TEXT NOT NULL, " +
                    "balance INTEGER DEFAULT 0);";
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void saveCard(Card card) {
        String query = "INSERT INTO card (id, number, pin, balance) VALUES (?, ?, ?, ?)";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
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
        String query = "SELECT * FROM card WHERE number = ?";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
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
        String query = "SELECT * FROM card;";
        try (Connection connection = this.dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            List<Card> cards = new LinkedList<>();
            while (resultSet.next()) {
                cards.add(getCardFromResultSet(resultSet));
            }
            return cards.toArray(new Card[0]);

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

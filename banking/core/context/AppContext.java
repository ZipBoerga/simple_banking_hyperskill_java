package banking.core.context;

import banking.core.service.BankingService;
import banking.domain.model.Card;

public class AppContext {
    final private BankingService bankingService;
    private boolean loggedIn;
    private Card currentCard;
    private String loginCardNumber;
    private String loginCardPin;

    public AppContext(final BankingService bankingService) {
        this.bankingService = bankingService;
        this.loggedIn = false;
    }

    public void setLoginCardNumber(String loginCardNumber) {
        this.loginCardNumber = loginCardNumber;
    }

    public void setLoginCardPin(String loginCardPIN) {
        this.loginCardPin = loginCardPIN;
    }

    public Card createCard() {
        return bankingService.createCard();
    }

    public int getBalance() {
        return currentCard.getBalance();
    }

    public void login() throws Exception {
        if (this.loginCardNumber == null || this.loginCardPin == null) {
            throw new Exception("Login or PIN empty in login moment. Should not happen.");
        }
        Card result = bankingService.login(this.loginCardNumber, this.loginCardPin);
        System.out.println(result);
        if (result == null) {
            throw new FailedLoginException();
        }
        this.loginCardNumber = null;
        this.loginCardPin = null;
        this.currentCard = result;
        this.loggedIn = true;
    }

    public void logout() {
        this.currentCard = null;
        this.loggedIn = false;
    }

}

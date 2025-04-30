package banking.core.application.context;

import banking.core.application.service.BankingService;
import banking.core.domain.model.Card;
import banking.presentation.states.ContextException;

public class AppContext {
    final private BankingService bankingService;
    private Card currentCard;
    private String loginCardNumber;
    private String loginCardPin;
    private String transferToCardNumber;

    public AppContext(final BankingService bankingService) {
        this.bankingService = bankingService;
    }

    public void setLoginCardNumber(String loginCardNumber) {
        this.loginCardNumber = loginCardNumber;
    }

    public void setLoginCardPin(String loginCardPIN) {
        this.loginCardPin = loginCardPIN;
    }

    public void setTransferToCardNumber(String transferToCardNumber) throws ContextException {
        if (currentCard.getNumber().equals(transferToCardNumber)) {
            throw new ContextException("You can't transfer money to the same account!");
        }
        if (!this.bankingService.isValidCard(transferToCardNumber)) {
            throw new ContextException("Probably you made a mistake in the card number. Please try again!");
        }
        if (!this.bankingService.isCardExist(transferToCardNumber)) {
            throw new ContextException("Such a card does not exist.");
        }
        this.transferToCardNumber = transferToCardNumber;
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
    }

    public void logout() {
        this.currentCard = null;
    }

    public void topUpBalance(int amount) {
        this.currentCard = this.bankingService.addIncome(currentCard.getNumber(), amount);
    }

    public void transfer(int amount) throws ContextException {
        if (amount > this.currentCard.getBalance()) {
            this.transferToCardNumber = null;
            throw new ContextException("Not enough money!");
        }
        this.currentCard = bankingService.transfer(currentCard.getNumber(), this.transferToCardNumber, amount);
        this.transferToCardNumber = null;
    }

    public void deleteCard() {
        this.bankingService.deleteCard(this.currentCard.getNumber());
        this.currentCard = null;
    }
}

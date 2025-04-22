package banking.interfaces.states;

import banking.core.context.AppContext;
import banking.domain.model.Card;

public final class MenuState extends AppState {

    final AppContext appContext;

    public MenuState(AppContext clientContext) {
        this.appContext = clientContext;
    }

    @Override
    public String prompt() {
        return "1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit";
    }

    @Override
    public StateHandlingResult handleInput(String input) throws ExitApp {
        switch (input) {
            case "1":
                Card card = this.appContext.createCard();
                String output = "Your card has been created\n" +
                        "Your card number:\n" +
                        card.getCardNumber() +
                        "\nYour card PIN:\n" +
                        card.getCardPin();
                return new StateHandlingResult(new MenuState(appContext), output);
            case "2":
                return new StateHandlingResult(new CardNumberInputState(appContext), null);
            case "0":
                throw new ExitApp();
            default:
                return new StateHandlingResult(new MenuState(appContext), "Wrong input. Should not happen");
        }
    }
}

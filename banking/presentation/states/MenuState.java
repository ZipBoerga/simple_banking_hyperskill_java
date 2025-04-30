package banking.presentation.states;

import banking.core.application.context.AppContext;
import banking.core.domain.model.Card;

public final class MenuState extends AppState {

    final AppContext appContext;

    public MenuState(AppContext clientContext) {
        this.appContext = clientContext;
    }

    @Override
    public String prompt() {
        return """
                1. Create an account
                2. Log into account
                0. Exit""";
    }

    @Override
    public StateHandlingResult handleInput(String input) throws ExitApp {
        switch (input) {
            case "1":
                Card card = this.appContext.createCard();
                String output = "Your card has been created\n" +
                        "Your card number:\n" +
                        card.getNumber() +
                        "\nYour card PIN:\n" +
                        card.getPin();
                return new StateHandlingResult(new MenuState(appContext), output);
            case "2":
                return new StateHandlingResult(new LoginCardNumInputState(appContext), null);
            case "0":
                throw new ExitApp();
            default:
                return new StateHandlingResult(new MenuState(appContext), "Wrong input. Should not happen");
        }
    }
}

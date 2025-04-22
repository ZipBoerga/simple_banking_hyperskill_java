package banking.interfaces.states;

import banking.core.context.AppContext;

public class CardNumberInputState extends AppState {
    final AppContext appContext;

    public CardNumberInputState(AppContext clientContext) {
        this.appContext = clientContext;
    }

    @Override
    public String prompt() {
        return "Enter your card number:";
    }

    @Override
    public StateHandlingResult handleInput(String input) {
        appContext.setLoginCardNumber(input.trim());
        return new StateHandlingResult(new CardPinInputState(appContext), null);
    }
}

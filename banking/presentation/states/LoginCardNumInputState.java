package banking.presentation.states;

import banking.core.application.context.AppContext;

public class LoginCardNumInputState extends AppState {
    final AppContext appContext;

    public LoginCardNumInputState(AppContext clientContext) {
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

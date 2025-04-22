package banking.interfaces.states;

import banking.core.context.AppContext;
import banking.core.context.FailedLoginException;

public class CardPinInputState extends AppState {
    final AppContext appContext;

    public CardPinInputState(AppContext clientContext) {
        this.appContext = clientContext;
    }

    @Override
    public String prompt() {
        return "Enter your card number:";
    }

    @Override
    public StateHandlingResult handleInput(String input) {
        appContext.setLoginCardPin(input.trim());
        try {
            appContext.login();
        } catch (FailedLoginException e) {
            return new StateHandlingResult(new MenuState(appContext), e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new StateHandlingResult(new LoggedInState(appContext), "You have successfully logged in!");
    }
}

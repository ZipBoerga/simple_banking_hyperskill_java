package banking.presentation.states;

import banking.core.application.context.AppContext;
import banking.core.application.context.FailedLoginException;

public class CardPinInputState extends AppState {
    final AppContext appContext;

    public CardPinInputState(AppContext clientContext) {
        this.appContext = clientContext;
    }

    @Override
    public String prompt() {
        return "Enter your PIN:";
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
        return new StateHandlingResult(new AccountMenuState(appContext), "You have successfully logged in!");
    }
}

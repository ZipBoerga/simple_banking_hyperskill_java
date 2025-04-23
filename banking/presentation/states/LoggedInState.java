package banking.presentation.states;

import banking.core.application.context.AppContext;

public class LoggedInState extends AppState {
    final AppContext appContext;

    public LoggedInState(AppContext clientContext) {
        this.appContext = clientContext;
    }

    @Override
    public String prompt() {
        return "1. Balance\n" +
                "2. Log out\n" +
                "0. Exit";
    }

    @Override
    public StateHandlingResult handleInput(String input) throws ExitApp {
        switch (input) {
            case "1":
                int balance = appContext.getBalance();
                return new StateHandlingResult(new LoggedInState(appContext), "Balance: " + balance);
            case "2":
                appContext.logout();
                return new StateHandlingResult(new MenuState(appContext), "You have successfully logged out!");
            case "0":
                throw new ExitApp();
            default:
                return new StateHandlingResult(new MenuState(appContext), "Wrong input. Should not happen");
        }
    }
}

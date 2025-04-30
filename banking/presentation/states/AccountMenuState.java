package banking.presentation.states;

import banking.core.application.context.AppContext;

public class AccountMenuState extends AppState {
    final AppContext appContext;

    public AccountMenuState(AppContext clientContext) {
        this.appContext = clientContext;
    }

    @Override
    public String prompt() {
        return """
                1. Balance
                2. Add income
                3. Do transfer
                4. Close account
                5. Log out
                0. Exit""";
    }

    @Override
    public StateHandlingResult handleInput(String input) throws ExitApp {
        return switch (input) {
            case "1" -> {
                int balance = appContext.getBalance();
                yield new StateHandlingResult(new AccountMenuState(appContext), "Balance: " + balance);
            }
            case "2" -> new StateHandlingResult(new TopUpInputState(appContext), null);
            case "3" -> new StateHandlingResult(new TransferCardNumInputState(appContext), null);
            case "4" -> {
                appContext.deleteCard();
                yield new StateHandlingResult(new MenuState(appContext), "Account is successfully closed.");
            }
            case "5" -> {
                appContext.logout();
                yield new StateHandlingResult(new MenuState(appContext), "You have successfully logged out!");
            }
            case "0" -> throw new ExitApp();
            default -> new StateHandlingResult(new AccountMenuState(appContext), "Wrong input. Should not happen");
        };
    }
}

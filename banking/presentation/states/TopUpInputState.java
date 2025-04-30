package banking.presentation.states;

import banking.core.application.context.AppContext;

public class TopUpInputState extends AppState {
    final AppContext appContext;

    public TopUpInputState(AppContext clientContext) {
        this.appContext = clientContext;
    }

    @Override
    public String prompt() {
        return "Enter income:";
    }

    @Override
    public StateHandlingResult handleInput(String input) {
        int amount;
        try {
            amount = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return new StateHandlingResult(new AccountMenuState(appContext),
                    "Incorrect number format, must be a natural number!");
        }
        appContext.topUpBalance(amount);
        return new StateHandlingResult(new AccountMenuState(appContext), "Income was added!");
    }
}

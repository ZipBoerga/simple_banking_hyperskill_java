package banking.presentation.states;

import banking.core.application.context.AppContext;

public class TransferAmountInputState extends AppState {
    final AppContext appContext;

    public TransferAmountInputState(AppContext clientContext) {
        this.appContext = clientContext;
    }

    @Override
    public String prompt() {
        return "Enter how much money you want to transfer:";
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
        try {
            appContext.transfer(amount);
        } catch (ContextException e) {
            return new StateHandlingResult(new AccountMenuState(appContext), e.getMessage());
        }
        return new StateHandlingResult(new AccountMenuState(appContext), "Success!");
    }
}

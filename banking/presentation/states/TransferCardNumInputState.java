package banking.presentation.states;

import banking.core.application.context.AppContext;

public class TransferCardNumInputState extends AppState {
    final AppContext appContext;

    public TransferCardNumInputState(AppContext clientContext) {
        this.appContext = clientContext;
    }

    @Override
    public String prompt() {
        return """
                Transfer
                Enter card number:""";
    }

    @Override
    public StateHandlingResult handleInput(String input) {
        try {
            appContext.setTransferToCardNumber(input.trim());
        } catch (ContextException e) {
            return new StateHandlingResult(new AccountMenuState(appContext), e.getMessage());
        }
        return new StateHandlingResult(new TransferAmountInputState(appContext), null);
    }
}

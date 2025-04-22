package banking.interfaces.states;

public class StateHandlingResult {
    public final AppState nextState;
    public final String output;

    public StateHandlingResult(AppState nextState, String output) {
        this.nextState = nextState;
        this.output = output;
    }
}

package banking.interfaces.states;

public abstract class AppState {
    public abstract String prompt();
    public abstract StateHandlingResult handleInput(String input) throws ExitApp;
}

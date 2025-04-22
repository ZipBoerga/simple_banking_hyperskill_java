package banking.interfaces;

import banking.core.context.AppContext;
import banking.interfaces.states.AppState;
import banking.interfaces.states.ExitApp;
import banking.interfaces.states.MenuState;
import banking.interfaces.states.StateHandlingResult;

import java.util.Scanner;

public class UserCLI {
    private AppState appState;
    private final Scanner scanner;

    public UserCLI(AppContext appContext) {
        appState = new MenuState(appContext);
        scanner = new Scanner(System.in);
    }

    public void startCLI() {
        while (true) {
            try {
                String prompt = appState.prompt();
                if (prompt!= null) System.out.println("\n" + prompt);

                StateHandlingResult result = appState.handleInput(scanner.nextLine());

                String output = result.output;
                if (output!= null) System.out.println("\n" + output);
                this.appState = result.nextState;
            } catch (ExitApp e) {
                System.out.println("\nBye!");
                break;
            }
        }
    }
}

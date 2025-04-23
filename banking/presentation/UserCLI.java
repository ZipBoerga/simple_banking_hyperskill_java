package banking.presentation;

import banking.core.application.context.AppContext;
import banking.presentation.states.AppState;
import banking.presentation.states.ExitApp;
import banking.presentation.states.MenuState;
import banking.presentation.states.StateHandlingResult;

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

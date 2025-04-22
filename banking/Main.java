package banking;

import banking.core.context.AppContext;
import banking.core.service.BankingService;
import banking.core.service.BankingServiceImpl;
import banking.domain.repository.CardRepo;
import banking.infrastracture.repository.FakeCardRepo;
import banking.interfaces.UserCLI;
import banking.interfaces.states.StateHandlingResult;

public class Main {
    public static void main(String[] args) {
        CardRepo cardRepo = new FakeCardRepo();
        BankingService bankingService = new BankingServiceImpl(cardRepo);
        AppContext appContext = new AppContext(bankingService);
        UserCLI cli = new UserCLI(appContext);

        cli.startCLI();
    }
}
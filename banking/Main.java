package banking;

import banking.core.application.context.AppContext;
import banking.core.application.service.BankingService;
import banking.core.application.service.BankingServiceImpl;
import banking.core.domain.repository.CardRepo;
import banking.infrastructure.repository.FakeCardRepo;
import banking.presentation.UserCLI;

public class Main {
    public static void main(String[] args) {
        CardRepo cardRepo = new FakeCardRepo();
        BankingService bankingService = new BankingServiceImpl(cardRepo);
        AppContext appContext = new AppContext(bankingService);
        UserCLI cli = new UserCLI(appContext);

        cli.startCLI();
    }
}
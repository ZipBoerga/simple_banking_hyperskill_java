package banking;

import banking.core.application.context.AppContext;
import banking.core.application.service.BankingService;
import banking.core.application.service.CardGeneratorService;
import banking.infrastructure.repository.DatabaseCardRepo;
import banking.presentation.UserCLI;

public class Main {
    public static void main(String[] args) {
        String filePath = args[1];
        DatabaseCardRepo cardRepo = new DatabaseCardRepo(filePath);
        cardRepo.initiateDb();

        CardGeneratorService cardGeneratorService = new CardGeneratorService(cardRepo);
        BankingService bankingService = new BankingService(cardRepo, cardGeneratorService);
        AppContext appContext = new AppContext(bankingService);
        UserCLI cli = new UserCLI(appContext);

        cli.startCLI();
    }
}
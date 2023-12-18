package bank.app.teller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bank.app.teller.model.Transaction;
import bank.app.teller.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Transactional
    public String createTransaction(Transaction transaction) {
        try {
            switch (transaction.getType()) {
                case "DEPOSIT":
                    accountService.deposit(transaction.getReceivingAccount().getId(), transaction.getAmount());
                    break;
                case "WITHDRAWAL":
                    accountService.withdraw(transaction.getSendingAccount().getId(), transaction.getAmount());
                    break;
                case "TRANSFER":
                    accountService.transfer(transaction.getSendingAccount().getId(), transaction.getReceivingAccount().getId(), transaction.getAmount());
                    break;
            }
            transactionRepository.save(transaction);
            return "Transaction created successfully";
        } catch (Exception e) {
            return "Transaction creation failed: " + e.getMessage();
        }
    }


}

package bank.app.teller.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bank.app.teller.model.Account;
import bank.app.teller.model.Customer;
import bank.app.teller.repository.AccountRepository;
import bank.app.teller.repository.CustomerRepository;

@Service
public class AccountService {	
	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;
	
	@Autowired
	public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
		super();
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
	}
	
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}
	
	public String addAccount(Account account) {
	    Customer customer = account.getCustomer();
	    if (customer == null || customer.getId() == null) {
	        return "Customer ID must not be null" + account.getClass();
	    }

	    Long customerId = customer.getId();
	    Optional<Customer> customerOpt = customerRepository.findById(customerId);

	    if (!customerOpt.isPresent()) {
	        return "Customer does not exist";
	    }

	    account.setCustomer(customerOpt.get());

	    try {
	        accountRepository.save(account);
	        return "Account created successfully";
	    } catch (Exception e) {
	        return "Error creating account: " + e.getMessage();
	    }
	}

	public String deleteAccount(Long id) {
	    Optional<Account> accountOpt = accountRepository.findById(id);
	    if (!accountOpt.isPresent()) {
	        return "Account does not exist";
	    }

	    accountRepository.deleteById(id);
	    return "Account deleted successfully";
	}
	
	public List<Account> getRelativeAccounts(Long customerId) {
        return accountRepository.findByCustomerIdOrderByBalanceDesc(customerId);
	}

	@Transactional
    public String deposit(Long accountId, BigDecimal amount) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (!accountOpt.isPresent()) {
            return "Account does not exist";
        }
        Account account = accountOpt.get();
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        return "Deposit successful";
    }

    @Transactional
    public String withdraw(Long accountId, BigDecimal amount) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (!accountOpt.isPresent()) {
            return "Account does not exist";
        }
        Account account = accountOpt.get();
        if (account.getBalance().compareTo(amount) < 0) {
            return "Insufficient funds";
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        return "Withdrawal successful";
    }

    @Transactional
    public String transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        try {
            withdraw(fromAccountId, amount);
            deposit(toAccountId, amount);
            return "Transfer successful";
        } catch (Exception e) {
            return "Transfer failed: " + e.getMessage();
        }
    }


}

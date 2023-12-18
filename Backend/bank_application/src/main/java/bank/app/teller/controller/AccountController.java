package bank.app.teller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bank.app.teller.model.Account;
import bank.app.teller.model.Customer;
import bank.app.teller.service.AccountService;
import bank.app.teller.repository.CustomerRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    private final AccountService accountService;
    private final CustomerRepository customerRepository;

    @Autowired
    public AccountController(AccountService accountService, CustomerRepository customerRepository) {
        this.accountService = accountService;
		this.customerRepository = customerRepository;
    }

    @GetMapping("/all")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addAccount(@RequestBody Map<String, Object> accountData) {
        Long customerId = Long.valueOf((Integer) accountData.get("customerId"));
        String accountType = (String) accountData.get("accountType");
        String accountNumber = (String) accountData.get("accountNumber");
        BigDecimal balance = BigDecimal.valueOf((Double) accountData.get("balance"));


        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (!customerOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer does not exist");
        }

        Account account = new Account(customerOpt.get(), accountType, accountNumber, balance);
        String result = accountService.addAccount(account);

        if (result.equals("Account created successfully")) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        String result = accountService.deleteAccount(id);
        if (result.equals("Account deleted successfully")) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }
    
    @GetMapping("/relative/{customerId}")
    public ResponseEntity<List<Account>> getRelativeAccounts(@PathVariable Long customerId) {
        List<Account> accounts = accountService.getRelativeAccounts(customerId);
        return ResponseEntity.ok().body(accounts);
    }
    
    @PostMapping("/{id}/deposit")
    public ResponseEntity<String> deposit(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        BigDecimal amount = new BigDecimal(String.valueOf(requestBody.get("amount")));
        String result = accountService.deposit(id, amount);
        if (result.equals("Deposit successful")) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        BigDecimal amount = new BigDecimal(String.valueOf(requestBody.get("amount")));
        String result = accountService.withdraw(id, amount);
        if (result.equals("Withdrawal successful")) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @PostMapping("/{fromId}/transfer/{toId}")
    public ResponseEntity<String> transfer(
            @PathVariable Long fromId,
            @PathVariable Long toId,
            @RequestBody Map<String, Object> requestBody
    ) {
        BigDecimal amount = new BigDecimal(String.valueOf(requestBody.get("amount")));
        String result = accountService.transfer(fromId, toId, amount);
        if (result.equals("Transfer successful")) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }


}

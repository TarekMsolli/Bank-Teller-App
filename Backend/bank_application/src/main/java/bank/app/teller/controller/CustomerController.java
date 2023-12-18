package bank.app.teller.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank.app.teller.model.Customer;
import bank.app.teller.service.CustomerService;

@RestController
@RequestMapping(path = "/api/v1/customer")
public class CustomerController {
	@GetMapping("/hello")
	public String hello() {
		return "Hello, customer";
	}
	
	private final CustomerService customerService;
	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
		}
	
	@GetMapping("/all" )
	public List<Customer> getCustomers()
	{
	return customerService.getCustomers();
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
	    customerService.addCustomer(customer);
	    return ResponseEntity.ok().body("Customer Created Successfully with ID: " + customer.getId());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
	    Customer customer = customerService.getCustomer(id);
	    if (customer == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(customer);
	}
	
	@PostMapping("/lookup")
	public List<Customer> lookupCustomer(@RequestBody Map<String, String> json) {
	    String filter = json.get("filter");
	    return customerService.searchCustomers(filter);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
	    boolean isRemoved = customerService.deleteCustomer(id);
	    if (!isRemoved) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with ID " + id + " not found");
	    }
	    return ResponseEntity.ok().body("Customer with ID " + id + " deleted successfully");
	}

	@PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        String result = customerService.updateCustomer(id, updatedCustomer);
        if (result.equals("Customer updated successfully")) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

}

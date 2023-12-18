package bank.app.teller.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bank.app.teller.model.Customer;
import bank.app.teller.repository.CustomerRepository;

@Service
public class CustomerService {	
	private final CustomerRepository customerRepository;
	@Autowired
	public CustomerService(CustomerRepository custumorRepository) {
		super();
		this.customerRepository = custumorRepository;
	}
	
	public List<Customer> getCustomers()
	{
		return customerRepository.findAll();
	}

	public void addCustomer(Customer customer) {
		customerRepository.save(customer);
		
	}
	
	public Customer getCustomer(Long id) {
	    return customerRepository.findById(id).orElse(null);
	}
	
	public List<Customer> searchCustomers(String filter) {
	    return customerRepository.search(filter);
	}

	public boolean deleteCustomer(Long id) {
	    if (customerRepository.existsById(id)) {
	    	customerRepository.deleteById(id);
	        return true;
	    } else {
	        return false;
	    }
	}
	
	public String updateCustomer(Long id, Customer updatedCustomer) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (!customerOpt.isPresent()) {
            return "Customer does not exist";
        }

        Customer customer = customerOpt.get();
        customer.setFirstName(updatedCustomer.getFirstName());
        customer.setLastName(updatedCustomer.getLastName());
        customer.setAddress(updatedCustomer.getAddress());
        customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
        customer.setEmail(updatedCustomer.getEmail());

        try {
            customerRepository.save(customer);
            return "Customer updated successfully";
        } catch (Exception e) {
            return "Error updating customer: " + e.getMessage();
        }
    }


}

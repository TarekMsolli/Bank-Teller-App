package bank.app.teller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bank.app.teller.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	@Query("SELECT c FROM Customer c WHERE " +
	           "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
	           "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
	           "LOWER(c.address) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
	           "LOWER(c.phoneNumber) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
	           "LOWER(c.email) LIKE LOWER(CONCAT('%', :filter, '%'))")
	    List<Customer> search(@Param("filter") String filter);
}

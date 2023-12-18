package bank.app.teller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bank.app.teller.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}


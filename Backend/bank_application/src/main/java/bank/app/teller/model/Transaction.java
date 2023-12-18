package bank.app.teller.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private BigDecimal amount;

    private String type;

    @ManyToOne
    private Account sendingAccount;

    @ManyToOne
    private Account receivingAccount;

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Account getSendingAccount() {
		return sendingAccount;
	}

	public void setSendingAccount(Account sendingAccount) {
		this.sendingAccount = sendingAccount;
	}

	public Account getReceivingAccount() {
		return receivingAccount;
	}

	public void setReceivingAccount(Account receivingAccount) {
		this.receivingAccount = receivingAccount;
	}

	public Long getId() {
		return id;
	}

    
}


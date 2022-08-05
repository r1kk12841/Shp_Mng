package domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "invoice")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "create_date")
	private Timestamp createdDate;
	
	@ManyToOne
	@JoinColumn(name = "createdby")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "type_id")
	private Type_Invoice type_Invoice;
	
	@OneToMany(mappedBy = "invoice")
	private List<Detail_Invoice> detail_Invoices = new ArrayList<Detail_Invoice>();

	public Invoice() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Type_Invoice getType_Invoice() {
		return type_Invoice;
	}

	public void setType_Invoice(Type_Invoice type_Invoice) {
		this.type_Invoice = type_Invoice;
	}

	public List<Detail_Invoice> getDetail_Invoices() {
		return detail_Invoices;
	}

	public void setDetail_Invoices(List<Detail_Invoice> detail_Invoices) {
		this.detail_Invoices = detail_Invoices;
	}
	
}

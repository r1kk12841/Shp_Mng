package domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "createddate")
	private Timestamp createdDate;
	
	@Column(name = "login")
	private int login;
	
	@Column(name = "active")
	private int active;
	
	@OneToOne(mappedBy = "account", fetch = FetchType.EAGER)
	private Employee employee;
	
	@OneToMany(mappedBy = "account")
	private List<Product> products = new ArrayList<Product>();

	@OneToMany(mappedBy = "account")
	private List<Invoice> invoices = new ArrayList<Invoice>();
	
	public Account() {
		
	}

	public Account(Long id) {
		this.id = id;
	}

	public Account(String username, String password, String role, Timestamp createdDate, int login) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.createdDate = createdDate;
		this.login = login;
	}


	public Account(Long id, String username, String password, String role, Timestamp createdDate, int login) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.createdDate = createdDate;
		this.login = login;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return  username ;
	}

	
	
	
}

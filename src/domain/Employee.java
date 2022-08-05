package domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	private String name;
	
	private Timestamp dob;
	
	private String address;
	
	private String phone;
	
	@Column(name = "image")
	private String linkImage;
	
	@Transient
	private ImageView imageView = new ImageView();
	
	@OneToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	private Account account;

	public Employee() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getDob() {
		return dob;
	}

	public void setDob(Timestamp dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLinkImage() {
		return linkImage;
	}

	public void setLinkImage(String linkImage) {
		this.linkImage = linkImage;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}	
	
	public ImageView getImageView() {
		imageView.setFitHeight(120);
		imageView.setFitWidth(120);
		return imageView;
	}

	public void setImageView(Image image) {
		this.imageView.setImage(image);
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", dob=" + dob + ", address=" + address + ", phone=" + phone
				+ ", linkImage=" + linkImage + ", imageView=" + imageView + ", account=" + account + "]";
	}
	
	
}

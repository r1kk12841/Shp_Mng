package domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	private String name;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "input_price")
	private Double input_Price;

	@Column(name = "output_price")
	private Double output_Price;

	@Column(name = "link_image")
	private String linkImage;

	@Column(name = "createddate")
	private Timestamp createdDate;

	@ManyToOne
	@JoinColumn(name = "createdby")
	private Account account;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "product")
	private List<Detail_Invoice> detail_Invoices = new ArrayList<Detail_Invoice>();

	@Transient
	private ImageView imageView = new ImageView();

	public Product() {

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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getInput_Price() {
		return input_Price;
	}

	public void setInput_Price(Double input_Price) {
		this.input_Price = input_Price;
	}

	public Double getOutput_Price() {
		return output_Price;
	}

	public void setOutput_Price(Double output_Price) {
		this.output_Price = output_Price;
	}

	public String getLinkImage() {
		return linkImage;
	}

	public void setLinkImage(String linkImage) {
		this.linkImage = linkImage;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Detail_Invoice> getDetail_Invoices() {
		return detail_Invoices;
	}

	public void setDetail_Invoices(List<Detail_Invoice> detail_Invoices) {
		this.detail_Invoices = detail_Invoices;
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
		return "Product [id=" + id + ", name=" + name + ", quantity=" + quantity + ", input_Price=" + input_Price
				+ ", output_Price=" + output_Price + "]";
	}

}

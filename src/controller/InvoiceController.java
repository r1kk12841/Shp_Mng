package controller;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import domain.Account;
import domain.Detail_Invoice;
import domain.Invoice;
import domain.Product;
import domain.Type_Invoice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import repository.InvoiceRepository;
import repository.ProductRepository;
import repository.TypeOfInvoiceRepository;
import scene.SwitchToAnotherScene;

public class InvoiceController implements Initializable {
	private ProductRepository productRepository = new ProductRepository();
	private InvoiceRepository invoiceRepository = new InvoiceRepository();
	private TypeOfInvoiceRepository typeOfInvoiceRepository = new TypeOfInvoiceRepository();
	
	
	private SwitchToAnotherScene sw = new SwitchToAnotherScene();
	private ObservableList<Product> allProduct = FXCollections.observableArrayList();
	private ObservableList<Invoice> listInvoice = FXCollections.observableArrayList();
	private ObservableList<Type_Invoice> Listtype_Invoice = FXCollections.observableArrayList();
	private List<Detail_Invoice> listDetail = new ArrayList<Detail_Invoice>();
	private Product product = null;
	

	@FXML
	void switchToAccount(MouseEvent event) {
		sw.switchToAccount(event);
	}

	@FXML
	void switchToCategory(MouseEvent event) {
		sw.switchToCategory(event);
	}

	@FXML
	void switchToHome(MouseEvent event) {
		sw.switchToHome(event);
	}

	@FXML
	void switchToInvoice(MouseEvent event) {
		sw.switchToInvoice(event);
	}

	@FXML
	void switchToLogin(MouseEvent event) {
		sw.switchToLogin(event);
	}

	@FXML
	void switchToProduct(MouseEvent event) {
		sw.switchToProduct(event);
	}

	@FXML
	void switchToStatitics(MouseEvent event) {
		sw.switchToStatitics(event);
	}

	@FXML
	private TableView<Invoice> invoice_table;

	@FXML
	private TableColumn<Invoice, Integer> invoice_id;

	@FXML
	private TableColumn<Invoice, Timestamp> invoice_createDate;

	@FXML
	private TableColumn<Invoice, Account> invoice_CreateBy;

	@FXML
	private ComboBox<Type_Invoice> choice_type_invoice;

	@FXML
	private DatePicker start;

	@FXML
	private DatePicker end;

	@FXML
	private TableView<Detail_Invoice> detail_table;

	@FXML
	private TableColumn<Detail_Invoice, Product> detail_name;

	@FXML
	private TableColumn<Detail_Invoice, Integer> detail_quan;

	@FXML
	private TableColumn<Detail_Invoice, Double> detail_output;

	@FXML
	private TableColumn<Detail_Invoice, Double> detail_input;

	@FXML
	private TableView<Product> product_Table;

	@FXML
	private TableColumn<Product, String> product_name;

	@FXML
	private TableColumn<Product, Double> product_output;

	@FXML
	private TableColumn<Product, Integer> product_quantity;

	@FXML
	private TextField txt_search;

	@FXML
	private TextField txt_quantity;

	@FXML
	private Label lb_totalAmount;

	@FXML
	private VBox listProduct;

	@FXML
	private Label lb_total_product;

	@FXML
	void Btn_Oke(ActionEvent event) {
		Detail_Invoice detail_Invoice = new Detail_Invoice();
		detail_Invoice.setProduct(product);
		detail_Invoice.setOutput_price(product.getOutput_Price());
		detail_Invoice.setQuantity(Integer.valueOf(txt_quantity.getText()));
		listDetail.add(detail_Invoice);

		HBox hBox = new HBox();
		Label proName = new Label(detail_Invoice.getProduct().getName());
		Label price = new Label(String.valueOf(detail_Invoice.getProduct().getOutput_Price()));
		Label quantity = new Label(txt_quantity.getText());
		hBox.getChildren().add(proName);
		hBox.getChildren().add(price);
		hBox.getChildren().add(quantity);
		hBox.setMargin(proName, new Insets(0,40,0,0));
		hBox.setMargin(price, new Insets(0,15,0,0));
		hBox.setMargin(quantity, new Insets(0,15,0,0));
		listProduct.getChildren().add(hBox);
		listProduct.setMargin(hBox, new Insets(15, 0, 0, 15));

		int totalProduct = Integer.valueOf(lb_total_product.getText()) + Integer.valueOf(txt_quantity.getText());
		lb_total_product.setText(String.valueOf(totalProduct));

		Double totalPrice = Double.valueOf(lb_totalAmount.getText())
				+ product.getOutput_Price() * Integer.valueOf(txt_quantity.getText());
		lb_totalAmount.setText(String.valueOf(totalPrice));
		
	}

	@FXML
	void Btn_Print(ActionEvent event) {
		Invoice invoice = new Invoice();
		invoice.setDetail_Invoices(listDetail);
		invoice.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		invoiceRepository.save(invoice);
	}

	@FXML
	void SearchProduct(KeyEvent event) {
		if (txt_search.getText().equals("")) {
			loadProductTable();
		} else {
			allProduct.clear();
			productRepository.findByName(txt_search.getText()).forEach(p -> {
				allProduct.add(p);
			});
			product_Table.setItems(allProduct);
			product_name.setCellValueFactory(new PropertyValueFactory<>("name"));
			product_output.setCellValueFactory(new PropertyValueFactory<>("output_Price"));
			product_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadProductTable();
		clickProductTable();
		loadAllTableInvoice();
		loadTypeInvoice();
		chooseTypeInvoice();
		clickInvoiceTable();
		
		HBox hBox = new HBox();
		Label proName = new Label("tên sản phẩm");
		Label price = new Label("giá");
		Label quantity = new Label("số lượng");
		hBox.getChildren().add(proName);
		hBox.getChildren().add(price);
		hBox.getChildren().add(quantity);
		hBox.setMargin(proName, new Insets(0,40,0,0));
		hBox.setMargin(price, new Insets(0,15,0,0));
		hBox.setMargin(quantity, new Insets(0,15,0,0));
		listProduct.getChildren().add(hBox);
		listProduct.setMargin(hBox, new Insets(15, 0, 0, 15));
	}

	public void loadProductTable() {
		allProduct.clear();
		productRepository.findAll().forEach(p -> {
			allProduct.add(p);
		});
		product_Table.setItems(allProduct);
		product_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		product_output.setCellValueFactory(new PropertyValueFactory<>("output_Price"));
		product_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
	}

	public void clickProductTable() {
		product_Table.setRowFactory(tv -> {
			TableRow<Product> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					product = row.getItem();
				}
			});
			return row;
		});
	}
	
	public void loadAllTableInvoice() {
		listInvoice.clear();
		invoiceRepository.findAll().forEach(p->{
			listInvoice.add(p);
		});
		invoice_table.setItems(listInvoice);
		invoice_CreateBy.setCellValueFactory(new PropertyValueFactory<>("account"));
		invoice_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		invoice_createDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
	}
	
	public void loadTypeInvoice() {
		typeOfInvoiceRepository.findAll().forEach(p->{
			Listtype_Invoice.add(p);
		});
		choice_type_invoice.setItems(Listtype_Invoice);
	}
	
	public void chooseTypeInvoice() {
		choice_type_invoice.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem == null) {
                
            } else {
        		listInvoice.clear();
        		choice_type_invoice.getValue().getInvoices().forEach(p->{
        			listInvoice.add(p);
        		});
        		invoice_table.setItems(listInvoice);
        		invoice_CreateBy.setCellValueFactory(new PropertyValueFactory<>("account"));
        		invoice_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        		invoice_createDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
            }
        });
	}
	
	public void clickInvoiceTable() {
		invoice_table.setRowFactory(tv -> {
			TableRow<Invoice> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					ObservableList<Detail_Invoice> listInvoiceDetail = FXCollections.observableArrayList();
					row.getItem().getDetail_Invoices().forEach(p->{
						listInvoiceDetail.add(p);
					});
					detail_table.setItems(listInvoiceDetail);
					detail_output.setCellValueFactory(new PropertyValueFactory<>("output_price"));
					detail_name.setCellValueFactory(new PropertyValueFactory<>("product"));
					detail_quan.setCellValueFactory(new PropertyValueFactory<>("quantity"));
				}
			});
			return row;
		});
	}
	
}

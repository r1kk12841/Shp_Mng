package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import domain.Account;
import domain.Category;
import domain.Invoice;
import domain.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import repository.CategoryRepository;
import repository.InvoiceRepository;
import repository.ProductRepository;
import scene.SwitchToAnotherScene;
import upload.FileUpload;

public class ProductController implements Initializable {

	private SwitchToAnotherScene sw = new SwitchToAnotherScene();

	private ProductRepository productRepository = new ProductRepository();
	
	private CategoryRepository categoryRepository = new CategoryRepository();
	
	private InvoiceRepository invoiceRepository = new InvoiceRepository();
	
	private FileUpload fileUpload = new FileUpload();

	private ObservableList<Category> allCategory = FXCollections.observableArrayList();
	
	private ObservableList<Product> allProduct = FXCollections.observableArrayList();
	
	private List<Product> listProduct = new ArrayList<Product>();
	
	private String linkAnh = null;
	
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
	private TableView<Product> mainTable;

	@FXML
	private TableColumn<Product, Integer> col_id;

	@FXML
	private TableColumn<Product, String> col_name;

	@FXML
	private TableColumn<Product, Category> col_category;

	@FXML
	private TableColumn<Product, Integer> col_quantity;

	@FXML
	private TableColumn<Product, Double> col_input;

	@FXML
	private TableColumn<Product, Double> col_output;

	@FXML
	private TableColumn<Product, Timestamp> col_date;

	@FXML
	private TableColumn<Product, Account> col_create_by;

	@FXML
	private TableColumn<Product, ImageView> col_image;

	@FXML
	private TextField txt_search;

	@FXML
	private TextField txt_name;

	@FXML
	private TextField txt_input;

	@FXML
	private TextField txt_output;

	@FXML
	private TextField txt_quantity;

	@FXML
	private ChoiceBox<Category> choice_Category;

	@FXML
	private ImageView image;

	@FXML
	void Btn_Add(ActionEvent event) {
		Invoice invoice = new Invoice();
		invoice.setAccount(LoginController.account);
		invoice.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		invoiceRepository.saveInvoiceInput(invoice, listProduct);
	}
	
	@FXML
    void Btn_AddProductToList(ActionEvent event) {
		linkAnh = fileUpload.upload(linkAnh);
		Product product = new Product();
		product.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		product.setInput_Price(Double.valueOf(txt_input.getText()));
		product.setLinkImage(linkAnh);
		product.setOutput_Price(Double.valueOf(txt_output.getText()));
		product.setName(txt_name.getText());
		product.setQuantity(Integer.valueOf(txt_quantity.getText()));
		product.setCategory(choice_Category.getValue());
		product.setAccount(LoginController.account);
		listProduct.add(product);
		linkAnh = null;
		image.setImage(null);
		
		
		
		VBox vBox = new VBox();
		vBox.setPrefHeight(500);
		vBox.setPrefWidth(500);
		HBox hBox = new HBox();
		vBox.getChildren().add(hBox);
		
		VBox vBoxName = new VBox();
		VBox vBoxInputPrice = new VBox();
		VBox vBoxOutputPrice = new VBox();
		VBox vBoxQuantity = new VBox();
		Label label = new Label("Tên sản phẩm");
		Label label1 = new Label("giá nhập");
		Label label2 = new Label("giá bán");
		Label label3 = new Label("số lượng");
		vBoxName.getChildren().add(label);
		vBoxInputPrice.getChildren().add(label1);
		vBoxOutputPrice.getChildren().add(label2);
		vBoxQuantity.getChildren().add(label3);
		for(Product p: listProduct) {
			Label label4 = new Label(p.getName());
			Label label5 = new Label(String.valueOf(p.getInput_Price()));
			Label label6 = new Label(String.valueOf(p.getOutput_Price()));
			Label label7 = new Label(String.valueOf(p.getQuantity()));
			vBoxName.getChildren().add(label4);
			vBoxInputPrice.getChildren().add(label5);
			vBoxOutputPrice.getChildren().add(label6);
			vBoxQuantity.getChildren().add(label7);
		}
		
		
		hBox.getChildren().add(vBoxName);
		hBox.getChildren().add(vBoxInputPrice);
		hBox.getChildren().add(vBoxOutputPrice);
		hBox.getChildren().add(vBoxQuantity);
		
		vBoxName.setPrefHeight(500);
		vBoxName.setPrefWidth(200);
		vBoxInputPrice.setPrefHeight(500);
		vBoxInputPrice.setPrefWidth(100);
		vBoxOutputPrice.setPrefHeight(500);
		vBoxOutputPrice.setPrefWidth(100);
		vBoxQuantity.setPrefHeight(500);
		vBoxQuantity.setPrefWidth(100);
		PopOver popOver = new PopOver();
		popOver.setArrowLocation(ArrowLocation.BOTTOM_CENTER);
		popOver.setContentNode(vBox);
		popOver.show((Node) event.getSource(), -15);
    }

	@FXML
	void Btn_Delete(ActionEvent event) {
		if(product != null) {
			Product product2 = new Product();
			product2.setId(product.getId());
			productRepository.delete(product2);
			initTable();
			linkAnh = null;
			product = null;
			image.setImage(null);
		}
		else {
			Message.getMess("Hãy chọn 1 sản phẩm");
		}
	}

	@FXML
	void Btn_Edit(ActionEvent event) {
		if(product != null) {
			Product pro = new Product();
			pro.setAccount(product.getAccount());
			pro.setCreatedDate(product.getCreatedDate());
			pro.setId(product.getId());
			pro.setInput_Price(Double.valueOf(txt_input.getText()));
			pro.setOutput_Price(Double.valueOf(txt_output.getText()));
			pro.setName(txt_name.getText());
			pro.setQuantity(Integer.valueOf(txt_quantity.getText()));
			if(linkAnh == null) {
				pro.setLinkImage(product.getLinkImage());
			}
			else {
				linkAnh = fileUpload.upload(linkAnh);
				pro.setLinkImage(linkAnh);
			}
			if(choice_Category.getValue() != null) {
				pro.setCategory(choice_Category.getValue());
			}
			else {
				pro.setCategory(product.getCategory());
			}
			
			productRepository.update(pro);
			initTable();
			linkAnh = null;
			product = null;
			image.setImage(null);
		}
		else {
			Message.getMess("Hãy chọn 1 sản phẩm");
		}
	}

	@FXML
	void ChooseImage(MouseEvent event) throws IOException{
		FileChooser fc = new FileChooser();
		File f = fc.showOpenDialog(null);
		if (f != null) {
			String s = f.getName();
			Path urlFile = f.toPath();
			linkAnh = urlFile.toString();
			File file = new File(linkAnh);
			String path = file.toURI().toURL().toString();
			Image image = new Image(path);
			this.image.setImage(image);

			System.out.println(linkAnh);

		}
	}

	@FXML
	void Search(KeyEvent event) {
		if(txt_search.getText().equals("")) {
			initTable();
		}
		else {
			allProduct.clear();
			productRepository.findByName(txt_search.getText()).forEach(p->{
				p.setImageView(new Image(p.getLinkImage()));
				allProduct.add(p);
			});
			mainTable.setItems(allProduct);
			col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
			col_image.setCellValueFactory(new PropertyValueFactory<>("imageView"));
			col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
			col_create_by.setCellValueFactory(new PropertyValueFactory<>("account"));
			col_date.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
			col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
			col_input.setCellValueFactory(new PropertyValueFactory<>("input_Price"));
			col_input.setCellValueFactory(new PropertyValueFactory<>("output_Price"));
			col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		categoryRepository.findAll().forEach(p->{
			allCategory.add(p);
		});
		choice_Category.setItems(allCategory);
		initTable();
		clickMainTable();
	}
	
	public void initTable() {
		allProduct.clear();
		productRepository.findAll().forEach(p->{
			p.setImageView(new Image(p.getLinkImage()));
			allProduct.add(p);
		});
		mainTable.setItems(allProduct);
		col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		col_image.setCellValueFactory(new PropertyValueFactory<>("imageView"));
		col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
		col_create_by.setCellValueFactory(new PropertyValueFactory<>("account"));
		col_date.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
		col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_input.setCellValueFactory(new PropertyValueFactory<>("input_Price"));
		col_input.setCellValueFactory(new PropertyValueFactory<>("output_Price"));
		col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
	}

	public void clickMainTable() {
		mainTable.setRowFactory(tv -> {
			TableRow<Product> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					product = row.getItem();
					txt_input.setText(String.valueOf(product.getInput_Price()));
					txt_output.setText(String.valueOf(product.getOutput_Price()));
					txt_name.setText(product.getName());
					txt_quantity.setText(String.valueOf(product.getQuantity()));
					image.setImage(new Image(product.getLinkImage()));
				}
			});
			return row;
		});
	}
}

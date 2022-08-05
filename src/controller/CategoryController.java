package controller;

import java.net.URL;
import java.util.ResourceBundle;

import domain.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import repository.CategoryRepository;
import scene.SwitchToAnotherScene;

public class CategoryController implements Initializable{

	private SwitchToAnotherScene sw = new SwitchToAnotherScene();

	private CategoryRepository categoryRepository = new CategoryRepository();
	
	private ObservableList<Category> allCategory = FXCollections.observableArrayList();
	
	private Category category = null;

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
	private TableView<Category> mainTable;

	@FXML
	private TableColumn<Category, Integer> main_id;

	@FXML
	private TableColumn<Category, String> main_name;

	@FXML
	private TextField txt_search;

	@FXML
	private TableView<Category> second_table;

	@FXML
	private TableColumn<Category, Integer> second_id;

	@FXML
	private TableColumn<Category, String> second_name;

	@FXML
	private Label total_product;

	@FXML
	private Label total_Child_Category;

	@FXML
	private TextField txt_name;

	@FXML
	private ChoiceBox<Category> choice_all_category;

	@FXML
	void Btn_Add(ActionEvent event) {
		Category category = new Category(txt_name.getText());
		if(choice_all_category.getValue()!= null) {
			category.setCategory(choice_all_category.getValue());
		}
		categoryRepository.save(category);
		loadMainTable();
	}

	@FXML
	void Btn_Delete(ActionEvent event) {
		if(category != null) {
			Category category2 = new Category();
			category2.setId(category.getId());
			categoryRepository.delete(category2);
			category = null;
			loadMainTable();
		}
		else {
			
		}
	}

	@FXML
	void Btn_Edit(ActionEvent event) {
		if(category != null) {
			Category category2 = new Category();
			category2.setId(category.getId());
			category2.setName(txt_name.getText());
			if(choice_all_category.getValue() != null) {
				category2.setCategory(choice_all_category.getValue());
			}
			else {
				category2.setCategory(category.getCategory());
			}
			categoryRepository.update(category2);
			loadMainTable();
			category = null;
		}
		else {
			
		}
	}

	@FXML
	void Search(KeyEvent event) {
		if(txt_search.getText().equals("")) {
			loadMainTable();
		}
		else {
			allCategory.clear();
			categoryRepository.findByName(txt_search.getText()).forEach(p->{
				allCategory.add(p);
			});
			mainTable.setItems(allCategory);
			main_id.setCellValueFactory(new PropertyValueFactory<>("id"));
			main_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadChoiceBox();
		loadMainTable();
		clickMainTable();
	}
	
	public void loadChoiceBox() {
		choice_all_category.setItems(allCategory);
	}
	
	public void loadMainTable() {
		allCategory.clear();
		categoryRepository.findAll().forEach(p->{
			allCategory.add(p);
		});
		loadChoiceBox();
		mainTable.setItems(allCategory);
		main_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		main_name.setCellValueFactory(new PropertyValueFactory<>("name"));
	}
	
	public void loadSecondTable(Category category) {
		ObservableList<Category> allCategorySecond = FXCollections.observableArrayList();
		category.getListCategories().forEach(p->{
			allCategorySecond.add(p);
		});
		second_table.setItems(allCategorySecond);
		second_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		second_name.setCellValueFactory(new PropertyValueFactory<>("name"));
	}
	
	public void clickMainTable() {
		mainTable.setRowFactory(tv -> {
			TableRow<Category> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					category = row.getItem();
					txt_name.setText(category.getName());
					loadSecondTable(category);
					total_product.setText(String.valueOf(category.getProducts().size())+" Sản phẩm");
					total_Child_Category.setText(String.valueOf(category.getListCategories().size())+ " Danh mục con");
				}
			});
			return row;
		});
	}
}

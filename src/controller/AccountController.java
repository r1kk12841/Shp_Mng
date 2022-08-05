package controller;

import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import domain.Account;
import domain.Category;
import domain.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import repository.AccountRepository;
import repository.EmployeeRepository;
import scene.SwitchToAnotherScene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class AccountController implements Initializable {
	private SwitchToAnotherScene sw = new SwitchToAnotherScene();
	private AccountRepository accountRepository = new AccountRepository();
	private EmployeeRepository employeeRepository = new EmployeeRepository();
	private ObservableList<String> listRole = FXCollections.observableArrayList();
	private ObservableList<Account> listAccount = FXCollections.observableArrayList();
	private ObservableList<Employee> listEmployee = FXCollections.observableArrayList();
	private Account account = null;
	private Employee employee = null;

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
	private TableView<Account> account_table;

	@FXML
	private TableColumn<Account, String> acc_username;

	@FXML
	private TableColumn<Account, String> acc_role;

	@FXML
	private TableColumn<Account, Timestamp> acc_createdDate;

	@FXML
	private TableColumn<Account, Integer> acc_status;

	@FXML
	private TextField txt_search;

	@FXML
	private Button btnLock;

	@FXML
	private TableView<Employee> employee_table;

	@FXML
	private TableColumn<Employee, String> empoyee_name;

	@FXML
	private TableColumn<Employee, Timestamp> employee_dob;

	@FXML
	private TableColumn<Employee, String> employee_phone;

	@FXML
	private TableColumn<Employee, String> employee_address;

	@FXML
	private TableColumn<Employee, ImageView> employee_image;

	@FXML
	private TextField txt_search_Emp;

	@FXML
	private TextField txt_name;

	@FXML
	private TextField txt_add;

	@FXML
	private TextField txt_phone;

	@FXML
	private DatePicker txt_dob;

	@FXML
	private ChoiceBox<String> choice_Role;

	@FXML
	private TextField txt_username;

	@FXML
	private Label image_name;

	@FXML
	void Btn_Add(ActionEvent event) throws ParseException {
		Account account = new Account();
		account.setPassword("123");
		account.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		account.setLogin(0);
		account.setRole(choice_Role.getValue());
		account.setUsername(txt_username.getText());

		Employee employee = new Employee();
		employee.setAccount(account);
		employee.setAddress(txt_add.getText());

		String time = txt_dob.getValue().toString();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date parsedDate = dateFormat.parse(time);
		Timestamp dob = new Timestamp(parsedDate.getTime());
		employee.setDob(dob);
		employee.setName(txt_name.getText());
		employee.setPhone(txt_phone.getText());
		account.setEmployee(employee);

		accountRepository.save(account);
		setInitAccountTable();
	}

	@FXML
	void Btn_Delete(ActionEvent event) {

	}

	@FXML
	void Btn_LockAccount(ActionEvent event) {
		if (account.getActive() == 1) {
			account.setActive(0);
		} else {
			account.setActive(1);
		}

		accountRepository.update(account);
	}

	@FXML
	void Btn_Update(ActionEvent event) throws ParseException {
		if(choice_Role.getValue() != null) {
			account.setRole(choice_Role.getValue());
		}
		account.getEmployee().setAddress(txt_add.getText());
		account.getEmployee().setName(txt_name.getText());
		account.getEmployee().setPhone(txt_phone.getText());
		String time = txt_dob.getValue().toString();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date parsedDate = dateFormat.parse(time);
		Timestamp dob = new Timestamp(parsedDate.getTime());
		account.getEmployee().setDob(dob);
		account.setInvoices(null);
		account.setProducts(null);
		account.setUsername(txt_username.getText());
		System.out.println(account.getEmployee());
		accountRepository.update(account);
		setInitAccountTable();
	}

	@FXML
	void ChooseImage(MouseEvent event) {
		
	}

	@FXML
	void SearchAccount(KeyEvent event) {
		if(txt_search.getText().equals("")) {
			setInitAccountTable();
		}
		else {
			listAccount.clear();
			listEmployee.clear();
			accountRepository.findByName(txt_search.getText()).forEach(p -> {
				listAccount.add(p);
				if (p.getEmployee() != null) {
					listEmployee.add(p.getEmployee());
				}
			});
			account_table.setItems(listAccount);
			acc_createdDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
			acc_role.setCellValueFactory(new PropertyValueFactory<>("role"));
			acc_status.setCellValueFactory(new PropertyValueFactory<>("login"));
			acc_username.setCellValueFactory(new PropertyValueFactory<>("username"));

			employee_table.setItems(listEmployee);
			employee_address.setCellValueFactory(new PropertyValueFactory<>("address"));
			employee_dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
			employee_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
			empoyee_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		}
	}

	@FXML
	void Search_Employee(KeyEvent event) {
		if(txt_search_Emp.getText().equals("")) {
			setInitAccountTable();
		}
		else {
			listEmployee.clear();
			employeeRepository.findByName(txt_search_Emp.getText()).forEach(p->{
				listEmployee.add(p);
			});
			employee_table.setItems(listEmployee);
			employee_address.setCellValueFactory(new PropertyValueFactory<>("address"));
			employee_dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
			employee_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
			empoyee_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listRole.add("ROLE_EMPLOYEE");
		listRole.add("ROLE_ADMIN");
		choice_Role.setItems(listRole);
		setInitAccountTable();
		ClickRowAccountTable();
	}

	public void setInitAccountTable() {
		listAccount.clear();
		listEmployee.clear();
		accountRepository.findAll().forEach(p -> {
			listAccount.add(p);
			if (p.getEmployee() != null) {
				listEmployee.add(p.getEmployee());
			}
		});
		account_table.setItems(listAccount);
		acc_createdDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
		acc_role.setCellValueFactory(new PropertyValueFactory<>("role"));
		acc_status.setCellValueFactory(new PropertyValueFactory<>("login"));
		acc_username.setCellValueFactory(new PropertyValueFactory<>("username"));

		employee_table.setItems(listEmployee);
		employee_address.setCellValueFactory(new PropertyValueFactory<>("address"));
		employee_dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
		employee_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		empoyee_name.setCellValueFactory(new PropertyValueFactory<>("name"));
	}

	public void ClickRowAccountTable() {
		account_table.setRowFactory(tv -> {
			TableRow<Account> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					account = row.getItem();
					if (account.getEmployee() != null) {
						txt_add.setText(account.getEmployee().getAddress());
						txt_dob.setValue(LocalDate.parse(account.getEmployee().getDob().toString().split(" ")[0]));
						txt_name.setText(account.getEmployee().getName());
						txt_phone.setText(account.getEmployee().getPhone());

					} else {
						txt_add.setText("");
						txt_dob.setValue(null);
						txt_name.setText("");
						txt_phone.setText("");
					}
					if (account.getActive() == 1) {
						btnLock.setText("Khóa tài khoản");
					}
					else {
						btnLock.setText("Mở khóa tài khoản");
					}
					txt_username.setText(account.getUsername());
				}
			});
			return row;
		});
	}

}

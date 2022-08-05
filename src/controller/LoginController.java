package controller;

import java.io.IOException;

import domain.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import repository.AccountRepository;
import scene.SwitchToAnotherScene;

public class LoginController {

	public static Account account = null;
	
	private AccountRepository AccountRepository = new AccountRepository();
	
	private SwitchToAnotherScene sw = new SwitchToAnotherScene();
	
	@FXML
	private TextField txt_user;

	@FXML
	private TextField txt_pass_back;

	@FXML
	private PasswordField txt_pass_front;

	@FXML
	private CheckBox show_pass;

	@FXML
	void ShowPass(MouseEvent event) {
		if(show_pass.isSelected()) {
			txt_pass_back.toFront();
		}
		else {
			txt_pass_front.toFront();
		}
	}

	@FXML
	void btnLogin(ActionEvent event) {
		Account acc = AccountRepository.checkLogin(txt_user.getText(), txt_pass_back.getText());
		if(acc != null) {
			account = acc;
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/home.fxml"));
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("../view/style.css").toExternalForm());
				stage.setScene(scene);
				stage.setTitle("trang chủ");
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			Message.getMess("đăng nhập thất bại");
		}
	}
	
    @FXML
    void Key_front(KeyEvent event) {
    	txt_pass_back.setText(txt_pass_front.getText());
    }
    
    @FXML
    void key_Back(KeyEvent event) {
    	txt_pass_front.setText(txt_pass_back.getText());
    }
}

package controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import scene.SwitchToAnotherScene;

public class HomeController {

	private SwitchToAnotherScene sw = new SwitchToAnotherScene();
	
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
}

package application;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class confController extends paymentController{

    @FXML
    private ListView<String> confListView = new ListView<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confListView.getItems().addAll(currentUser.getCartDisplayList());
    }
}

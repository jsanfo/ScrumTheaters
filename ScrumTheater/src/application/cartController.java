package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class cartController extends sceneController implements Initializable {
    @FXML
    private ListView<String> cartListView = new ListView<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cartListView.getItems().addAll(currentUser.getCartDisplayList());
    }
}

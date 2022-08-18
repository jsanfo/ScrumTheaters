package application;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class confController extends paymentController{
    //Confirmation List View.  User will determine if this is all the tickets they want.
        //If not, they may simply leave the page.
    @FXML
    private ListView<String> confListView = new ListView<>();

    /**
     * Initializes the confirmation page's List View.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confListView.getItems().addAll(currentUser.getCartDisplayList());
    }
}

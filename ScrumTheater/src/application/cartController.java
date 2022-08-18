package application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class cartController extends sceneController implements Initializable {
    @FXML
    private ListView<String> cartListView = new ListView<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cartListView.getItems().addAll(currentUser.getCartDisplayList());
    }

    /**
     * Places tickets in the cart into the User's current ticket list.
     * 	Takes User to a confirmation page.
     * @param event
     */
    public void purchaseClicked(ActionEvent event) throws IOException {

        for (int i = 0; i < currentUser.getCart().size(); i++)
        {
            currentUser.addTicketToCurrent(currentUser.getCart().get(i));
        }
    }

    public void removeFromCartClicked(ActionEvent event) throws IOException {
        String ticket = "";
        ObservableList<String> tickets;
        tickets =  cartListView.getSelectionModel().getSelectedItems();

        for (String t: tickets)
        {
            ticket = t;
        }

        String[] strs = ticket.split(" For: ");

        Integer amt = Integer.parseInt(strs[0]);
        String namTime = strs[1];
        String[] s = namTime.split(" at ");

        String name = s[0];
        String time = s[1];

        String tick = amt + "," + name + "," + time;


        currentUser.removeFromCart(tick);
        cartListView.getItems().clear();
        cartListView.getItems().addAll(currentUser.getCartDisplayList());
    }
}

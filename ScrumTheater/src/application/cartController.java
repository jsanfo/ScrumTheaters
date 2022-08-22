package application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class cartController extends sceneController implements Initializable {
    //Cart List View.  Shows the items in the cart.
    @FXML
    private ListView<String> cartListView = new ListView<>();

    /**
     * Author(s): Nova ("James") Sanford
     * Date: August 15, 2022
     * Initializes the cartListView to show all items in current User's cart.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cartListView.getItems().addAll(currentUser.getCartDisplayList());
    }

    /**
     * Author(s): Miranda Medina
     * Date: August 15, 2022
     * Places tickets in the cart into the User's current ticket list.
     * 	Takes User to a confirmation page.
     * @param event
     */
    public void purchaseClicked(ActionEvent event) throws IOException {

        continueToPayment(event);
    }

    /**
     * Author(s): Omar Marron
     * Date: August 16, 2022
     * Removes ticket selected from the cart list.
     *  If nothing is selected, gives a warning message.
     * @param event
     * @throws IOException
     */
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

    /**
     * Author(s): Omar Marron
     * Date: August 16, 2022
     * Moves the scene to the payment screen page.
     * @param event
     * @throws IOException
     */
    public void continueToPayment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("paymentScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        stage.show();
    }
}

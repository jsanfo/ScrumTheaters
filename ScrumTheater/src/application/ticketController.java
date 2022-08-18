package application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ticketController extends cartController implements Initializable {

    //List of purchased tickets
    @FXML
    protected ListView<String> purchasedTicketListView = new ListView<>();
    //Label to notify user of errors.
    @FXML
    private Label errorLbl = new Label();

    /**
     * Initializes the ticket page by filling the list view of the tickets that have been purchased.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try {
            purchasedTicketListView.getItems().addAll(currentUser.getPurchasedTickets());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * When remove is clicked:
     *  If there is no selection, will set a warning message
     *  If there is a selection clicked, clears any previous warning message and moves scene to
     *  the cancellation page.  Also initializes a ticket string into the cancellation controller.
     * @param event
     * @throws IOException
     */
    public void removeFromTickets(ActionEvent event) throws IOException {
        String ticket = "";
        ObservableList<String> tickets;
        tickets =  purchasedTicketListView.getSelectionModel().getSelectedItems();

        for (String t: tickets)
        {
            ticket = t;
        }

        if (ticket.equals(""))
        {
            errorLbl.setText("Please Make A Selection");
            return;
        }
        errorLbl.setText("");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("cancellationPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        cancellationController controller = loader.getController();
        controller.initData(ticket);

        stage.show();
    }
}

package application;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ticketController extends cartController implements Initializable {

    //Ticket Page
    @FXML
    private ListView<String> purchasedTicketListView = new ListView<>();
    @FXML
    private Label errorLbl = new Label();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try {
            purchasedTicketListView.getItems().addAll(currentUser.getPurchasedTickets());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFromTickets() throws IOException {

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

        String strs[] = ticket.split(" For: ");

        Integer amt = Integer.parseInt(strs[0]);
        String namTime = strs[1];
        String s[] = namTime.split(" at ");

        String name = s[0];
        String time = s[1];

        String tick = amt + "," + name + "," + time;

        currentUser.removeFromPurchased(tick);
        purchasedTicketListView.getItems().clear();
        purchasedTicketListView.getItems().addAll(currentUser.getPurchasedTickets());
    }
}

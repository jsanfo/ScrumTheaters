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

    //Ticket Page
    @FXML
    protected ListView<String> purchasedTicketListView = new ListView<>();
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

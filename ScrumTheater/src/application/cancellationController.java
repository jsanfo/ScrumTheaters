package application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class cancellationController extends paymentController{

    private String ticket;

    @FXML
    private Label cardNumLabel = new Label();
    @FXML
    private AnchorPane areYouSurePane = new AnchorPane();
    @FXML
    private AnchorPane confirmedPane = new AnchorPane();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        cardNumLabel.setText(censorCardNumber(currentUser.getCardNum()));
    }

    public void initData(String t) {
        ticket = t;
    }

    public void yesCancelClicked(ActionEvent event) throws IOException {
        areYouSurePane.setVisible(false);
        areYouSurePane.setDisable(true);


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


        confirmedPane.setVisible(true);
        confirmedPane.setDisable(false);
    }

}

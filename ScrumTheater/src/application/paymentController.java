package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class paymentController extends ticketController {

    @FXML
    private Label userLabel = new Label();
    @FXML
    private Label cardLabel = new Label();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        setLabels();
    }

    public void confirmClicked(ActionEvent event) throws IOException {
        for (int i = 0; i < currentUser.getCart().size(); i++) {
            currentUser.addTicketToCurrent(currentUser.getCart().get(i));
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("confPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));



        stage.show();

    }

    private void setLabels()
    {
        userLabel.setText(currentUser.getUserName());
        cardLabel.setText(censorCardNumber(currentUser.getCardNum()));
    }
    protected String censorCardNumber(String cardNum)
    {
        char star = '*';

        for (int i = 0; i < 12; i++)
        {
            cardNum = cardNum.substring(0, i) + star
                    + cardNum.substring(i+1);
        }

        return cardNum;
    }
}
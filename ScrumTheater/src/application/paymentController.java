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
    //Shows current User's username to show customer to confirm.
    @FXML
    private Label userLabel = new Label();
    //Shows a censored card number (only the last 4 digits visible)
    @FXML
    private Label cardLabel = new Label();

    /**
     * Author(s): Miranda Medina
     * Date: August 18, 2022
     * Initializes by setting the labels with username and card number respectively.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        setLabels();
    }

    /**
     * Author(s): Miranda Medina
     * Date: August 18, 2022
     * Moves scene to the confirmation page.
     * @param event
     * @throws IOException
     */
    public void confirmClicked(ActionEvent event) throws IOException {
        for (int i = 0; i < currentUser.getCart().size(); i++) {
            currentUser.addTicketToCurrent(currentUser.getCart().get(i));
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("confPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        stage.show();
    }

    /**
     * Author(s): Miranda Medina
     * Date: August 18, 2022
     * Sets the Labels for the username and card number.
     */
    private void setLabels()
    {
        userLabel.setText(currentUser.getUserName());
        cardLabel.setText(censorCardNumber(currentUser.getCardNum()));
    }

    /**
     * Author(s): Miranda Medina, Omar Marron
     * Date: August 18, 2022
     * Censors the card number for all digits except the last 4, and returns it.
     * @param cardNum number to be censored
     * @return censored number.
     */
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
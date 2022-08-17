package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class showTimeController extends movieController implements Initializable {

    private static String movNameStr;
    @FXML
    private Label movNameLbl = new Label();
    @FXML
    private ChoiceBox<String> eightPM = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> eightThirPM  = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> ninePM  = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> nineThirPM  = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> tenPM  = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> tenThirPM  = new ChoiceBox<>();
    @FXML
    private Label errorLbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        eightPM.getItems().addAll(getAmounts());
        eightThirPM.getItems().addAll(getAmounts());
        ninePM.getItems().addAll(getAmounts());
        nineThirPM.getItems().addAll(getAmounts());
        tenPM.getItems().addAll(getAmounts());
        tenThirPM.getItems().addAll(getAmounts());
    }

    public void initData(String movName)
    {
        movNameStr = movName;
        movNameLbl.setText(movNameStr);
    }

    /**
     * If there are selection(s), take User to the cart with those selections added to it.
     * @param event
     */
    public void continueCartClicked (ActionEvent event) throws IOException {
        List<String> selections = new ArrayList<>();
        String movName = movNameLbl.getText();

        if (eightPM.getValue() != null && !Objects.equals(eightPM.getValue(), "0")){
            selections.add(eightPM.getValue() + "," + movName + ",8:00pm");
        }
        if (eightThirPM.getValue() != null && !Objects.equals(eightThirPM.getValue(), "0")){
            selections.add(eightThirPM.getValue() + "," + movName + ",8:30pm");
        }
        if (ninePM.getValue() != null && !Objects.equals(ninePM.getValue(), "0")){
            selections.add(ninePM.getValue() + "," + movName + ",9:00pm");
        }
        if (nineThirPM.getValue() != null && !Objects.equals(nineThirPM.getValue(), "0")){
            selections.add(nineThirPM.getValue() + "," + movName + ",8:30pm");
        }
        if (tenPM.getValue() != null && !Objects.equals(tenPM.getValue(), "0")){
            selections.add(tenPM.getValue() + "," + movName + ",10:00pm");
        }
        if (tenThirPM.getValue() != null && !Objects.equals(tenThirPM.getValue(), "0")){
            selections.add(tenThirPM.getValue() + "," + movName + ",10:30pm");
        }

        if(!selections.isEmpty()){
            currentUser.addToCart(selections);
            toCart(event);
        }
        else
        {
            errorLbl.setText("Please Make A Selection Before Continuing");
        }

    }

    /**
     * Take the user to the cart.
     * @param event
     * @throws IOException
     */
    private void toCart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cartPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        stage.show();
    }

    /**
     * Similar to getMovieList, gets amounts to add to the choice boxes in the Show Times page.
     * @return List of amounts of tickets you can buy
     */
    private List<String> getAmounts (){
        List<String> amounts = new ArrayList<>();

        amounts.add("0");
        amounts.add("1");
        amounts.add("2");
        amounts.add("3");

        return amounts;


    }
}

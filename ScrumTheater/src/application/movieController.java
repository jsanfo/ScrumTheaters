package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class movieController extends sceneController {

    @FXML
    protected Text movNameTxt = new Text();

    /**
     * Takes User to showtimes of this movie.
     * @param event
     * @throws IOException
     */
    public void showTimes(ActionEvent event) throws IOException {
        continueToShowTimes(event);
    }
    private void continueToShowTimes(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("showTimes.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        showTimeController controller = loader.getController();
        controller.initData(movNameTxt.getText());

        stage.show();
    }
}

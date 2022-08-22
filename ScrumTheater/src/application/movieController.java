package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class movieController extends sceneController {
    //Movie's name.  The movie name is derived from this object and passed to the showtimes page.
    @FXML
    protected Text movNameTxt = new Text();

    /**
     * Author(s): Nova ("James") Sanford
     * Date: August 16, 2022
     * Takes User to showtimes of this movie.
     * @param event
     * @throws IOException
     */
    public void showTimes(ActionEvent event) throws IOException {
        continueToShowTimes(event);
    }

    /**
     * Author(s): Nova ("James") Sanford
     * Date: August 16, 2022
     * Moves scene to showtime page.
     * @param event
     * @throws IOException
     */
    private void continueToShowTimes(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("showTimes.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        showTimeController controller = loader.getController();
        controller.initData(movNameTxt.getText());

        stage.show();
    }
}

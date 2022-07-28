package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class sceneController implements Initializable
{
	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	public ListView<String> catalogListView = new ListView<>();


	// Initialization //

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{

		try {
			catalogListView.getItems().addAll(getMovieList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


		/*

		@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.getItems().addAll(items);

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                currentItem = listView.getSelectionModel().getSelectedItem();

                myLabel.setText(currentItem);
            }
        });
    }

		*/
	}


	// Event Methods //

	/**
	 * When the title is clicked, opens the home page.
	 * @param event Mouse clicked on the label
	 * @throws IOException
	 */
	public void titleClicked(MouseEvent event) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("homePage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * When home button is clicked, opens the home page.
	 * @param event Button clicked
	 * @throws IOException
	 */
	public void homeClicked(ActionEvent event) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("homePage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * When catalog button is clicked, opens the catalog page
	 * @param event Button clicked
	 * @throws IOException
	 */
	public void catalogClicked(ActionEvent event) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("catalogPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * When tickets button is clicked, opens the tickets page.
	 * @param event Button clicked
	 * @throws IOException
	 */
	public void ticketsClicked(ActionEvent event) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("ticketPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * When account button is clicked, opens the Account page
	 * @param event Button clicked
	 * @throws IOException
	 */
	public void accountClicked(ActionEvent event) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("accountPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * When login button is clicked, open login page
	 * @param event Button clicked
	 * @throws IOException
	 */
	public void loginClicked(ActionEvent event) throws IOException
	{
		//No Login Yet
			//Will log in the user using the username/email and the password
			//Then, take them to the home page
	}

	/**
	 * When create account button is clicked, open the registration page.
	 * @param event Button clicked
	 * @throws IOException
	 */
	public void createAccClicked(ActionEvent event) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("registrationPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}


	// Helper Methods //


	private List<String> getMovieList() throws IOException {
		List<String> movieList = new ArrayList<String>();

		File movNamesFile = new File("ScrumTheater/bin/resources/text/movieNames");
		FileInputStream is = new FileInputStream(movNamesFile);
		Scanner sc = new Scanner(is);

		while (sc.hasNextLine())
		{
			movieList.add(sc.nextLine());
		}

		sc.close();

		Collections.sort(movieList);

		return movieList;
	}

}

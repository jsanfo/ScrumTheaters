package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class sceneController implements Initializable
{
	private Stage stage;
	private Scene scene;
	private Parent root;

	//Treemap of usernames and passwords.  Passwords are never directly called, only derived from this treemap
		//Usernames are stored as keys,
		//Passwords are stored as values.
	protected static TreeMap<String, String> userPasses = new TreeMap<>();
	//User instance of the current user, initialized when the user logs in or registers.
	protected static User currentUser;

	//Account Page
	@FXML
	private TextField userField;
	@FXML
	private TextField passField;
	@FXML
	private TextField cardField;
	@FXML
	private Button finalizeBtn;
	@FXML
	private Button editProfBtn;
	@FXML
	private Button viewInfoBtn;
	@FXML
	private Label errorLbl;

	//Catalog Page
	@FXML
	private ListView<String> catalogListView = new ListView<>();

	// Initialization //

	/**
	 * Author(s): Nova ("James") Sanford
	 * Date: July 27, 2022
	 * Initializes the list view for the catalog.
	 * @param url
	 * @param resourceBundle
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		try {
			catalogListView.getItems().addAll(getMovieList());



		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Author(s): Nova ("James") Sanford
	 * Date: August 16, 2022
	 * Initializes data after User logs in
	 * @param current User instance for the current user.
	 * @param UP Treemap for the usernames/passwords
	 */
	public void initData(User current, TreeMap<String, String> UP)
	{
		currentUser = current;
		userPasses = UP;
	}


	// Event Methods //

	/**
	 * Author(s): Nova ("James") Sanford, Miranda Medina
	 * Date: July 27, 2022
	 * When the title is clicked, opens the home page.
	 * @param event Mouse clicked on the label
	 * @throws IOException
	 */
	public void titleClicked(MouseEvent event) throws IOException
	{
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Author(s): Nova ("James") Sanford, Miranda Medina
	 * Date: July 27, 2022
	 * When home button is clicked, opens the home page.
	 * @param event Button clicked
	 * @throws IOException
	 */
	public void homeClicked(ActionEvent event) throws IOException
	{
		goTo(event, "homePage.fxml");
	}

	/**
	 * Author(s): Nova ("James") Sanford
	 * Date: July 27, 2022
	 * When catalog button is clicked, opens the catalog page
	 * @param event Button clicked
	 * @throws IOException
	 */
	public void catalogClicked(ActionEvent event) throws IOException
	{
		goTo(event, "catalogPage.fxml");
	}

	/**
	 * Author(s): Nova ("James") Sanford, Miranda Medina
	 * Date: July 29, 2022
	 * When tickets button is clicked, opens the tickets page.
	 * @param event Button clicked
	 * @throws IOException
	 */
	public void ticketsClicked(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ticketPage.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(loader.load()));



		stage.show();
	}

	/**
	 * Author(s): Nova ("James") Sanford, Miranda Medina
	 * Date: July 29, 2022
	 * When account button is clicked, opens the Account page
	 * @param event Button clicked
	 * @throws IOException
	 */
	public void accountClicked(ActionEvent event) throws IOException
	{
		goTo(event, "accountPage.fxml");
	}

	/**
	 * Author(s): Nova ("James") Sanford
	 * Date: July 29, 2022
	 * 	Allows User to see their username,
	 * 	Amount of chars in their password,
	 * 	Amount of chars in their Card Number
	 * @param event
	 */
	public void viewInfoClicked(ActionEvent event)
	{
		userField.setText(currentUser.getUserName());
		passField.setText(userPasses.get(currentUser.getUserName()));
		cardField.setText(currentUser.getCardNum());

		viewInfoBtn.setDisable(true);
		viewInfoBtn.setVisible(false);

		editProfBtn.setDisable(false);
		editProfBtn.setVisible(true);
	}

	/**
	 * Author(s): Nova ("James") Sanford, Miranda Medina
	 * Date: July 29, 2022
	 * Allows user to edit their username, password, and Card Number
	 * @param event
	 */
	public void editProfileClicked(ActionEvent event)
	{
		userField.setDisable(false);
		passField.setDisable(false);
		cardField.setDisable(false);

		editProfBtn.setDisable(true);
		editProfBtn.setVisible(false);

		finalizeBtn.setDisable(false);
		finalizeBtn.setVisible(true);
	}

	/**
	 * Author(s): Nova ("James") Sanford
	 * Date: July 29, 2022
	 * Checks that requirements are met, if not displays why in a label
	 * Sets username, password, and card number found in text and pass fields to the User's
	 * 	matching variables.
	 * @throws IOException
	 */
	public void finalizeClicked() throws IOException {
		if (Objects.equals(userField.getText(), passField.getText()))
		{
			errorLbl.setText("Username cannot be the same as password");
			return;
		}
		else if (Objects.equals(userField.getText(), cardField.getText()))
		{
			errorLbl.setText("Username cannot be the same as Card Number");
			return;
		}
		else if (Objects.equals(passField.getText(), cardField.getText()))
		{
			errorLbl.setText("Password cannot be the same as Card Number");
			return;
		}

		if (cardField.getText().length() != 16)
		{
			errorLbl.setText("Card Number must be 16 digits");
			return;
		}
		else if (!cardField.getText().matches("[0-9]+"))
		{
			errorLbl.setText("Card Number must only contain numbers.");
			return;
		}
		currentUser.setUserName(userField.getText());
		currentUser.setPassword(passField.getText());
		currentUser.setCardNum(cardField.getText());

		finalizeBtn.setDisable(true);
		finalizeBtn.setVisible(false);

		viewInfoBtn.setDisable(false);
		viewInfoBtn.setVisible(true);

		userField.setDisable(true);
		userField.setText("");
		passField.setDisable(true);
		passField.setText("");
		cardField.setDisable(true);
		cardField.setText("");
	}

	/**
	 * Author(s): Nova ("James") Sanford, Miranda Medina
	 * Date: August 15, 2022
	 * Sets the movie that was selected as the choice.  Does nothing if no movie was
	 * selected
	 * @param event
	 * @throws IOException
	 */
	public void movieSelected(ActionEvent event) throws IOException {
		String movie="";
		ObservableList<String> movies;
		movies=catalogListView.getSelectionModel().getSelectedItems();

		for(String m: movies){
			movie = m;
		}
		if (movie.equals("Atlantic Rim"))
			ARClicked(event);
		else if (movie.equals("Big Guy 7"))
			BG7Clicked(event);
		else if (movie.equals("Finding Fish"))
			FFClicked(event);
		else if (movie.equals("The Quick and The Angry"))
			QAClicked(event);
		else if (movie.equals("Squidnado"))
			SquidClicked(event);
		else if (movie.equals("The Neptunian"))
			NeptunianClicked(event);
		else if (movie.equals("Mravel's The Revengers"))
			RevengersClicked(event);

	}

	/**
	 * Author(s):
	 * Date:
	 * Takes the user to Atlantic Rim's description page.
	 * @param event
	 * @throws IOException
	 */
	public void ARClicked(ActionEvent event) throws IOException {
		goTo(event, "Atlantic_Rim.fxml");
	}

	/**
	 * Author(s): Nova ("James") Sanford, Miranda Medina
	 * Date: July 31, 2022
	 * Takes the user to Big Guy 7's description page.
	 * @param event
	 * @throws IOException
	 */
	public void BG7Clicked(ActionEvent event) throws IOException {
		continueToMovie(event, "BigGuy7.fxml");
	}

	/**
	 * Author(s): Nova ("James") Sanford, Miranda Medina
	 * Date: July 31, 2022
	 * Takes the user to Finding Fish's description page.
	 * @param event
	 * @throws IOException
	 */
	public void FFClicked(ActionEvent event) throws IOException {
		goTo(event, "Finding_Fish.fxml");
	}

	/**
	 * Author(s): Nova ("James") Sanford, Miranda Medina
	 * Date: July 31, 2022
	 * Takes the user to The Quick and The Angry's description page.
	 * @param event
	 * @throws IOException
	 */
	public void QAClicked(ActionEvent event) throws IOException {
		goTo(event, "QuickAndAngry.fxml");
	}

	/**
	 * Author(s): Nova ("James") Sanford, Miranda Medina
	 * Date: July 31, 2022
	 * Takes the user to Squidnado's description page.
	 * @param event
	 * @throws IOException
	 */
	public void SquidClicked(ActionEvent event) throws IOException {
		goTo(event, "Squidnado.fxml");
	}

	/**
	 * Author(s): Nova ("James") Sanford, Miranda Medina
	 * Date: July 31, 2022
	 * Takes the user to The Neptunian's description page.
	 * @param event
	 * @throws IOException
	 */
	public void NeptunianClicked(ActionEvent event) throws IOException {
		goTo(event, "TheNeptunian.fxml");
	}

	/**
	 * Author(s): Nova ("James") Sanford, Miranda Medina
	 * Date: July 31, 2022
	 * Takes the user to The Revengers' description page.
	 * @param event
	 * @throws IOException
	 */
	public void RevengersClicked(ActionEvent event) throws IOException {
		goTo(event, "TheRevengers.fxml");
	}


	// Helper Methods //

	/**
	 * Author(s): Nova ("James") Sanford
	 * Date: July 29, 2022
	 * Sets the scene to the .fxml file whose name was passed in.
	 * 	Easy implementation for a button to "Go To" a page.
	 * @param event
	 * @param fileName Name of the file to go to
	 * @throws IOException
	 */
	private void goTo(ActionEvent event, String fileName) throws IOException
	{
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fileName)));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Author(s): Nova ("James") Sanford
	 * Date: August 8, 2022
	 * Moves scene to the name of the page's file passed in.
	 * @param event
	 * @param fileName name of the page's file.
	 * @throws IOException
	 */
	private void continueToMovie(ActionEvent event, String fileName) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(loader.load()));

		stage.show();
	}

	/**
	 * Author(s): Nova ("James") Sanford, Miranda Medina
	 * Date: July 29, 2022
	 * Gets the movie list information from movieNames.txt.
	 * 	Used in initialize method to initialize the catalog list.
	 * @return A list of movie names
	 * @throws IOException
	 */
	private List<String> getMovieList() throws IOException {
		List<String> movieList = new ArrayList<String>();

		File movNamesFile = new File("ScrumTheater/bin/resources/text/movieNames");
		FileInputStream is = new FileInputStream(movNamesFile);
		Scanner sc = new Scanner(is);

		while (sc.hasNextLine())
		{
			movieList.add(sc.nextLine());
		}

		is.close();
		sc.close();

		Collections.sort(movieList);

		return movieList;
	}
}
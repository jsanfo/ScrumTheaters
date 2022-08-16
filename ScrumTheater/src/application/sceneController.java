package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class sceneController implements Initializable
{
	private Stage stage;
	private Scene scene;
	private Parent root;

	private TreeMap<String, String> userPasses;
	private static User currentUser;

	//Account Page
	@FXML
	private TextField accTextField;
	@FXML
	private TextField accPassField;
	@FXML
	private TextField accCardField;
	@FXML
	private Button finalizeBtn;
	@FXML
	private Button editProfBtn;
	@FXML
	private Button viewInfoBtn;

	//Registration Page
	@FXML
	private TextField regUserField;
	@FXML
	private TextField cardNumField;
	@FXML
	private PasswordField regPasswordField;
	@FXML
	private PasswordField confirmPasswordField;
	@FXML
	private Label regErrorLbl;

	//Login Page
	@FXML
	private Label errorLbl;
	@FXML
	private TextField userField;
	@FXML
	private PasswordField passwordField;

	//Showtimes Page
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

	//Catalog Page
	@FXML
	private ListView<String> catalogListView = new ListView<>();

	//Ticket Page
	@FXML
	private ListView<String> purchasedTicketListView = new ListView<>();
	@FXML
	private ListView<String> pastTicketListView = new ListView<>();


	// Initialization //

	/**
	 * Initializes the list view for the catalog.
	 * 	Later, will initialize the list views for the previous and purchased tickets as well.
	 * @param url
	 * @param resourceBundle
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		try {
			catalogListView.getItems().addAll(getMovieList());

			eightPM.getItems().addAll(getAmounts());
			eightThirPM.getItems().addAll(getAmounts());
			ninePM.getItems().addAll(getAmounts());
			nineThirPM.getItems().addAll(getAmounts());
			tenPM.getItems().addAll(getAmounts());
			tenThirPM.getItems().addAll(getAmounts());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void initData(User current, TreeMap<String, String> UP)
	{
		currentUser = current;
		userPasses = UP;
	}


	// Event Methods //

	/**
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
	 * When home button is clicked, opens the home page.
	 * @param event Button clicked
	 * @throws IOException
	 */
	public void homeClicked(ActionEvent event) throws IOException
	{
		goTo(event, "homePage.fxml");
	}

	/**
	 * When catalog button is clicked, opens the catalog page
	 * @param event Button clicked
	 * @throws IOException
	 */
	public void catalogClicked(ActionEvent event) throws IOException
	{
		goTo(event, "catalogPage.fxml");
	}

	/**
	 * When tickets button is clicked, opens the tickets page.
	 * @param event Button clicked
	 * @throws IOException
	 */
	public void ticketsClicked(ActionEvent event) throws IOException
	{
		goTo(event, "ticketPage.fxml");
	}

	/**
	 * When account button is clicked, opens the Account page
	 * @param event Button clicked
	 * @throws IOException
	 */
	public void accountClicked(ActionEvent event) throws IOException
	{
		goTo(event, "accountPage.fxml");
	}


	/**
	 * When create account button is clicked, open the registration page.
	 * @param event Button clicked
	 * @throws IOException
	 */


	/**
	 * 	Allows User to see their username,
	 * 	Amount of chars in their password,
	 * 	Amount of chars in their Card Number
	 * @param event
	 */
	public void viewInfoClicked(ActionEvent event)
	{
		accTextField.setText(currentUser.getUserName());
		accPassField.setText(userPasses.get(currentUser.getUserName()));
		accCardField.setText(currentUser.getCardNum());

		viewInfoBtn.setDisable(true);
		viewInfoBtn.setVisible(false);

		editProfBtn.setDisable(false);
		editProfBtn.setVisible(true);
	}

	/**
	 * Allows user to edit their username, password, and Card Number
	 * @param event
	 */
	public void editProfileClicked(ActionEvent event)
	{
		accTextField.setDisable(false);
		accPassField.setDisable(false);
		accCardField.setDisable(false);

		editProfBtn.setDisable(true);
		editProfBtn.setVisible(false);

		finalizeBtn.setDisable(false);
		finalizeBtn.setVisible(true);
	}

	/**
	 * Checks that requirements are met, if not displays why in a label
	 * Sets username, password, and card number found in text and pass fields to the User's
	 * 	matching variables.
	 * @throws IOException
	 */
	public void finalizeClicked() throws IOException {
		if (Objects.equals(accTextField.getText(), accPassField.getText()))
		{
			errorLbl.setText("Username cannot be the same as password");
			return;
		}
		else if (Objects.equals(accTextField.getText(), accCardField.getText()))
		{
			errorLbl.setText("Username cannot be the same as Card Number");
			return;
		}
		else if (Objects.equals(accPassField.getText(), accCardField.getText()))
		{
			errorLbl.setText("Password cannot be the same as Card Number");
			return;
		}

		if (accCardField.getText().length() != 16)
		{
			errorLbl.setText("Card Number must be 16 digits");
			return;
		}
		else if (!accCardField.getText().matches("[0-9]+"))
		{
			errorLbl.setText("Card Number must only contain numbers.");
			return;
		}
		currentUser.setUserName(accTextField.getText());
		currentUser.setPassword(accPassField.getText());
		currentUser.setCardNum(accCardField.getText());

		finalizeBtn.setDisable(true);
		finalizeBtn.setVisible(false);

		viewInfoBtn.setDisable(false);
		viewInfoBtn.setVisible(true);

		accTextField.setDisable(true);
		accTextField.setText("");
		accPassField.setDisable(true);
		accPassField.setText("");
		accCardField.setDisable(true);
		accCardField.setText("");
	}

	/**
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
	 * Takes User to showtimes of this movie.
	 * @param event
	 * @throws IOException
	 */

	public void showTimes(ActionEvent event) throws IOException {
		goTo(event, "showTimes.fxml" );
	}

	/**
	 * If there are selection(s), take User to the cart with those selections added to it.
	 * @param event
	 */
	public void continueCartClicked (ActionEvent event) throws IOException {
		List<String> selections = new ArrayList<>();

		if (eightPM.getValue() != null && !Objects.equals(eightPM.getValue(), "0")){
			selections.add(eightPM.getValue());
		}

		if (eightThirPM.getValue() != null && !Objects.equals(eightThirPM.getValue(), "0")){
			selections.add(eightThirPM.getValue());
		}
		if (ninePM.getValue() != null && !Objects.equals(ninePM.getValue(), "0")){
			selections.add(ninePM.getValue());
		}
		if (nineThirPM.getValue() != null && !Objects.equals(nineThirPM.getValue(), "0")){
			selections.add(nineThirPM.getValue());
		}
		if (tenPM.getValue() != null && !Objects.equals(tenPM.getValue(), "0")){
			selections.add(tenPM.getValue());
		}
		if (tenThirPM.getValue() != null && !Objects.equals(tenThirPM.getValue(), "0")){
			selections.add(tenThirPM.getValue());
		}

		if(!selections.isEmpty()){
			System.out.println(selections);
			currentUser.addToCart(selections);
			goTo(event, "cartPage.fxml");
		}
		else
		{
			errorLbl.setText("Please Make A Selection Before Continuing");
		}

	}

	/**
	 *
	 * @param event
	 */
	public void purchaseCLicked (ActionEvent event){

	}


	/**
	 * Takes the user to Atlantic Rim's description page.
	 * @param event
	 * @throws IOException
	 */
	public void ARClicked(ActionEvent event) throws IOException {
		goTo(event, "Atlantic_Rim.fxml");
	}

	/**
	 * Takes the user to Big Guy 7's description page.
	 * @param event
	 * @throws IOException
	 */
	public void BG7Clicked(ActionEvent event) throws IOException {
		goTo(event, "BigGuy7.fxml");
	}

	/**
	 * Takes the user to Finding Fish's description page.
	 * @param event
	 * @throws IOException
	 */
	public void FFClicked(ActionEvent event) throws IOException {
		goTo(event, "Finding_Fish.fxml");
	}

	/**
	 * Takes the user to The Quick and The Angry's description page.
	 * @param event
	 * @throws IOException
	 */
	public void QAClicked(ActionEvent event) throws IOException {
		goTo(event, "QuickAndAngry.fxml");
	}

	/**
	 * Takes the user to Squidnado's description page.
	 * @param event
	 * @throws IOException
	 */
	public void SquidClicked(ActionEvent event) throws IOException {
		goTo(event, "Squidnado.fxml");
	}

	/**
	 * Takes the user to The Neptunian's description page.
	 * @param event
	 * @throws IOException
	 */
	public void NeptunianClicked(ActionEvent event) throws IOException {
		goTo(event, "TheNeptunian.fxml");
	}

	/**
	 * Takes the user to The Revengers' description page.
	 * @param event
	 * @throws IOException
	 */
	public void RevengersClicked(ActionEvent event) throws IOException {
		goTo(event, "TheRevengers.fxml");
	}


	// Helper Methods //

	/**
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

	/**
	 * Similar to getMovieList, gets amounts to add to the choice boxes in the Show Times page.
	 * @return
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
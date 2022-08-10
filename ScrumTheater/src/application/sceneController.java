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
	private TreeMap<String, String> userPasses = new TreeMap<>();
	private static User currentUser;

	// Login Page username TextField
	@FXML
	private TextField userField;
	// Registration Page username TextField
	@FXML
	private TextField regUserField;
	@FXML
	private TextField cardNumField;
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
	// Login Page password PasswordField
	@FXML
	private PasswordField passwordField;
	// Registration Page password PasswordField
	@FXML
	private PasswordField regPasswordField;
	// Registration Page password confirmation PasswordField
	@FXML
	private PasswordField confirmPasswordField;
	// Login Page error label Label
	@FXML
	private Label errorLbl;
	// Registration Page error label Label
	@FXML
	private Label regErrorLbl;
	// Catalog Page Listview
	@FXML
	private ListView<String> catalogListView = new ListView<>();
	// Ticket Page List View
	@FXML
	private ListView<String> purchasedTicketListView = new ListView<>();
	@FXML
	private ListView<String> pastTicketListView = new ListView<>();

	/**
	 * Default constructor for sceneController class
	 * 	calls getUserPasses method to initialize the TreeMap.
	 * @throws FileNotFoundException
	 */
	public sceneController() throws FileNotFoundException
	{
		getUserPasses();
	}

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
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
	 * When login button is clicked, open login page
	 * @param event Button clicked
	 * @throws IOException
	 */
	public void loginClicked(ActionEvent event) throws IOException
	{

		String username = userField.getText();
		String password = passwordField.getText();


		if (!checkUserIsRegistered(username))
		{
			errorLbl.setText("Username provided has no registered account");
			return;
		}

		if (!checkPasswordCorrect(username, password))
		{
			errorLbl.setText("Password does not match username provided");
		}
		else
		{
			currentUser = new User(new File("ScrumTheater/src/resources/text/accountFiles/" + username));
			homeClicked(event);
		}
	}
	/**
	 * When create account button is clicked, open the registration page.
	 * @param event Button clicked
	 * @throws IOException
	 */
	public void createAccClicked(ActionEvent event) throws IOException
	{
		goTo(event, "registrationPage.fxml");
	}
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
	 * Registers the user's new account with the parameters provided in the
	 * 	text field and password fields.
	 * @param event Button Clicked
	 * @throws IOException
	 */
	public void registerAccClicked(ActionEvent event) throws IOException
	{
		if (userPasses.containsKey(regUserField.getText()))
		{
			regErrorLbl.setText("This username is already in use");
			return;
		}

		if (!cardNumField.getText().matches("[0-9]+"))
		{
			regErrorLbl.setText("Invalid Card Number: Numbers only");
			return;
		}
		else if (cardNumField.getText().length() != 16)
		{
			regErrorLbl.setText("Invalid Card Number: Must Be 16 Characters");
			return;
		}

		if (Objects.equals(regUserField.getText(), regPasswordField.getText()))
		{
			regErrorLbl.setText("Username cannot be the same as password");
			return;
		}
		else if (Objects.equals(regUserField.getText(), cardNumField.getText()))
		{
			regErrorLbl.setText("Username cannot be the same as Card Number");
			return;
		}
		else if (Objects.equals(regPasswordField.getText(), cardNumField.getText()))
		{
			regErrorLbl.setText("Password cannot be the same as Card Number");
			return;
		}

		if (Objects.equals(regPasswordField.getText(), confirmPasswordField.getText()))
		{

			currentUser = new User(regUserField.getText(),
					regPasswordField.getText(),
					cardNumField.getText());
			currentUser.createAccountFile();
			goTo(event, "homePage.fxml");
		}

	}
	public void ARClicked(ActionEvent event) throws IOException {
		goTo(event, "Atlantic_Rim.fxml");
	}
	public void BG7Clicked(ActionEvent event) throws IOException {
		goTo(event, "BigGuy7.fxml");
	}
	public void FFClicked(ActionEvent event) throws IOException {
		goTo(event, "Finding_Fish.fxml");
	}
	public void QAClicked(ActionEvent event) throws IOException {
		goTo(event, "QuickAndAngry.fxml");
	}
	public void SquidClicked(ActionEvent event) throws IOException {
		goTo(event, "Squidnado.fxml");
	}
	public void NeptunianClicked(ActionEvent event) throws IOException {
		goTo(event, "TheNeptunian.fxml");
	}
	public void RevengersClicked(ActionEvent event) throws IOException {
		goTo(event, "TheRevengers.fxml");
	}


	// Helper Methods //

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
	 * Checks if the user is already registered.
	 * 	If they are, return true;
	 * @param username To check across the Treemap of Usernames and Passwords.
	 * @return True if they are registered with this username already
	 * 			False if they are not.
	 */
	private boolean checkUserIsRegistered(String username)
	{
		boolean isRegistered = false;

		for (int i = 0; i < userPasses.size(); i++)
		{
			if (userPasses.containsKey(username))
				isRegistered = true;
		}

		return isRegistered;
	}

	/**
	 * Checks if the password passed in is part of the key, value pair with the username.
	 * @param username The key of the userPasses Treemap
	 * @param password The value of the userPasses Treemap
	 * @return True if these two are paired,
	 * 			False if they are not.
	 */
	private boolean checkPasswordCorrect(String username, String password)
	{
		boolean isCorrect = false;

		String check = userPasses.get(username);

		if (Objects.equals(check, password))
			isCorrect = true;

		return isCorrect;
	}

	/**
	 * Gets the username and password from every account file
	 * 	and puts it in the userPasses Treemap as a key, value pair
	 * @throws FileNotFoundException
	 */
	private void getUserPasses() throws FileNotFoundException {
		File accountFolder = new File("ScrumTheater/src/resources/text/accountFiles");
		List<File> files = List.of(Objects.requireNonNull(accountFolder.listFiles()));



		for (int i = 0; i < files.size(); i++)
		{
			Scanner sc = new Scanner(files.get(i));
			String user = sc.nextLine();
			String pass = sc.nextLine();

			userPasses.put(user, pass);
			sc.close();
		}
	}
}
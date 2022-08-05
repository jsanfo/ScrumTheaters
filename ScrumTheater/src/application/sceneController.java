package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class sceneController implements Initializable
{
	private Stage stage;
	private Scene scene;
	private Parent root;
	private TreeMap<String, String> userPasses = new TreeMap<>();
	//private User currentUser = null;
	private String currentUsername = "";

	// Login Page username TextField
	@FXML
	private TextField userField;
	// Registration Page username TextField
	@FXML
	private TextField regUserField;
	@FXML
	private TextField accTextField;
	@FXML
	private TextField accPassField;
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
	private ListView<String> ticketListView = new ListView<>();

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
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
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
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("catalogPage.fxml")));
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
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ticketPage.fxml")));
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

		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("accountPage.fxml")));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

		accTextField.setText(currentUsername);
		accPassField.setText(userPasses.get(currentUsername));
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
			errorLbl.setText("Password does not match username provided");
		else
		{
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
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("registrationPage.fxml")));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Registers the user's new account with the parameters provided in the
	 * 	text field and password fields.
	 * @param event Button Clicked
	 * @throws IOException
	 */
	public void registerAccClicked(ActionEvent event) throws IOException
	{
		User currentUser;
		if (userPasses.containsKey(regUserField.getText()))
		{
			regErrorLbl.setText("This username is already in use");
			return;
		}

		if (Objects.equals(regPasswordField.getText(), confirmPasswordField.getText()))
		{
			currentUser = new User(regUserField.getText(), regPasswordField.getText());
			currentUser.createAccountFile();

			root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}

	}


	// Helper Methods //

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
		}
	}

}

package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import application.sceneController;

public class loginRegController implements Initializable {

    //Treemap for Usernames & Passwords
        //This is the only place passwords are stored outside of User's account file.
    private TreeMap<String, String> userPasses = new TreeMap<>();
    private User currentUser;
    private Stage stage;

    @FXML
    private TextField userField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField cardNumField;
    @FXML
    private Label errorLbl;

    //Initialization//

    /**
     * Initializes the userpasses from the account files.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getUserPasses();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    //Buttons CLicked//

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
            continueHome(event);
        }
    }

    /**
     * Continues program to the Registration Page.
     * @param event
     * @throws IOException
     */
    public void createAccClicked(ActionEvent event) throws IOException
    {
        continueTo(event, "registrationPage.fxml");
    }
    /**
     * Registers the user's new account with the parameters provided in the
     * 	text field and password fields.
     * @param event
     * @throws IOException
     */
    public void registerAccClicked(ActionEvent event) throws IOException
    {
        if (userPasses.containsKey(userField.getText()))
        {
            errorLbl.setText("This username is already in use");
            return;
        }

        if (!cardNumField.getText().matches("[0-9]+"))
        {
            errorLbl.setText("Invalid Card Number: Numbers only");
            return;
        }
        else if (cardNumField.getText().length() != 16)
        {
            errorLbl.setText("Invalid Card Number: Must Be 16 Characters");
            return;
        }

        if (Objects.equals(userField.getText(), passwordField.getText()))
        {
            errorLbl.setText("Username cannot be the same as password");
            return;
        }
        else if (Objects.equals(userField.getText(), cardNumField.getText()))
        {
            errorLbl.setText("Username cannot be the same as Card Number");
            return;
        }
        else if (Objects.equals(passwordField.getText(), cardNumField.getText()))
        {
            errorLbl.setText("Password cannot be the same as Card Number");
            return;
        }

        if (Objects.equals(passwordField.getText(), confirmPasswordField.getText()))
        {

            currentUser = new User(userField.getText(),
                    passwordField.getText(),
                    cardNumField.getText());
            currentUser.createAccountFile();
            getUserPasses();
            continueHome(event);
        }

    }

    //Helper Methods//

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

    /**
     * Continues to Home Page, calls sceneController's initdata function and passes the data.
     * @param event
     * @throws IOException
     */

    private void continueHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        sceneController controller = loader.getController();
        controller.initData(currentUser, userPasses);

        stage.show();
    }
    /**
     * Sets the scene to the .fxml file whose name was passed in.
     * 	Easy implementation for a button to "Go To" a page.
     * @param event
     * @param fileName Name of the file to go to
     * @throws IOException
     */
    private void continueTo(ActionEvent event, String fileName) throws IOException
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fileName)));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

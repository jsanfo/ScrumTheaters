package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class sceneController 
{
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void titleClicked(MouseEvent event) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("homePage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void homeClicked(ActionEvent event) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("homePage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void catalogClicked(ActionEvent event) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("catalogPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	public void ticketsClicked(ActionEvent event) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("ticketPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void accountClicked(ActionEvent event) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("accountPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void loginClicked(ActionEvent event) throws IOException
	{
		//No Login Yet
			//Will log in the user using the username/email and the password
			//Then, take them to the home page
	}
	
	public void createAccClicked(ActionEvent event) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("registrationPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}

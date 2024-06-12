package com.parthenope.project;

import static com.parthenope.project.constants.Constants.PATH_VISTE;

import java.nio.file.Path;

import com.parthenope.project.entities.Autostrada;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource(Path.of(PATH_VISTE, "login.fxml").toString()));
		primaryStage.setTitle("SICVE Autostradale");
		primaryStage.setScene(new Scene(root, 400, 300));
		primaryStage.show();
	}

	public static void main(String[] args)
	{

		Autostrada.getInstance();

		launch(args);
	}
}
package com.parthenope.project;

import static com.parthenope.project.constants.Constants.PATH_RISORSE;
import static com.parthenope.project.constants.Constants.PATH_VISTE;
import static com.parthenope.project.constants.Constants.SUCCESS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.parthenope.project.entities.Utente;
import com.parthenope.project.exceptions.CreazioneVeicoloException;
import com.parthenope.project.exceptions.VeicoloNonSupportato;
import com.parthenope.project.login.AutenticaUtenteAbstract;
import com.parthenope.project.login.AutenticazioneFile;
import com.parthenope.project.utils.Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController
{

	@FXML
	private Text actionTarget;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField usernameField;

	private static final Path PATH_UTENTE = Paths.get(PATH_RISORSE, "%s");

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws IOException
	{
		System.out.println(passwordField.getCharacters().toString());

		AutenticaUtenteAbstract autenticazioneUtente = new AutenticazioneFile(usernameField.getCharacters().toString(),
				passwordField.getCharacters().toString());
		Map.Entry<String, Utente> utente = autenticaUtente(autenticazioneUtente);
		Path filePath = Path.of(String.format(PATH_UTENTE.toString(), utente.getValue().getUsername()));
		if (Files.exists(filePath))
		{
			try
			{
				utente.getValue().setVeicolo(Utils.ottieniVeicolo(filePath));
			} catch (VeicoloNonSupportato | CreazioneVeicoloException e)
			{
				e.printStackTrace();
			}
		}

		if (!utente.getKey().equals(SUCCESS))
		{
			actionTarget.setText(utente.getKey());
			actionTarget.getStyleClass().add("errortext");
			return;
		}

		String vistaDaCaricare = utente.getValue().getRuolo().equalsIgnoreCase("Admin") ? "admin_home.fxml"
				: "utente_home.fxml";
		String titolo = utente.getValue().getRuolo().equalsIgnoreCase("Admin") ? "Admin Scene" : "User Scene";
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Path.of(PATH_VISTE, vistaDaCaricare).toString()));
		Stage stage = new Stage();
		stage.setTitle(titolo);
		Scene scene = new Scene(fxmlLoader.load(), 900, 900);
		stage.setScene(scene);
		ControllerInitializable uc = fxmlLoader.getController();
		uc.initData(utente.getValue());
		stage.show();
		Stage loginStage = (Stage) actionTarget.getScene().getWindow();
		loginStage.close();

	}

	@FXML
	protected void registrati(ActionEvent event) throws IOException
	{
		String vistaDaCaricare = "registrati.fxml";
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Path.of(PATH_VISTE, vistaDaCaricare).toString()));
		Stage stage = new Stage();
		stage.setTitle("Nuovo utente");
		Scene scene = new Scene(fxmlLoader.load(), 900, 900);
		stage.setScene(scene);
		stage.show();
		Stage loginStage = (Stage) actionTarget.getScene().getWindow();
		loginStage.close();
	}

	private Map.Entry<String, Utente> autenticaUtente(AutenticaUtenteAbstract autenticazioneUtente)
	{
		return autenticazioneUtente.login();
	}
}

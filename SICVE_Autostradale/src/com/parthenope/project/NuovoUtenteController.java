package com.parthenope.project;

import static com.parthenope.project.constants.Constants.PATH_RISORSE;
import static com.parthenope.project.constants.Constants.PATH_VISTE;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.parthenope.project.utils.Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NuovoUtenteController
{
	@FXML
	private TextField nome;
	@FXML
	private TextField cognome;
	@FXML
	private TextField password;
	@FXML
	private TextField username;
	@FXML
	private TextField ripetiPassword;
	@FXML
	private ComboBox<String> ruolo;
	@FXML
	private Text actionTarget;
	private static final String ERROR_MESSAGE = "Tutti i campi sono obbligatori";
	private static final String ERROR_MESSAGE_2 = "Le password devono coincidere";
	private static final Path PATH_UTENTE = Paths.get(PATH_RISORSE, "%s");

	@FXML
	protected void salva(ActionEvent action)
	{
		actionTarget.setText("");
		List<String> fields = List.of(nome.getText(), cognome.getText(), password.getText(), username.getText(),
				ripetiPassword.getText());

		boolean error = false;
		String errorMessage = "";
		for (String field : fields)
		{
			if (field == null || field.equals(""))
			{
				error = true;
				errorMessage = ERROR_MESSAGE;
			}
		}
		if (ruolo == null || ruolo.getValue().equals(""))
		{
			error = true;
			errorMessage = ERROR_MESSAGE;
		}

		if (password != null && ripetiPassword != null && !password.getText().equals(ripetiPassword.getText()))
		{
			error = true;
			errorMessage = ERROR_MESSAGE_2;
		}

		if (error)
		{
			actionTarget.setText(errorMessage);
			return;
		}

		Alert alert = Utils.confirmationAlert("Vuoi salvare questo utente?");
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES)
		{
			salvaUtente();
		}

	}

	private void salvaUtente()
	{
		String fileLocation = String.format(PATH_UTENTE.toString(), "utenti");
		String line = String.format("%s %s %s %s %s\n", nome.getText(), cognome.getText(), username.getText(),
				password.getText(), ruolo.getValue());
		FileWriter fw = null;
		BufferedWriter br = null;
		PrintWriter pr = null;
		try
		{

			// False -> Nuovo file
			fw = new FileWriter(fileLocation, true);
			br = new BufferedWriter(fw);
			pr = new PrintWriter(br);
			pr.println(line);
			Alert alert2 = Utils.successAlert("Utente salvato");

			alert2.showAndWait();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				pr.close();
				br.close();
				fw.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@FXML
	protected void indietro(ActionEvent action)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Path.of(PATH_VISTE, "login.fxml").toString()));
		Stage stage = new Stage();
		stage.setTitle("Login");
		Scene scene = null;
		try
		{
			scene = new Scene(fxmlLoader.load(), 400, 400);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		stage.setScene(scene);
		stage.show();
		Stage loginStage = (Stage) username.getScene().getWindow();
		loginStage.close();
	}
}

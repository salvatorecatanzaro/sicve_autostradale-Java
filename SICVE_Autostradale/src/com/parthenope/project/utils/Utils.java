package com.parthenope.project.utils;

import static com.parthenope.project.constants.Constants.AUTO;
import static com.parthenope.project.constants.Constants.CAMION;
import static com.parthenope.project.constants.Constants.MOTO;
import static com.parthenope.project.constants.Constants.PATH_RISORSE;
import static com.parthenope.project.constants.Constants.TUTOR_ID;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.parthenope.project.entities.ConcreteAutoBuilder;
import com.parthenope.project.entities.ConcreteCamionBuilder;
import com.parthenope.project.entities.ConcreteMotoBuilder;
import com.parthenope.project.entities.Utente;
import com.parthenope.project.entities.Veicolo;
import com.parthenope.project.entities.VeicoloBuilder;
import com.parthenope.project.exceptions.CreazioneVeicoloException;
import com.parthenope.project.exceptions.FormException;
import com.parthenope.project.exceptions.VeicoloNonSupportato;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Utils
{

	public static Map<String, Utente> getUsersFromFile()
	{
		Map<String, Utente> result = new HashMap<>();
		BufferedReader reader;

		try
		{
			StringBuilder filepath = new StringBuilder(PATH_RISORSE);
			filepath.append("/utenti"); // TODO QUESTO SU WINDOWS NON FUNZIONA
			reader = new BufferedReader(new FileReader(filepath.toString()));
			String line;

			while ((line = reader.readLine()) != null)
			{
				System.out.println(line);
				if (line == null || line.isBlank() || line.trim().startsWith("//"))
					continue; // vai alla prossima linea

				String[] utenteCorrenteStringArr = line.split(" ");
				Utente utente = new Utente(utenteCorrenteStringArr);
				result.put(utente.getUsername(), utente);
			}

			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	public static boolean listKeyStartsWith(List<String> list, String s)
	{
		for (String currentString : list)
		{
			if (s.startsWith(currentString))
				return true;
		}
		return false;
	}

	public static void bindFormData(Map<String, Object> map)
	{

	}

	public static List<Map<String, Object>> salvaForm(ActionEvent e, GridPane gridPane) throws FormException
	{
		List<String> campiDaSaltare = List.of("Aggiungi autovelox", "Salva Tratta");

		Iterator<Node> iterator = gridPane.getChildren().iterator();
		List<Map<String, Object>> result = new ArrayList<>();
		Map<String, Object> innerMap = new HashMap<>();

		while (iterator.hasNext())
		{
			Node node = iterator.next();
			if (!(node instanceof Label))
			{
				continue; // skipping this field
			}
			Label label = (Label) node;
			if (label.getText().startsWith(TUTOR_ID))
			{
				innerMap = new HashMap<>(); // se ci sono più TUTOR_ID sono in modifica e devo creare una nuova inner
											// map
				result.add(innerMap);
				innerMap.put(TUTOR_ID, label.getText().split(" ")[2]);
				continue;
			}
			if (label.getText().startsWith("Autovelox"))
			{
				System.out.println("Autovelox" + label.getText());
				iterator.next(); // Il prossimo campo è "Limite", lo salto
			}
			if (Utils.listKeyStartsWith(campiDaSaltare, label.getText()))
			{
				continue; // field not needed
			}

			node = iterator.next();
			if (!(node instanceof TextField) && !(node instanceof CheckBox) && !(node instanceof ComboBox))
			{
				throw new FormException(
						"Malformed input: After the previous label there should be a TextField, CheckBox, ComboBox");
			}

			innerMap.put(label.getText(), node);

		}
		// Se la innerMap è piena e result è vuoto significa che siamo in inserimento
		if (result.isEmpty() && !innerMap.isEmpty())
		{
			result.add(innerMap);

		}
		System.out.println(result);
		return result;

	}

	public static Alert confirmationAlert(String message)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);

		DialogPane dialogPane = alert.getDialogPane();
		// set style
		dialogPane.setStyle(
				"-fx-background-color: #181818; -fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;");
		dialogPane.lookup(".content.label").setStyle(
				"-fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;");
		dialogPane.getStyleClass().remove("alert");

		GridPane grid = (GridPane) dialogPane.lookup(".header-panel");
		for (Node node : grid.getChildren())
		{
			if (node instanceof Label)
			{
				Label label = (Label) node;
				label.setStyle(
						"-fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;");
			}
		}
		grid.setStyle(
				"-fx-background-color: #181818; -fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;");

		ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
		buttonBar.setStyle("-fx-background-color: transparent; ");
		dialogPane.lookup(".content.label").setStyle(
				"-fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;");
		buttonBar.getButtons().forEach(b -> b.setStyle(
				"-fx-background-color: #24A0ED; -fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;"));

		return alert;
	}

	public static Alert warningAlert(String message)
	{
		Alert alert = new Alert(AlertType.WARNING, message, ButtonType.OK);

		DialogPane dialogPane = alert.getDialogPane();
		// set style
		dialogPane.setStyle(
				"-fx-background-color: #181818; -fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;");
		dialogPane.lookup(".content.label").setStyle(
				"-fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;");
		dialogPane.getStyleClass().remove("alert");

		GridPane grid = (GridPane) dialogPane.lookup(".header-panel");
		for (Node node : grid.getChildren())
		{
			if (node instanceof Label)
			{
				Label label = (Label) node;
				label.setStyle(
						"-fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;");
			}
		}
		grid.setStyle(
				"-fx-background-color: #181818; -fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;");

		ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
		buttonBar.setStyle("-fx-background-color: transparent; ");
		dialogPane.lookup(".content.label").setStyle(
				"-fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;");
		buttonBar.getButtons().forEach(b -> b.setStyle(
				"-fx-background-color: #24A0ED; -fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;"));

		return alert;
	}

	public static Alert successAlert(String message)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.OK);
		DialogPane dialogPane = alert.getDialogPane();
		// set style
		dialogPane.setStyle(
				"-fx-background-color: #181818; -fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;");
		dialogPane.lookup(".content.label").setStyle(
				"-fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;");
		dialogPane.getStyleClass().remove("alert");

		GridPane grid = (GridPane) dialogPane.lookup(".header-panel");
		for (Node node : grid.getChildren())
		{
			if (node instanceof Label)
			{
				Label label = (Label) node;
				label.setStyle(
						"-fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;");
			}
		}
		grid.setStyle(
				"-fx-background-color: #181818; -fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;");

		ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
		buttonBar.setStyle("-fx-background-color: transparent; ");
		dialogPane.lookup(".content.label").setStyle(
				"-fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;");
		buttonBar.getButtons().forEach(b -> b.setStyle(
				"-fx-background-color: #24A0ED; -fx-text-base-color: white; -fx-fill: WHITE; -fx-text-base-color: WHITE; -fx-text-fill: WHITE;"));
		return alert;
	}

	/**
	 * Questo metodo carica il veicolo dal file indicato
	 * 
	 * @param filePath il file dove sono presenti le informazioni sul veicolo
	 * @return
	 * @throws VeicoloNonSupportato
	 * @throws CreazioneVeicoloException
	 */
	public static Veicolo ottieniVeicolo(Path filePath) throws VeicoloNonSupportato, CreazioneVeicoloException
	{
		List<String> lines = new ArrayList<>();
		try
		{
			lines.addAll(Files.readAllLines(new File(filePath.toString()).toPath(), Charset.defaultCharset()));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		VeicoloBuilder builder = null;
		for (String line : lines)
		{
			if (line.trim().startsWith("#") || line.trim().startsWith("//"))
				continue; // non abbiamo bisogno di questa linea
			String[] veicoloArray = line.split(" ");

			switch (veicoloArray[4])
			{
			case AUTO:
				builder = new ConcreteAutoBuilder();
				break;
			case MOTO:
				builder = new ConcreteMotoBuilder();
				break;
			case CAMION:
				builder = new ConcreteCamionBuilder();
				break;
			default:
				throw new VeicoloNonSupportato("Questo veicolo non è supportato dall'applicazione");

			}
			builder.casaAutomobilistica(veicoloArray[1]);
			builder.targa(veicoloArray[0]);
			builder.numeroRuote(Integer.parseInt(veicoloArray[3]));
			builder.velocitaMassimaVeicolo(Integer.parseInt(veicoloArray[2]));
			builder.tipoVeicolo(veicoloArray[4]);

		}

		return builder.build();
	}

	public static double calcolaTempoImpiegato(int lunghezzaTrattaAutovelox, double velocita)
	{
		return (lunghezzaTrattaAutovelox / velocita);
	}

	public static void initSidebar(VBox sidebar, List<Button> buttons)
	{

	}

	public static boolean checkNumber(char[] charArray)
	{

		for (char c : charArray)
		{
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}
}

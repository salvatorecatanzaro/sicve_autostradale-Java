package com.parthenope.project;

import static com.parthenope.project.constants.Constants.AGGIUNGI_AUTOVELOX;
import static com.parthenope.project.constants.Constants.NOME_STAZIONE_ENTRATA;
import static com.parthenope.project.constants.Constants.NOME_STAZIONE_USCITA;
import static com.parthenope.project.constants.Constants.PATH_RISORSE;
import static com.parthenope.project.constants.Constants.PATH_VISTE;
import static com.parthenope.project.constants.Constants.SALVA_TRATTA;
import static com.parthenope.project.constants.Constants.TUTOR_ATTIVO;
import static com.parthenope.project.constants.StyleConstants.ACTION_BUTTON;
import static com.parthenope.project.constants.StyleConstants.BORDER_PANE;
import static com.parthenope.project.constants.StyleConstants.GRID_PANE;
import static com.parthenope.project.constants.StyleConstants.MENU_TEXT;
import static com.parthenope.project.constants.StyleConstants.SCROLL_PANE;
import static com.parthenope.project.constants.StyleConstants.SIDEBAR;
import static com.parthenope.project.constants.StyleConstants.SIDEBAR_BUTTON;
import static com.parthenope.project.constants.StyleConstants.TEXT_WHITE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.parthenope.project.entities.Autostrada;
import com.parthenope.project.entities.Autovelox;
import com.parthenope.project.entities.TrattoAutostrada;
import com.parthenope.project.entities.Tutor;
import com.parthenope.project.entities.Utente;
import com.parthenope.project.exceptions.FormException;
import com.parthenope.project.exceptions.TutorNonEsisteException;
import com.parthenope.project.utils.FormBinding;
import com.parthenope.project.utils.InserisciFormTutor;
import com.parthenope.project.utils.ModificaFormTutor;
import com.parthenope.project.utils.Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminController implements ControllerInitializable
{
	private Utente utente;
	@FXML
	private Text username;
	@FXML
	private TextField usernameField;
	@FXML
	private VBox sidebar;
	@FXML
	private GridPane gridPane;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private BorderPane borderPane;
	@FXML
	private Text usernameLabel;

	private int autoveloxRow = 4;
	private static final Path PATH_UTENTE = Paths.get(PATH_RISORSE, "%s");

	// Usata per gestire l'eliminazione dei nodi quando si cancellano gli autovelox
	Map<String, List<Control>> nodeMap = new HashMap<>();

	@Override
	public void initData(Object o)
	{
		Utente utente = (Utente) o;
		this.utente = utente;
		this.username.setText(utente.getUsername());

		// Preferenze
		scrollPane.setPrefWidth(borderPane.getPrefWidth());
		sidebar.setPrefWidth(200);
		gridPane.setHgap(3);
		gridPane.setVgap(5);

		// Creo bottone sidebar inserisci tratta e gli lego un evento
		Button inserisciTratta = new Button("Inserisci tratta");
		inserisciTratta.setPrefWidth(sidebar.getPrefWidth());
		inserisciTratta.setOnAction(e -> creaFormInserisciTratta(e));

		// Creo bottone sidebar modifica tratta e gli lego un evento
		Button modificaTratta = new Button("Modifica tratta");
		modificaTratta.setPrefWidth(sidebar.getPrefWidth());
		modificaTratta.setOnAction(e -> creaFormModificaTratta(e));

		// Creo bottone sidebar ottieni statistiche e gli lego un evento
		Button ottieniStatisticheTratta = new Button("Ottieni statistiche tratta");
		ottieniStatisticheTratta.setPrefWidth(sidebar.getPrefWidth());
		ottieniStatisticheTratta.setOnAction(e -> ottieniStatistiche(e));

		// Creo text MENU
		Text menu = new Text("-MENU-");
		menu.getStyleClass().add(MENU_TEXT);
		Text empty1 = new Text("");

		this.sidebar.getChildren().add(empty1);
		this.sidebar.getChildren().add(menu);
		this.sidebar.getChildren().add(inserisciTratta);
		this.sidebar.getChildren().add(modificaTratta);
		this.sidebar.getChildren().add(ottieniStatisticheTratta);

		// Init stylesheets
		sidebar.getStyleClass().add(SIDEBAR);
		gridPane.getStyleClass().add(GRID_PANE);
		inserisciTratta.getStyleClass().add(SIDEBAR_BUTTON);
		ottieniStatisticheTratta.getStyleClass().add(SIDEBAR_BUTTON);
		modificaTratta.getStyleClass().add(SIDEBAR_BUTTON);
		borderPane.getStyleClass().add(BORDER_PANE);
		scrollPane.getStyleClass().add(SCROLL_PANE);
		borderPane.getStyleClass().add(BORDER_PANE);

	}

	protected void creaFormInserisciTratta(ActionEvent event)
	{

		nodeMap = new HashMap<>();
		gridPane.getChildren().clear();
		autoveloxRow = 4;

		// Prima riga
		gridPane.add(new Label(TUTOR_ATTIVO), 0, 0);
		gridPane.add(new CheckBox(), 1, 0);

		// Seconda riga
		gridPane.add(new Label(NOME_STAZIONE_ENTRATA), 0, 1);
		gridPane.add(new TextField(), 1, 1);

		// Terza riga
		gridPane.add(new Label(NOME_STAZIONE_USCITA), 0, 2);
		gridPane.add(new TextField(), 1, 2);

		// Quarta riga
		Button aggiungiAutovelox = new Button("+");
		aggiungiAutovelox.getStyleClass().add(ACTION_BUTTON);
		gridPane.add(new Label(AGGIUNGI_AUTOVELOX), 0, 3);
		gridPane.add(aggiungiAutovelox, 1, 3);

		// Questo Id verrà utilizzato per la mappa nella fase di insert
		String tmpTutorId = UUID.randomUUID().toString();
		aggiungiAutovelox.setOnAction(e -> aggiungiAutoveloxForm(e, null, tmpTutorId));

		// Autovelox stazione entrata
		aggiungiAutoveloxForm(null, null, tmpTutorId);
		// Autovelox stazione uscita
		aggiungiAutoveloxForm(null, null, tmpTutorId);

		// bottone inserimento form
		Button salvaTratta = new Button(SALVA_TRATTA);
		gridPane.add(salvaTratta, 6, 0);
		salvaTratta.getStyleClass().add(ACTION_BUTTON);

		salvaTratta.setOnAction(e ->
		{
			try
			{

				List<Map<String, Object>> formData = Utils.salvaForm(e, gridPane);

				FormBinding ifo = new InserisciFormTutor();
				for (Map<String, Object> tutorForm : formData)
				{

					if (((InserisciFormTutor) ifo).validateForm(tutorForm))
					{
						Alert alert = Utils.confirmationAlert("Vuoi salvare questa tratta?");
						alert.showAndWait();

						if (alert.getResult() == ButtonType.YES)
						{
							ifo.bindForm(tutorForm);
						}
						Alert alert2 = Utils.successAlert("Tratta salvata");
						alert2.showAndWait();
					}
				}

			} catch (FormException e1)
			{
				e1.printStackTrace();
			} catch (TutorNonEsisteException e1)
			{
				e1.printStackTrace();
			}
		});

		// Aggiungo stile alle label
		for (

		Node n : gridPane.getChildren())
			if (n instanceof Label)
				n.getStyleClass().add(TEXT_WHITE);
	}

	private void aggiungiAutoveloxForm(ActionEvent e, Autovelox autoveloxObj, String tutorId)
	{

		int autoveloxCount = 0;
		for (Node node : gridPane.getChildren())
		{
			if (node instanceof Label && ((Label) node).getText().startsWith("Autovelox"))
				autoveloxCount++;
		}
		Label autovelox = new Label(String.format("Autovelox - %s",
				autoveloxObj == null ? String.valueOf(autoveloxCount) : autoveloxObj.getId()));
		autovelox.getStyleClass().add("textwhite");

		Label limiteVelocita = new Label(String.format("Limite %d", autoveloxCount));
		limiteVelocita.getStyleClass().add("textwhite");

		TextField limiteVelocitaInput = new TextField();
		if (autoveloxObj != null)
			limiteVelocitaInput.setText(String.valueOf(autoveloxObj.getLimiteVelocita()));
		Button cancella = new Button("-");
		cancella.getStyleClass().add("actionbutton");

		List<Control> autoveloxComponents = new ArrayList<>();
		autoveloxComponents.add(autovelox);
		autoveloxComponents.add(limiteVelocita);
		autoveloxComponents.add(limiteVelocitaInput);
		autoveloxComponents.add(cancella);

		gridPane.add(autovelox, 0, ++autoveloxRow);
		autoveloxRow++;
		gridPane.add(limiteVelocita, 0, autoveloxRow);
		gridPane.add(limiteVelocitaInput, 1, autoveloxRow);
		gridPane.add(cancella, 2, autoveloxRow);

		cancella.setOnAction(
				deleteEvent -> eliminaElementoCorrenteDaGriglia(deleteEvent, autoveloxComponents, tutorId));
		if (nodeMap.get(tutorId) == null)
		{

			List<Control> components = new ArrayList<>();
			components.add(autovelox);
			components.add(limiteVelocita);
			components.add(limiteVelocitaInput);
			components.add(cancella);

			nodeMap.put(tutorId, components);
		} else
		{
			nodeMap.get(tutorId).addAll(autoveloxComponents);
		}

	}

	private void eliminaElementoCorrenteDaGriglia(ActionEvent e, List<Control> elementiAssociati, String tutorId)
	{

		int autoveloxCount = 0;

		for (Control node : nodeMap.get(tutorId))
		{
			if (node instanceof Label && ((Label) node).getText().startsWith("Autovelox"))
				autoveloxCount++;
		}

		if (autoveloxCount <= 2)
		{
			System.out.println("Ci devono essere minimo due autovelox");
			return;
		}

		elementiAssociati.forEach(element ->
		{
			// Delete elements from node map and from grid
			nodeMap.get(tutorId).remove(element);
			gridPane.getChildren().remove(element);
		});

	}

	private void creaFormModificaTratta(ActionEvent event)
	{
		nodeMap = new HashMap<>();

		Autostrada autostrada = Autostrada.getInstance();
		if (autostrada.getTrattiAutostrada().isEmpty())
		{
			System.out.println("Errore non ci sono tutor attivi al momento");
			return;
		}

		gridPane.getChildren().clear();
		autoveloxRow = 4;

		int row = 0;
		for (TrattoAutostrada trattoAutostrada : autostrada.getTrattiAutostrada())
		{

			Tutor tutor = trattoAutostrada.getTutor();
			gridPane.add(new Label(String.format("Tutor ID %s", tutor.getId())), 0, row);

			row++;
			// Prima riga
			gridPane.add(new Label(TUTOR_ATTIVO), 0, row);
			CheckBox checkBox = new CheckBox();
			if (tutor.isAttivo())
				checkBox.fire();
			gridPane.add(checkBox, 1, row);

			// Seconda riga
			row++;
			gridPane.add(new Label(NOME_STAZIONE_ENTRATA), 0, row);
			TextField stazioneEntrata = new TextField();
			stazioneEntrata.setText(tutor.getStazioneEntrata().getNome());
			gridPane.add(stazioneEntrata, 1, row);

			// Terza riga
			row++;
			gridPane.add(new Label(NOME_STAZIONE_USCITA), 0, row);
			TextField stazioneUscita = new TextField();
			stazioneUscita.setText(tutor.getStazioneUscita().getNome());
			gridPane.add(stazioneUscita, 1, row);

			row++;
			autoveloxRow = row;
			// Righe autovelox
			for (Autovelox autovelox : tutor.getListaAutovelox())
			{
				aggiungiAutoveloxForm(null, autovelox, tutor.getId());
				autoveloxRow++;
				row += 3;
			}

			row++;

			Text spazio = new Text(" ");
			gridPane.add(spazio, 0, row);

			row++;

			Text spazio2 = new Text(" ");
			gridPane.add(spazio2, 0, row);

			row++;

			// bottone inserimento form
			Button salvaTratta = new Button(SALVA_TRATTA);
			salvaTratta.getStyleClass().add("actionbutton");
			gridPane.add(salvaTratta, 6, 0);
			salvaTratta.setOnAction(e ->
			{
				try
				{

					List<Map<String, Object>> formData = Utils.salvaForm(e, gridPane);
					FormBinding mf = new ModificaFormTutor();
					boolean error = false;
					for (Map<String, Object> tutorForm : formData)
					{

						if (!((ModificaFormTutor) mf).validateForm(tutorForm))
						{
							error = true;
						}

					}
					if (!error)
					{
						Alert alert = Utils.confirmationAlert("Vuoi modificare i dati?");
						alert.showAndWait();
						if (alert.getResult() == ButtonType.YES)
						{
							for (Map<String, Object> tutorForm : formData)
							{

								mf.bindForm(tutorForm);
							}
							Alert alert2 = Utils.successAlert("Dati modificati");
							alert2.showAndWait();
						}
					}

				} catch (FormException e1)
				{
					e1.printStackTrace();
				} catch (TutorNonEsisteException e1)
				{
					e1.printStackTrace();
				}
			});
		}

		// Aggiungo stile alle label
		for (

		Node n : gridPane.getChildren())
			if (n instanceof Label)
				n.getStyleClass().add(TEXT_WHITE);

	}

	private void ottieniStatistiche(ActionEvent event)
	{
		System.out.println("statistiche tratta");
		String fileLocation = String.format(PATH_UTENTE.toString(), "statistiche");
		List<String> lines = null;
		try
		{
			if (Files.exists(Path.of(fileLocation)))
				lines = Files.readAllLines(Path.of(fileLocation));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		gridPane.getChildren().clear();
		autoveloxRow = 4;

		int rowIdx = 0;
		for (String line : lines)
		{
			gridPane.add(new Label(line), 0, rowIdx++);
		}

		// Aggiungo stile alle label
		for (Node n : gridPane.getChildren())
			if (n instanceof Label)
				n.getStyleClass().add(TEXT_WHITE);
	}

	@FXML
	private void logout(ActionEvent event)
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

	public Utente getUtente()
	{
		return utente;
	}

	public void setUtente(Utente utente)
	{
		this.utente = utente;
	}

	public Text getUsername()
	{
		return username;
	}

	public void setUsername(Text username)
	{
		this.username = username;
	}

}

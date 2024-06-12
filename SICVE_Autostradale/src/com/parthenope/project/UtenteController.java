package com.parthenope.project;

import static com.parthenope.project.constants.Constants.AUTO;
import static com.parthenope.project.constants.Constants.CAMION;
import static com.parthenope.project.constants.Constants.CASA_AUTOMOBILISTICA;
import static com.parthenope.project.constants.Constants.MOTO;
import static com.parthenope.project.constants.Constants.NUMERO_RUOTE;
import static com.parthenope.project.constants.Constants.PATH_RISORSE;
import static com.parthenope.project.constants.Constants.PATH_VISTE;
import static com.parthenope.project.constants.Constants.SALVA_VEICOLO;
import static com.parthenope.project.constants.Constants.TARGA;
import static com.parthenope.project.constants.Constants.TIPO_VEICOLO;
import static com.parthenope.project.constants.Constants.VELOCITA_MASSIMA_VEICOLO;
import static com.parthenope.project.constants.StyleConstants.ACTION_BUTTON;
import static com.parthenope.project.constants.StyleConstants.BORDER_PANE;
import static com.parthenope.project.constants.StyleConstants.COMBOBOX_ACTION;
import static com.parthenope.project.constants.StyleConstants.GRID_PANE;
import static com.parthenope.project.constants.StyleConstants.MENU_TEXT;
import static com.parthenope.project.constants.StyleConstants.SCROLL_PANE;
import static com.parthenope.project.constants.StyleConstants.SIDEBAR;
import static com.parthenope.project.constants.StyleConstants.SIDEBAR_BUTTON;
import static com.parthenope.project.constants.StyleConstants.TEXT_WHITE;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.parthenope.project.entities.Autostrada;
import com.parthenope.project.entities.Autovelox;
import com.parthenope.project.entities.AutoveloxImpl;
import com.parthenope.project.entities.TrattoAutostrada;
import com.parthenope.project.entities.Tutor;
import com.parthenope.project.entities.Utente;
import com.parthenope.project.exceptions.FormException;
import com.parthenope.project.exceptions.VeicoloNonSupportato;
import com.parthenope.project.utils.FormBinding;
import com.parthenope.project.utils.InserisciFormVeicolo;
import com.parthenope.project.utils.Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UtenteController implements ControllerInitializable
{
	private static final Path PATH_UTENTE = Paths.get(PATH_RISORSE, "%s");

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

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws IOException
	{

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

	@FXML
	protected void sss(ActionEvent event) throws IOException
	{
		System.out.println("");
	}

	@Override
	public void initData(Object o)
	{
		Utente u = (Utente) o;
		this.utente = u;
		this.username.setText(u.getUsername());

		// Preferenze
		scrollPane.setPrefWidth(borderPane.getPrefWidth());
		sidebar.setPrefWidth(200);
		gridPane.setHgap(3);
		gridPane.setVgap(5);

		// Creo bottone sidebar inserisci tratta e gli lego un evento
		Button operazioniUtente = new Button("Operazioni utente");
		operazioniUtente.setPrefWidth(sidebar.getPrefWidth());
		operazioniUtente.setOnAction(e -> operazioniUtenteAction(e));

		// Creo bottone sidebar modifica tratta e gli lego un evento
		Button messaggiRicevuti = new Button("Messaggi ricevuti");
		messaggiRicevuti.setPrefWidth(sidebar.getPrefWidth());
		messaggiRicevuti.setOnAction(e -> messaggiRicevutiAction(e));

		// Creo bottone sidebar modifica tratta e gli lego un evento
		Button ilMioVeicolo = new Button("Il mio veicolo");
		ilMioVeicolo.setPrefWidth(sidebar.getPrefWidth());
		ilMioVeicolo.setOnAction(e -> ilMioVeicoloAction(e));
		// Creo text MENU
		Text menu = new Text("-MENU-");
		menu.getStyleClass().add(MENU_TEXT);

		Text empty1 = new Text("");

		this.sidebar.getChildren().add(empty1);
		this.sidebar.getChildren().add(menu);
		this.sidebar.getChildren().add(operazioniUtente);
		this.sidebar.getChildren().add(messaggiRicevuti);
		this.sidebar.getChildren().add(ilMioVeicolo);

		// Init stylesheets
		sidebar.getStyleClass().add(SIDEBAR);
		gridPane.getStyleClass().add(GRID_PANE);
		messaggiRicevuti.getStyleClass().add(SIDEBAR_BUTTON);
		operazioniUtente.getStyleClass().add(SIDEBAR_BUTTON);
		ilMioVeicolo.getStyleClass().add(SIDEBAR_BUTTON);
		borderPane.getStyleClass().add(BORDER_PANE);
		scrollPane.getStyleClass().add(SCROLL_PANE);
		borderPane.getStyleClass().add(BORDER_PANE);
	}

	protected void operazioniUtenteAction(ActionEvent event)
	{
		gridPane.getChildren().clear();
		System.out.println("Operazioni utente");

		Autostrada autostrada = Autostrada.getInstance();
		int row = 0;
		for (TrattoAutostrada trattoAutostrada : autostrada.getTrattiAutostrada())
		{
			Tutor tutor = trattoAutostrada.getTutor();
			gridPane.add(new Label(String.format("Tratta:  %s - %s", tutor.getStazioneEntrata().getNome(),
					tutor.getStazioneUscita().getNome())), 0, row);
			row++;
			Button percorriTratta = new Button("Percorri tratta");
			percorriTratta.getStyleClass().add(ACTION_BUTTON);
			gridPane.add(percorriTratta, 6, row++);
			percorriTratta.setOnAction(e ->
			{
				percorriTratta(e, tutor);
			});

		}

		// Aggiungo stile alle label
		for (Node n : gridPane.getChildren())
			if (n instanceof Label)
				n.getStyleClass().add(TEXT_WHITE);
	}

	protected void messaggiRicevutiAction(ActionEvent event)
	{
		gridPane.getChildren().clear();

		int rowIdx = 0;
		for (String avviso : utente.getAvvisi())
		{
			gridPane.add(new Label(String.format("[%s %s] %s", "Avviso:", new Date(), avviso)), 0, rowIdx++);
		}

		// Aggiungo stile alle label
		for (Node n : gridPane.getChildren())
			if (n instanceof Label)
				n.getStyleClass().add(TEXT_WHITE);
	}

	protected void percorriTratta(ActionEvent event, Tutor tutor)
	{
		if (utente.getVeicolo() == null)
		{
			Alert alert = Utils.warningAlert("Crea prima un veicolo");
			alert.showAndWait();
			return;
		}

		if (!tutor.isAttivo())
		{
			System.out.println("Il tutor non è attivo non saranno raccolte informazioni");
			return;
		}
		if (tutor.getListaAutovelox().isEmpty())
			System.out.println("Non ci sono autovelox");

		if (utente.isAvvisiAbilitati())
		{
			utente.getAvvisi().add(String.format("Hai percorso la tratta %s - %s coperta da tutor attivo",
					tutor.getStazioneEntrata().getNome(), tutor.getStazioneUscita().getNome()));
		}

		StringBuffer sb = new StringBuffer("Targa veicolo: ");
		sb.append(utente.getVeicolo().getTarga());
		sb.append("\nTutor: ");
		sb.append(tutor.getId());
		int i = 0;
		for (Autovelox autovelox : tutor.getListaAutovelox())
		{
			sb.append("\nAutovelox: ");
			sb.append(autovelox.getId());
			i++;
			autovelox.autoveloxPercorso(utente.getVeicolo());

			if (i > 1)
			{
				int lunghezzaTrattaAutovelox = (int) (tutor.lunghezzaKmTratta() / tutor.getListaAutovelox().size());
				double velocitaMedia = autovelox.calcolaVelocitaMedia(utente.getVeicolo(), lunghezzaTrattaAutovelox,
						Utils.calcolaTempoImpiegato(lunghezzaTrattaAutovelox,
								utente.getVeicolo().getVelocitaCorrente()));
				sb.append("\nvelocita media: ");
				sb.append(String.valueOf(velocitaMedia));
				autovelox.isLimiteVelocitaMediaSuperato(utente.getVeicolo(), velocitaMedia);
			}
			if (i == tutor.getListaAutovelox().size() - 1)
				((AutoveloxImpl) autovelox).getComputer()
						.inoltraInfrazione(autovelox.getTargaInfrazione().get(utente.getVeicolo().getTarga()));

		}
		sb.append("\n===============");
		String outMsg = sb.toString();
		FileWriter fw = null;
		BufferedWriter br = null;
		PrintWriter pr = null;
		String fileLocation = String.format(PATH_UTENTE.toString(), "statistiche");

		try
		{
			// False -> Nuovo file
			fw = new FileWriter(fileLocation, true);
			br = new BufferedWriter(fw);
			pr = new PrintWriter(br);
			pr.println(outMsg);
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

	protected void ilMioVeicoloAction(ActionEvent event)
	{
		System.out.println("il mio veicolo meno");
		gridPane.getChildren().clear();

		// Prima riga
		gridPane.add(new Label(TARGA), 0, 0);
		TextField targa = new TextField();
		gridPane.add(targa, 1, 0);

		// Seconda riga
		gridPane.add(new Label(NUMERO_RUOTE), 0, 1);
		TextField numeroPorte = new TextField();
		gridPane.add(numeroPorte, 1, 1);

		// Terza riga
		gridPane.add(new Label(CASA_AUTOMOBILISTICA), 0, 2);
		TextField casaAutomobilistica = new TextField();
		gridPane.add(casaAutomobilistica, 1, 2);
		// Quarta riga
		gridPane.add(new Label(VELOCITA_MASSIMA_VEICOLO), 0, 3);
		TextField velocitaMassimaVeicolo = new TextField();
		gridPane.add(velocitaMassimaVeicolo, 1, 3);

		// Quinta riga
		gridPane.add(new Label(TIPO_VEICOLO), 0, 4);
		ObservableList<String> options = FXCollections.observableArrayList(AUTO, MOTO, CAMION);
		final ComboBox<String> comboBox = new ComboBox<>(options);
		comboBox.getStyleClass().add(COMBOBOX_ACTION);
		gridPane.add(comboBox, 1, 4);
		// bottone inserimento form
		Button salvaVeicolo = new Button(SALVA_VEICOLO);
		salvaVeicolo.getStyleClass().add(ACTION_BUTTON);

		if (utente.getVeicolo() != null)
		{
			targa.setText(utente.getVeicolo().getTarga());
			numeroPorte.setText(String.valueOf(utente.getVeicolo().getNumeroRuote()));
			casaAutomobilistica.setText(utente.getVeicolo().getCasaAutomobilistica());
			velocitaMassimaVeicolo.setText(String.valueOf(utente.getVeicolo().getVelocitaMassimaVeicolo()));
			comboBox.setValue(utente.getVeicolo().getTipoVeicolo());
		}

		gridPane.add(salvaVeicolo, 6, 0);
		salvaVeicolo.setOnAction(e ->
		{
			try
			{

				List<Map<String, Object>> formData = Utils.salvaForm(event, gridPane);
				FormBinding ifo = new InserisciFormVeicolo();
				for (Map<String, Object> veicoloForm : formData)
				{

					if (((InserisciFormVeicolo) ifo).validateForm(veicoloForm))
					{
						Alert alert = Utils.confirmationAlert("Vuoi salvare questo veicolo?");
						alert.showAndWait();
						if (alert.getResult() == ButtonType.YES)
						{

							ifo.bindForm(veicoloForm, utente);
							Alert alert2 = Utils.successAlert("Dati salvati");
							alert2.showAndWait();
						}
					}
				}
			} catch (FormException e1)
			{
				e1.printStackTrace();
			} catch (VeicoloNonSupportato e1)
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

	@FXML
	protected void triggerAvvisi(ActionEvent e)
	{
		if (utente.isAvvisiAbilitati())
			utente.setAvvisiAbilitati(false);
		else
			utente.setAvvisiAbilitati(true);
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

}

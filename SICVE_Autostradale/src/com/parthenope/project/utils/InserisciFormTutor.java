package com.parthenope.project.utils;

import static com.parthenope.project.constants.Constants.NOME_STAZIONE_ENTRATA;
import static com.parthenope.project.constants.Constants.NOME_STAZIONE_USCITA;
import static com.parthenope.project.constants.Constants.PATH_RISORSE;
import static com.parthenope.project.constants.Constants.TUTOR_ATTIVO;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.parthenope.project.entities.Autostrada;
import com.parthenope.project.entities.Autovelox;
import com.parthenope.project.entities.AutoveloxImpl;
import com.parthenope.project.entities.AutoveloxMediator;
import com.parthenope.project.entities.AutoveloxMediatorimpl;
import com.parthenope.project.entities.Stazione;
import com.parthenope.project.entities.TrattoAutostrada;
import com.parthenope.project.entities.Tutor;
import com.parthenope.project.exceptions.PersistenzaException;
import com.parthenope.project.exceptions.TutorNonEsisteException;
import com.parthenope.project.exceptions.VeicoloNonSupportato;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class InserisciFormTutor implements FormBinding, FormValidate
{

	private static final String CURRENT_AUTH_METHOD = "File";
	private static final Path PATH_TUTOR = Paths.get(PATH_RISORSE, "tutor");

	@Override
	public void bindForm(Map<String, Object> input) throws TutorNonEsisteException
	{
		/**
		 * Le informazioni saranno salvate in memoria e su un file per consentire la
		 * persistenza dei dati
		 */

		Autostrada autostrada = Autostrada.getInstance();
		TrattoAutostrada trattoAutostrada = new TrattoAutostrada();
		Tutor tutor = new Tutor();
		trattoAutostrada.setTutor(tutor);
		autostrada.getTrattiAutostrada().add(trattoAutostrada);
		tutor.setStazioneEntrata(new Stazione("Entrata", ((TextField) input.get(NOME_STAZIONE_ENTRATA)).getText()));
		tutor.setStazioneUscita(new Stazione("Uscita", ((TextField) input.get(NOME_STAZIONE_USCITA)).getText()));
		tutor.setAttivo(((CheckBox) input.get(TUTOR_ATTIVO)).isSelected());
		AutoveloxMediator mediator = new AutoveloxMediatorimpl();

		for (String s : input.keySet())
		{

			if (!s.startsWith("Autovelox"))
			{
				continue;
			}

			TextField limiteVelocita = (TextField) input.get(s);
			Autovelox autovelox = new AutoveloxImpl(mediator, Integer.parseInt(limiteVelocita.getText()));
			mediator.aggiungiAutovelox(autovelox);
			tutor.getListaAutovelox().add(autovelox);
		}
		IPersistenza p = new PersistenzaFileTutor();
		try
		{
			p.aggiungiInfo(tutor);
		} catch (PersistenzaException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public void bindForm(Map<String, Object> input, Object o) throws VeicoloNonSupportato
	{

	}

}

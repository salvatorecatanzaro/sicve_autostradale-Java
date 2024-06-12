package com.parthenope.project.utils;

import static com.parthenope.project.constants.Constants.AUTO;
import static com.parthenope.project.constants.Constants.CAMION;
import static com.parthenope.project.constants.Constants.CASA_AUTOMOBILISTICA;
import static com.parthenope.project.constants.Constants.MOTO;
import static com.parthenope.project.constants.Constants.NUMERO_RUOTE;
import static com.parthenope.project.constants.Constants.TARGA;
import static com.parthenope.project.constants.Constants.TIPO_VEICOLO;
import static com.parthenope.project.constants.Constants.VELOCITA_MASSIMA_VEICOLO;

import java.util.Map;

import com.parthenope.project.entities.ConcreteAutoBuilder;
import com.parthenope.project.entities.ConcreteCamionBuilder;
import com.parthenope.project.entities.ConcreteMotoBuilder;
import com.parthenope.project.entities.Director;
import com.parthenope.project.entities.Utente;
import com.parthenope.project.entities.Veicolo;
import com.parthenope.project.entities.VeicoloBuilder;
import com.parthenope.project.exceptions.CreazioneVeicoloException;
import com.parthenope.project.exceptions.PersistenzaException;
import com.parthenope.project.exceptions.TutorNonEsisteException;
import com.parthenope.project.exceptions.VeicoloNonSupportato;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class InserisciFormVeicolo implements FormBinding, FormValidate
{

	@Override
	public void bindForm(Map<String, Object> input) throws TutorNonEsisteException
	{

	}

	@Override
	public void bindForm(Map<String, Object> input, Object o) throws VeicoloNonSupportato
	{
		Utente utente = (Utente) o;
		// Utilizzando il builder pattern popolo il veicolo ottenuto dal form
		VeicoloBuilder builder;

		switch (((ComboBox) input.get(TIPO_VEICOLO)).getValue().toString())
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
		Director director = new Director(builder);
		builder.casaAutomobilistica(((TextField) input.get(CASA_AUTOMOBILISTICA)).getText());
		builder.targa(((TextField) input.get(TARGA)).getText());
		builder.numeroRuote(Integer.parseInt(((TextField) input.get(NUMERO_RUOTE)).getText()));
		builder.velocitaMassimaVeicolo(Integer.parseInt(((TextField) input.get(VELOCITA_MASSIMA_VEICOLO)).getText()));
		builder.tipoVeicolo(((ComboBox<String>) input.get(TIPO_VEICOLO)).getValue());

		Veicolo veicolo = null;
		try
		{
			veicolo = director.generaUnVeicolo();
		} catch (CreazioneVeicoloException e)
		{
			e.printStackTrace();
		}
		utente.setVeicolo(veicolo);

		IPersistenza p = new PersistenzaFileVeicolo();
		try
		{
			p.aggiungiInfo(utente);
		} catch (PersistenzaException e1)
		{
			e1.printStackTrace();
		}

	}

}

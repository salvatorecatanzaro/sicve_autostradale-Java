package com.parthenope.project.entities;

import static com.parthenope.project.constants.Constants.NOME_STAZIONE_ENTRATA;
import static com.parthenope.project.constants.Constants.NOME_STAZIONE_USCITA;
import static com.parthenope.project.constants.Constants.PATH_RISORSE;
import static com.parthenope.project.constants.Constants.TUTOR_ATTIVO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.parthenope.project.exceptions.InitApplicationException;

public class Autostrada
{
	private static final Path PATH_TUTOR = Paths.get(PATH_RISORSE, "tutor");
	private List<TrattoAutostrada> trattiAutostrada;
	private static Autostrada instance = null;

	public List<TrattoAutostrada> getTrattiAutostrada()
	{
		return trattiAutostrada;
	}

	public void setTrattiAutostrada(List<TrattoAutostrada> trattiAutostrada)
	{
		this.trattiAutostrada = trattiAutostrada;
	}

	public void addTrattiAutostrada(TrattoAutostrada trattoAutostrada)
	{
		this.trattiAutostrada.add(trattoAutostrada);
	}

	private Autostrada()
	{
		this.trattiAutostrada = new ArrayList<>();
	}

	public static Autostrada getInstance()
	{
		if (instance == null)
		{
			instance = new Autostrada();
			try
			{
				buildAutostrada(instance);
			} catch (InitApplicationException e)
			{
				e.printStackTrace();
			}
		}
		return instance;
	}

	/**
	 * Questo metodo viene eseguito come prima istruzione dell'applicazione:
	 * Partendo da una fonte di dati (In questo caso file) permette la persistenza
	 * dell'entita Autostrada
	 * 
	 * @param instance
	 * @throws InitApplicationException
	 */
	private static void buildAutostrada(Autostrada instance) throws InitApplicationException
	{
		List<String> lines = null;
		try
		{
			if (Files.exists(PATH_TUTOR))
				lines = Files.readAllLines(PATH_TUTOR);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		if (lines == null)
			return; // non ci sono dati da caricare

		Tutor tutor = null;
		Stazione stazioneEntrata = null;
		Stazione stazioneUscita = null;
		TrattoAutostrada trattoAutostrada = null;
		AutoveloxMediator mediator = new AutoveloxMediatorimpl();

		for (String s : lines)
		{
			if (s.startsWith("=====Tutor"))
			{
				tutor = new Tutor();
				// eg. =====Tutor 2872a6a2-acdb-4a5c-9db3-788631ca7909=====
				tutor.setId(s.split(" ")[1].split("=")[0]);
				stazioneEntrata = new Stazione("Entrata", "");
				stazioneUscita = new Stazione("Uscita", "");
				trattoAutostrada = new TrattoAutostrada();
				trattoAutostrada.setTutor(tutor);
				instance.getTrattiAutostrada().add(trattoAutostrada);
				continue;
			} else if (s.contains("==="))
				continue;

			String key = s.split(":")[0];
			String value = s.split(":")[1];

			if (key.chars().filter(ch -> ch == '-').count() == 4)
			{
				Autovelox autovelox = new AutoveloxImpl(mediator, Integer.parseInt(value));
				autovelox.setId(key);
				mediator.aggiungiAutovelox(autovelox);
				tutor.getListaAutovelox().add(autovelox);
			} else if (key.equals(NOME_STAZIONE_ENTRATA))
			{
				stazioneEntrata = new Stazione("Entrata", value);
				tutor.setStazioneEntrata(stazioneEntrata);

			} else if (key.equals(NOME_STAZIONE_USCITA))
			{
				stazioneUscita = new Stazione("Uscita", value);
				tutor.setStazioneUscita(stazioneUscita);
			} else if (key.equals(TUTOR_ATTIVO))
			{
				tutor.setAttivo(Boolean.valueOf(value));
			} else
			{
				throw new InitApplicationException("Unexpected value");

			}
		}

	}

}

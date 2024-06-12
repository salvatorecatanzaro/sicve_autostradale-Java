package com.parthenope.project.utils;

import static com.parthenope.project.constants.Constants.NOME_STAZIONE_ENTRATA;
import static com.parthenope.project.constants.Constants.NOME_STAZIONE_USCITA;
import static com.parthenope.project.constants.Constants.PATH_RISORSE;
import static com.parthenope.project.constants.Constants.TUTOR_ATTIVO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.parthenope.project.entities.Autostrada;
import com.parthenope.project.entities.Autovelox;
import com.parthenope.project.entities.TrattoAutostrada;
import com.parthenope.project.entities.Tutor;
import com.parthenope.project.exceptions.PersistenzaException;

public class PersistenzaFileTutor implements IPersistenza
{
	private static final Path PATH_TUTOR = Paths.get(PATH_RISORSE, "tutor");

	@Override
	public void aggiornaInfo() throws PersistenzaException
	{
		Autostrada autostrada = Autostrada.getInstance();
		int idx = 0;
		for (TrattoAutostrada ta : autostrada.getTrattiAutostrada())
		{
			Tutor tutor = ta.getTutor();
			List<String> lines = new ArrayList<>();
			lines.add(String.format("=====Tutor %s=====", tutor.getId()));
			lines.add(String.format("%s:%s", NOME_STAZIONE_ENTRATA, tutor.getStazioneEntrata().getNome()));
			lines.add(String.format("%s:%s", NOME_STAZIONE_USCITA, tutor.getStazioneUscita().getNome()));
			lines.add(String.format("%s:%s", TUTOR_ATTIVO, tutor.isAttivo()));
			for (Autovelox autovelox : tutor.getListaAutovelox())
			{
				lines.add(String.format("%s:%s", autovelox.getId(), autovelox.getLimiteVelocita()));
			}
			lines.add("=================");
			File file = new File(PATH_TUTOR.toString());
			FileWriter fw = null;
			BufferedWriter br = null;
			PrintWriter pr = null;
			try
			{

				// False -> Nuovo file
				fw = new FileWriter(file, idx > 0 ? true : false);
				br = new BufferedWriter(fw);
				pr = new PrintWriter(br);
				for (String line : lines)
					pr.println(line);
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
			idx++;
		}

	}

	@Override
	public void aggiungiInfo(Object o) throws PersistenzaException
	{
		Tutor tutor = (Tutor) o;
		List<String> lines = new ArrayList<>();
		lines.add(String.format("=====Tutor %s=====", tutor.getId()));
		lines.add(String.format("%s:%s", NOME_STAZIONE_ENTRATA, tutor.getStazioneEntrata().getNome()));
		lines.add(String.format("%s:%s", NOME_STAZIONE_USCITA, tutor.getStazioneUscita().getNome()));
		lines.add(String.format("%s:%s", TUTOR_ATTIVO, tutor.isAttivo()));
		for (Autovelox autovelox : tutor.getListaAutovelox())
		{
			lines.add(String.format("%s:%s", autovelox.getId(), autovelox.getLimiteVelocita()));
		}
		lines.add("=================");
		try
		{
			if (Files.exists(PATH_TUTOR))
				Files.write(PATH_TUTOR, lines, StandardOpenOption.APPEND);
			else
				Files.write(PATH_TUTOR, lines, StandardOpenOption.CREATE);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}

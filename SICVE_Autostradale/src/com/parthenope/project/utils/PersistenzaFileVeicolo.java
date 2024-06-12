package com.parthenope.project.utils;

import static com.parthenope.project.constants.Constants.PATH_RISORSE;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.parthenope.project.entities.Utente;
import com.parthenope.project.entities.Veicolo;
import com.parthenope.project.exceptions.PersistenzaException;

public class PersistenzaFileVeicolo implements IPersistenza
{
	private static final Path PATH_UTENTE = Paths.get(PATH_RISORSE, "%s");

	@Override
	public void aggiornaInfo() throws PersistenzaException
	{

	}

	@Override
	public void aggiungiInfo(Object o) throws PersistenzaException
	{
		Utente utente = (Utente) o;
		Veicolo veicolo = utente.getVeicolo();
		String fileLocation = String.format(PATH_UTENTE.toString(), utente.getUsername());
		String line = String.format("%s %s %s %s %s", veicolo.getTarga(), veicolo.getCasaAutomobilistica(),
				veicolo.getVelocitaMassimaVeicolo(), veicolo.getNumeroRuote(), veicolo.getTipoVeicolo());
		FileWriter fw = null;
		BufferedWriter br = null;
		PrintWriter pr = null;
		try
		{

			// False -> Nuovo file
			fw = new FileWriter(fileLocation, false);
			br = new BufferedWriter(fw);
			pr = new PrintWriter(br);
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
	}

}

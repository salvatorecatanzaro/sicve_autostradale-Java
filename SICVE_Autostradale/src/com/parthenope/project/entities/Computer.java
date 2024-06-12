package com.parthenope.project.entities;

import static com.parthenope.project.constants.Constants.PATH_RISORSE;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Computer
{
	private static final Path PATH_UTENTE = Paths.get(PATH_RISORSE, "%s");

	/**
	 * Questo metodo si occupa di simulare la comunicazione alla centrale di polizia
	 * 
	 * @param infrazione l'infraizone commessa
	 */
	public void inoltraInfrazione(Infrazione infrazione)
	{
		if (infrazione == null)
		{
			System.out.println("Non sono state commesse infrazioni");
			return;
		}
		String fileLocation = String.format(PATH_UTENTE.toString(), "segnalazioni_computer");
		String outMsg = String.format("Il computer inoltra l'infrazione alla stazione di polizia, INFRAZIONE: %s\n",
				infrazione);
		System.out.print(outMsg);
		FileWriter fw = null;
		BufferedWriter br = null;
		PrintWriter pr = null;
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

}

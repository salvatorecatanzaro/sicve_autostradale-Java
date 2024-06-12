package com.parthenope.project.entities;

import com.parthenope.project.exceptions.CreazioneVeicoloException;

public interface VeicoloBuilder
{

	public VeicoloBuilder tipoVeicolo(String tipoVeicolo);

	public VeicoloBuilder targa(String targa);

	public VeicoloBuilder numeroRuote(int numeroRuote);

	public VeicoloBuilder casaAutomobilistica(String casaAutomobilistica);

	public VeicoloBuilder velocitaMassimaVeicolo(int velocitaMassimaVeicolo);

	/**
	 * Questo metodo del pattern creazionale Builder consente di costruire un
	 * oggetto di tipo @see com.parthenope.project.entities.Veicolo
	 * 
	 * @return
	 * @throws CreazioneVeicoloException
	 */
	public Veicolo build() throws CreazioneVeicoloException;
}

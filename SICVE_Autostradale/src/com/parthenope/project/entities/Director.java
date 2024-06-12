package com.parthenope.project.entities;

import com.parthenope.project.exceptions.CreazioneVeicoloException;

public class Director
{
	VeicoloBuilder builder;

	public Director(VeicoloBuilder builder)
	{
		this.builder = builder;

	}

	public void generaUnaFiat(VeicoloBuilder builder) throws CreazioneVeicoloException
	{
		builder.casaAutomobilistica("FIAT");
		builder.numeroRuote(4);
		builder.tipoVeicolo("AUTO");
	}

	/**
	 * Questo metodo genera un veicolo a partire dal builder passato come
	 * costruttore
	 * 
	 * @return
	 * @throws CreazioneVeicoloException
	 */
	public Veicolo generaUnVeicolo() throws CreazioneVeicoloException
	{
		return builder.build();
	}

}

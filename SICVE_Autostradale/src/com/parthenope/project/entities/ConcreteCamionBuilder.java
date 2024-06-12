package com.parthenope.project.entities;

import com.parthenope.project.exceptions.CreazioneVeicoloException;

public class ConcreteCamionBuilder implements VeicoloBuilder
{
	private String tipoVeicolo;
	private String targa;
	private int numeroPorte;
	private int numeroRuote;
	private String casaAutomobilistica;
	private Veicolo camion;
	private int velocitaMassimaVeicolo;

	@Override
	public VeicoloBuilder tipoVeicolo(String tipoVeicolo)
	{
		this.tipoVeicolo = tipoVeicolo;
		return this;
	}

	@Override
	public VeicoloBuilder targa(String targa)
	{
		this.targa = targa;
		return this;
	}

	public VeicoloBuilder numeroPorte(int numeroPorte)
	{
		this.numeroPorte = numeroPorte;
		return this;
	}

	@Override
	public VeicoloBuilder numeroRuote(int numeroRuote)
	{
		this.numeroRuote = numeroRuote;
		return this;
	}

	@Override
	public VeicoloBuilder casaAutomobilistica(String casaAutomobilistica)
	{
		this.casaAutomobilistica = casaAutomobilistica;
		return this;
	}

	@Override
	public Veicolo build() throws CreazioneVeicoloException
	{
		if (targa == null)
			throw new CreazioneVeicoloException("La targa è un valore obbligatorio");
		camion = new Camion(this.targa, this.numeroPorte, this.numeroRuote, this.casaAutomobilistica, this.tipoVeicolo,
				this.velocitaMassimaVeicolo);
		return camion;

	}

	@Override
	public VeicoloBuilder velocitaMassimaVeicolo(int velocitaMassimaVeicolo)
	{
		this.velocitaMassimaVeicolo = velocitaMassimaVeicolo;
		return this;
	}

}

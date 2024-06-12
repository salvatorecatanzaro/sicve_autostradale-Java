package com.parthenope.project.entities;

import com.parthenope.project.exceptions.CreazioneVeicoloException;

public class ConcreteMotoBuilder implements VeicoloBuilder
{

	private String tipoVeicolo;
	private String targa;
	private int numeroRuote;
	private String casaAutomobilistica;
	private Veicolo moto;
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
		if (this.targa == null)
			throw new CreazioneVeicoloException("Una moto non può non avere una targa");
		// ritorno il product buildato

		moto = new Moto(this.targa, this.numeroRuote, this.casaAutomobilistica, this.tipoVeicolo,
				this.velocitaMassimaVeicolo);
		return moto;
	}

	@Override
	public VeicoloBuilder velocitaMassimaVeicolo(int velocitaMassimaVeicolo)
	{

		this.velocitaMassimaVeicolo = velocitaMassimaVeicolo;
		return null;
	}

}

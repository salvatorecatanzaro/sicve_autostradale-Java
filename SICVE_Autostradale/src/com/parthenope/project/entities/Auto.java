package com.parthenope.project.entities;

public class Auto extends Veicolo
{
	private int numeroPorte;

	public Auto(String targa, int numeroPorte, int numeroRuote, String casaAutomobilistica, String tipoVeicolo,
			int velocitaMassimaVeicolo)
	{
		super(targa, numeroRuote, casaAutomobilistica, tipoVeicolo, velocitaMassimaVeicolo);
		this.numeroPorte = numeroPorte;
	}

	@Override
	public void entraInAutostrada()
	{
		System.out.println(String.format("Il veicolo %s è entrato in autostrada", this));

	}

	@Override
	public String toString()
	{

		return String.format("Auto: %s, targa: %s", this.getCasaAutomobilistica(), this.getTarga());
	}

}

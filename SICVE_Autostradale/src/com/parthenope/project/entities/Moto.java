package com.parthenope.project.entities;

public class Moto extends Veicolo
{

	public Moto(String targa, int numeroRuote, String casaAutomobilistica, String tipoVeicolo,
			int velocitaMassimaVeicolo)
	{
		super(targa, numeroRuote, casaAutomobilistica, tipoVeicolo, velocitaMassimaVeicolo);
	}

	@Override
	public void entraInAutostrada()
	{
		System.out.println(String.format("Il veicolo %s è entrato in autostrada", this));

	}

	@Override
	public String toString()
	{

		return String.format("Moto: %s, targa: %s", this.getCasaAutomobilistica(), this.getTarga());
	}

}

package com.parthenope.project.entities;

public abstract class Veicolo
{
	private String targa;
	private int numeroRuote;
	private String casaAutomobilistica;
	private int velocitaMassimaVeicolo;
	private String tipoVeicolo;

	public String getTipoVeicolo()
	{
		return tipoVeicolo;
	}

	public void setTipoVeicolo(String tipoVeicolo)
	{
		this.tipoVeicolo = tipoVeicolo;
	}

	public int getVelocitaMassimaVeicolo()
	{
		return velocitaMassimaVeicolo;
	}

	public void setVelocitaMassimaVeicolo(int velocitaMassimaVeicolo)
	{
		this.velocitaMassimaVeicolo = velocitaMassimaVeicolo;
	}

	public Veicolo(String targa, int numeroRuote, String casaAutomobilistica, String tipoVeicolo,
			int velocitaMassimaVeicolo)
	{
		super();
		this.targa = targa;
		this.numeroRuote = numeroRuote;
		this.casaAutomobilistica = casaAutomobilistica;
		this.tipoVeicolo = tipoVeicolo;
		this.velocitaMassimaVeicolo = velocitaMassimaVeicolo;
	}

	public abstract void entraInAutostrada();

	public double getVelocitaCorrente()
	{
		return Math.random() * this.velocitaMassimaVeicolo;
	}

	public String getTarga()
	{
		return targa;
	}

	public void setTarga(String targa)
	{
		this.targa = targa;
	}

	public int getNumeroRuote()
	{
		return numeroRuote;
	}

	public void setNumeroRuote(int numeroRuote)
	{
		this.numeroRuote = numeroRuote;
	}

	public String getCasaAutomobilistica()
	{
		return casaAutomobilistica;
	}

	public void setCasaAutomobilistica(String casaAutomobilistica)
	{
		this.casaAutomobilistica = casaAutomobilistica;
	}

}

package com.parthenope.project.entities;

public abstract class Infrazione
{
	private int livelloGravitaInfrazione;
	private String descrizione;
	private String targaAuto;
	private int costo;

	public Infrazione(int livelloGravitaInfrazione, String descrizione, String targaAuto, int costo)
	{
		super();
		this.livelloGravitaInfrazione = livelloGravitaInfrazione;
		this.descrizione = descrizione;
		this.targaAuto = targaAuto;
		this.costo = costo;
	}

	@Override
	public String toString()
	{
		return "Infrazione [livelloGravitaInfrazione=" + livelloGravitaInfrazione + ", descrizione=" + descrizione
				+ ", targaAuto=" + targaAuto + ", costo=" + costo + "]";
	}

	public int getLivelloGravitaInfrazione()
	{
		return livelloGravitaInfrazione;
	}

	public void setLivelloGravitaInfrazione(int livelloGravitaInfrazione)
	{
		this.livelloGravitaInfrazione = livelloGravitaInfrazione;
	}

	public String getDescrizione()
	{
		return descrizione;
	}

	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}

	public String getTargaAuto()
	{
		return targaAuto;
	}

	public void setTargaAuto(String targaAuto)
	{
		this.targaAuto = targaAuto;
	}

	public int getCosto()
	{
		return costo;
	}

	public void setCosto(int costo)
	{
		this.costo = costo;
	}

}

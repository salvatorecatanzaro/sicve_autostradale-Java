package com.parthenope.project.entities;

public class EccessoDiVelocita extends Infrazione
{
	private static final String DESCRIZIONE = "L\' utente ha superato il limite di velocita";

	public EccessoDiVelocita(String targaAuto)
	{
		super(9, DESCRIZIONE, targaAuto, 300);
	}

}

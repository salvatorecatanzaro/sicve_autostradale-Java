package com.parthenope.project.entities;

public class VelocitaMedia extends Infrazione
{
	private static final String DESCRIZIONE = "L\' utente ha superato il limite di velocita media";

	public VelocitaMedia(String targaAuto, int gravita)
	{
		super(gravita, DESCRIZIONE, targaAuto, 300);
	}

}

package com.parthenope.project.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Autovelox
{
	private String id;
	private int limiteVelocita;
	protected AutoveloxMediator mediator;
	private Map<String, Infrazione> targaInfrazione;
	private Computer computer;

	public Autovelox(AutoveloxMediator mediator, int limiteVelocita)
	{
		this.mediator = mediator;
		this.limiteVelocita = limiteVelocita;
		this.id = UUID.randomUUID().toString();
		this.targaInfrazione = new HashMap<>();
		this.computer = new Computer();
	}

	/**
	 * Questo metodo si occupa di effettuare il calcolo della velocita media del
	 * veicolo passato in input
	 * 
	 * @param veicolo  il veicolo
	 * @param distanza la distanza
	 * @param ore      le ore impiegate a percorrere la tratta
	 * @return velocita media
	 */
	public abstract double calcolaVelocitaMedia(Veicolo veicolo, int distanza, double ore);

	/**
	 * Controlla la velocita MEDIA del veicolo al momento del passaggio, se questa è
	 * superiore di {@link Autovelox#limiteVelocita} viene generata una Infrazione
	 * del tipo @see com.parthenope.project.entities.EccessoDiVelocita
	 * 
	 * @param veicolo       il veicolo
	 * @param velocitaMedia la velocita media del veicolo
	 */
	public abstract void isLimiteVelocitaMediaSuperato(Veicolo veicolo, double velocitaMedia);

	/**
	 * Simula l'atto di superamento di un autovelox. Se la velocita istantanea del
	 * veicolo è superiore a {@link Autovelox#limiteVelocita} viene generata una
	 * infrazione del tipo @see com.parthenope.project.entities.VelocitaMedia
	 * 
	 * @param veicolo
	 */
	public abstract void autoveloxPercorso(Veicolo veicolo);

	/**
	 * Metodo del design pattern mediator: Si occupa di inviare l'infrazione a tutti
	 * gli altri autovelox
	 * 
	 * @param infrazione
	 */
	public abstract void send(Infrazione infrazione);

	/**
	 * Metodo del design pattern mediator: Si occupa di far ricevere l'infrazione a
	 * tutti gli altri autovelox
	 * 
	 * @param infrazione
	 */
	public abstract void receive(Infrazione infrazione);

	public Computer getComputer()
	{
		return computer;
	}

	public void setComputer(Computer computer)
	{
		this.computer = computer;
	}

	public Map<String, Infrazione> getTargaInfrazione()
	{
		return targaInfrazione;
	}

	public void setTargaInfrazione(Map<String, Infrazione> targaInfrazione)
	{
		this.targaInfrazione = targaInfrazione;
	}

	public int getLimiteVelocita()
	{
		return this.limiteVelocita;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setLimiteVelocita(int limiteVelocita)
	{
		this.limiteVelocita = limiteVelocita;
	}

	@Override
	public String toString()
	{
		return "Autovelox [limiteVelocita=" + limiteVelocita + ", mediator=" + mediator + "]";
	}
}

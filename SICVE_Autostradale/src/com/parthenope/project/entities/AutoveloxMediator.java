package com.parthenope.project.entities;

public interface AutoveloxMediator
{

	/**
	 * Questo metodo utilizzato per implementare il design pattern mediator si
	 * occupa di inviare l'infrazione a tutti gli altri autovelox
	 * 
	 * @param infrazione l'infrazione generata da un veicolo
	 * @param autovelox  l'autovelox che ha generato l'evento
	 */
	public void segnalaInfrazione(Infrazione infrazione, Autovelox autovelox);

	/**
	 * Questo metodo si occupa di aggiungere un autoveloc al mediator
	 * 
	 * @param autovelox
	 */
	public void aggiungiAutovelox(Autovelox autovelox);

}

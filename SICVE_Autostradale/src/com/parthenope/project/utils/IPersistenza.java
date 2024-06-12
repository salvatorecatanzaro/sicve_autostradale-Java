package com.parthenope.project.utils;

import com.parthenope.project.exceptions.PersistenzaException;

public interface IPersistenza
{
	/**
	 * Utilizzato per l'inserimento di nuovi dati
	 * 
	 * @throws PersistenzaException
	 */
	public void aggiornaInfo() throws PersistenzaException;

	/**
	 * Utilizzato per la modifica dei dati
	 * 
	 * @param o l'oggetto da modificare
	 * @throws PersistenzaException
	 */
	public void aggiungiInfo(Object o) throws PersistenzaException;

}

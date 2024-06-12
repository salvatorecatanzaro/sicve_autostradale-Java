package com.parthenope.project.utils;

import java.util.Map;

import com.parthenope.project.exceptions.TutorNonEsisteException;
import com.parthenope.project.exceptions.VeicoloNonSupportato;

public interface FormBinding
{

	/**
	 * Questo metodo si occupa di salvare in memoria i dati inseriti nel form.
	 * 
	 * @param input un elemento del form
	 * @throws TutorNonEsisteException
	 */
	public void bindForm(Map<String, Object> input) throws TutorNonEsisteException;

	/**
	 * Questo metodo si occupa di salvare in memoria i dati inseriti nel form
	 * 
	 * @param input  un elemento del form
	 * @param Object un elemento aggiuntivo per il binding del form
	 * @throws VeicoloNonSupportato
	 */
	public void bindForm(Map<String, Object> input, Object Object) throws VeicoloNonSupportato;
}

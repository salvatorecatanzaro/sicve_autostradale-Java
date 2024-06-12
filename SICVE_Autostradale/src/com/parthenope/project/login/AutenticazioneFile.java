package com.parthenope.project.login;

import static com.parthenope.project.constants.Constants.PASSWORD_ERRATA;
import static com.parthenope.project.constants.Constants.SUCCESS;
import static com.parthenope.project.constants.Constants.UTENTE_NON_ESISTE;

import java.util.Map;

import com.parthenope.project.entities.Utente;
import com.parthenope.project.utils.Utils;

public class AutenticazioneFile extends AutenticaUtenteAbstract
{

	public AutenticazioneFile(String username, String password)
	{
		super(username, password);
	}

	@Override
	public Map.Entry<String, Utente> login()
	{
		Map<String, Utente> utenti = Utils.getUsersFromFile();
		Utente utente = utenti.get(this.getUsername());
		if (utente == null)
			return Map.entry(UTENTE_NON_ESISTE, new Utente());

		if (!utente.getPassword().equals(this.getPassword()))
			return Map.entry(PASSWORD_ERRATA, new Utente());

		return Map.entry(SUCCESS, utente);
	}

}

package com.parthenope.project.login;

import java.util.Map;

import com.parthenope.project.entities.Utente;

public abstract class AutenticaUtenteAbstract
{
	private String username;
	private String password;

	public AutenticaUtenteAbstract(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Questo metodo permette di effettuare un login. Username e password forniti al
	 * costruttore {@link AutenticazioneFile} verranno utilizzati per
	 * l'autenticazione
	 * 
	 * @return <Messaggio, Utente ottenuto>
	 */
	public abstract Map.Entry<String, Utente> login();
}

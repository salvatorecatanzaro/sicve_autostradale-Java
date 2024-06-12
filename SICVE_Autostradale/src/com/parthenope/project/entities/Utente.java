package com.parthenope.project.entities;

import java.util.ArrayList;
import java.util.List;

public class Utente
{
	private String nome;
	private String cognome;
	private String username;
	private String password;
	private String ruolo;
	private Veicolo veicolo;
	private List<String> avvisi;
	private boolean avvisiAbilitati;

	public List<String> getAvvisi()
	{
		return avvisi;
	}

	public void setAvvisi(List<String> avvisi)
	{
		this.avvisi = avvisi;
	}

	public boolean isAvvisiAbilitati()
	{
		return avvisiAbilitati;
	}

	public void setAvvisiAbilitati(boolean avvisiAbilitati)
	{
		this.avvisiAbilitati = avvisiAbilitati;
	}

	public Utente()
	{
	}

	public Utente(String nome, String cognome, String username, String password, String ruolo, Veicolo veicoloc)
	{
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.password = password;
		this.ruolo = ruolo;
		this.veicolo = veicolo;
		avvisi = new ArrayList<>();
	}

	public Utente(String[] valori)
	{
		super();
		this.nome = valori[0];
		this.cognome = valori[1];
		this.username = valori[2];
		this.password = valori[3];
		this.ruolo = valori[4];
		avvisi = new ArrayList<>();
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getCognome()
	{
		return cognome;
	}

	public void setCognome(String cognome)
	{
		this.cognome = cognome;
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

	public String getRuolo()
	{
		return ruolo;
	}

	public void setRuolo(String ruolo)
	{
		this.ruolo = ruolo;
	}

	public Veicolo getVeicolo()
	{
		return veicolo;
	}

	public void setVeicolo(Veicolo veicolo)
	{
		this.veicolo = veicolo;
	}

}

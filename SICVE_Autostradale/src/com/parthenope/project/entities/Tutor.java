package com.parthenope.project.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * La classe {@link Tutor} al momento della creazione di un'istanza genera un
 * valore trattaCopertaKm che avrà un valore compreso tra 10km e 25km
 * 
 * @author salvatorecatanzaro
 *
 */
public class Tutor
{
	private String id;
	private boolean attivo;
	private List<Autovelox> listaAutovelox;
	private Stazione stazioneEntrata;
	private Stazione stazioneUscita;
	private double trattaCopertaKm;

	public Tutor()
	{
		listaAutovelox = new ArrayList<>();
		this.id = UUID.randomUUID().toString();
		this.trattaCopertaKm = (Math.random() * 25) + 10;
	}

	public double lunghezzaKmTratta()
	{
		return trattaCopertaKm;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public boolean isAttivo()
	{
		return attivo;
	}

	public void setAttivo(boolean attivo)
	{
		this.attivo = attivo;
	}

	public Stazione getStazioneEntrata()
	{
		return stazioneEntrata;
	}

	public void setStazioneEntrata(Stazione stazioneEntrata)
	{
		this.stazioneEntrata = stazioneEntrata;
	}

	public Stazione getStazioneUscita()
	{
		return stazioneUscita;
	}

	public void setStazioneUscita(Stazione stazioneUscita)
	{
		this.stazioneUscita = stazioneUscita;
	}

	public List<Autovelox> getListaAutovelox()
	{
		return listaAutovelox;
	}

	public void setListaAutovelox(List<Autovelox> listaAutovelox)
	{
		this.listaAutovelox = listaAutovelox;
	}

	@Override
	public String toString()
	{
		return "Tutor [attivo=" + attivo + ", listaAutovelox=" + listaAutovelox + ", stazioneEntrata=" + stazioneEntrata
				+ ", stazioneUscita=" + stazioneUscita + "]";
	}

}

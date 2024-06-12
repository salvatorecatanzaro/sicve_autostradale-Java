package com.parthenope.project.entities;

public class Stazione
{
	private String direzione;
	private String nome;

	public Stazione(String direzione, String nome)
	{
		this.direzione = direzione;
		this.nome = nome;
	}

	public String getDirezione()
	{
		return direzione;
	}

	public void setDirezione(String direzione)
	{
		this.direzione = direzione;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	@Override
	public String toString()
	{
		return "Stazione [direzione=" + direzione + ", nome=" + nome + "]";
	}

}

package com.parthenope.project.entities;

public class AutoveloxImpl extends Autovelox
{

	public AutoveloxImpl(AutoveloxMediator mediator, int limiteVelocita)
	{
		super(mediator, limiteVelocita);

	}

	@Override
	public void send(Infrazione infrazione)
	{
		mediator.segnalaInfrazione(infrazione, this);

	}

	@Override
	public void receive(Infrazione infrazione)
	{
		String targa = infrazione.getTargaAuto();
		System.out.println("Infrazione comunicata");
		System.out.println(infrazione);
		Infrazione infrazioneEsistente = this.getTargaInfrazione().get(targa);

		if (infrazioneEsistente == null
				|| infrazioneEsistente.getLivelloGravitaInfrazione() < infrazione.getLivelloGravitaInfrazione())
		{
			this.getTargaInfrazione().put(targa, infrazione);
		}

	}

	@Override
	public void autoveloxPercorso(Veicolo veicolo)
	{
		int limite = this.getLimiteVelocita();
		double velocitaCorrenteVeicolo = veicolo.getVelocitaCorrente();

		if (velocitaCorrenteVeicolo > limite)
		{
			Infrazione limiteVelocitaSuperato = new EccessoDiVelocita(veicolo.getTarga());
			this.getComputer().inoltraInfrazione(limiteVelocitaSuperato);
		}
	}

	@Override
	public double calcolaVelocitaMedia(Veicolo veicolo, int distanza, double ore)
	{
		double velocitaMedia = distanza / ore;

		return velocitaMedia;
	}

	@Override
	public void isLimiteVelocitaMediaSuperato(Veicolo veicolo, double velocitaMedia)
	{

		String targaVeicolo = veicolo.getTarga();
		if (velocitaMedia > this.getLimiteVelocita())
		{

			VelocitaMedia infrazioneGenerata = new VelocitaMedia(targaVeicolo,
					(int) (velocitaMedia - this.getLimiteVelocita()));
			Infrazione infrazioneEsistente = this.getTargaInfrazione().get(targaVeicolo);
			if (infrazioneEsistente == null || infrazioneEsistente.getLivelloGravitaInfrazione() < infrazioneGenerata
					.getLivelloGravitaInfrazione())
			{
				this.send(infrazioneGenerata);
				this.getTargaInfrazione().put(veicolo.getTarga(), infrazioneGenerata);
			} else
			{
				this.send(infrazioneGenerata);

			}

		}
	}

}

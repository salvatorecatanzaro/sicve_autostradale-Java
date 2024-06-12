package com.parthenope.project.entities;

import java.util.ArrayList;
import java.util.List;

public class AutoveloxMediatorimpl implements AutoveloxMediator
{
	private List<Autovelox> listaAutovelox;

	public AutoveloxMediatorimpl()
	{
		this.listaAutovelox = new ArrayList<>();
	}

	@Override
	public void aggiungiAutovelox(Autovelox autovelox)
	{
		this.listaAutovelox.add(autovelox);

	}

	@Override
	public void segnalaInfrazione(Infrazione infrazione, Autovelox autovelox)
	{
		String targa = infrazione.getTargaAuto();
		for (Autovelox currentAutovelox : this.listaAutovelox)
		{
			if (currentAutovelox != autovelox)
				currentAutovelox.receive(infrazione);
		}
	}
}

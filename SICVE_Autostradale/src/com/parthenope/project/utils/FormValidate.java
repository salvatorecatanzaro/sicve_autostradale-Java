package com.parthenope.project.utils;

import java.util.List;
import java.util.Map;

import javafx.scene.control.TextField;

public interface FormValidate
{
	/**
	 * This method will validate form inputs
	 * 
	 * @param input the form input
	 * @return
	 */
	public default boolean validateForm(Map<String, Object> input)
	{
		// This list will contain all the number textfield
		List<String> numbersList = List.of("Autovelox", "Numero ruote", "Velocita massima");
		boolean isValid = true;
		for (String key : input.keySet())
		{
			Object currentObj = input.get(key);
			if (Utils.listKeyStartsWith(numbersList, key))
			{

				TextField tf = (TextField) currentObj;
				if (tf == null || tf.getText().isEmpty())
				{
					((TextField) input.get(key)).setText("Campo obbligatorio");
					isValid = false;
				} else if (!Utils.checkNumber(tf.getText().toCharArray()))
				{

					((TextField) input.get(key)).setText("Inserire un intero");
					isValid = false;
				}

			} else if (currentObj instanceof TextField)
			{
				TextField tf = (TextField) currentObj;
				if (tf == null || tf.getText().isEmpty())
				{
					((TextField) input.get(key)).setText("Campo obbligatorio");
					isValid = false;
				}
			}
		}
		return isValid;
	}

}

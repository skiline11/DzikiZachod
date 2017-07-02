// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.List;
import java.util.Random;

public class Bandyta extends Gracz_Standardowy {

	Random rand = new Random();
	
	public Bandyta()
	{
		super(Bandyta.class);
	}
	
	public Bandyta(Class<?> klasa_strategii)
	{
		super(Bandyta.class, klasa_strategii);
	}
	
	@Override
	public String getOpis() {

		if(super.getZycia() == 0) return "X (Bandyta)";
		else return "Bandyta (liczba żyć: " + super.getZycia() + ")";
	}
}

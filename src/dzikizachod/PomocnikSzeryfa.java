// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class PomocnikSzeryfa extends Gracz_Standardowy {

	Random rand = new Random();
	
	public PomocnikSzeryfa()
	{
		super(PomocnikSzeryfa.class);
	}
	
	public PomocnikSzeryfa(Class<?> klasa_strategii)
	{
		super(PomocnikSzeryfa.class, klasa_strategii);
	}

	@Override
	public String getOpis() {
		if(super.getZycia() == 0) return "X (Pomocnik Szeryfa)";
		else return "Pomocnik Szeryfa (liczba żyć: " + super.getZycia() + ")";
	}
}

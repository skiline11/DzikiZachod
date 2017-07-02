// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class Szeryf extends Gracz_Standardowy {

	private Set<Gracz> gracze_ktorzy_atakowali_mnie;

	Random rand = new Random();

	public Szeryf()
	{
		super(Szeryf.class);
	}
	
	public Szeryf(Class<?> klasa_strategii)
	{
		super(Szeryf.class, klasa_strategii);
	}
	
	@Override
	public String getOpis() {
		if(super.getZycia() == 0) return "X (Szeryf)";
		else return "Szeryf (liczba żyć: " + super.getZycia() + ")";
	}

	@Override
	public boolean sprubuj_uleczyc(Class klasa, List<Gracz> gracze, PulaAkcji pulaAkcji) {
		return super.sprubuj_uleczyc(klasa, gracze, pulaAkcji);
	}
	
}

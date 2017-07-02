// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	//Przykładowa rozgrywka
	public static void main(String[] args)
	{
		// Szeryf jest tylko jeden
		List<Gracz> gracze = new ArrayList<>();
		gracze.add(new Szeryf());
		for(int i = 0; i < 5; i++)
		{
			gracze.add(new Bandyta(StrategiaBandytyCierpliwa.class));
			gracze.add(new Bandyta());
			gracze.add(new Bandyta(StrategiaBandytySprytna.class));
			gracze.add(new PomocnikSzeryfa());
			gracze.add(new PomocnikSzeryfa(StrategiaPomocnikaSzeryfaZliczajaca.class));
		}
		PulaAkcji pulaAkcji = new PulaAkcji();
		pulaAkcji.dodaj(Akcja.ULECZ, 100);
		pulaAkcji.dodaj(Akcja.STRZEL, 100);
		pulaAkcji.dodaj(Akcja.ZASIEG_PLUS_JEDEN, 200);
		pulaAkcji.dodaj(Akcja.ZASIEG_PLUS_DWA, 10);
		pulaAkcji.dodaj(Akcja.DYNAMIT, 1);
		Gra gra = new Gra();
		gra.rozgrywka(gracze, pulaAkcji);
	}
}

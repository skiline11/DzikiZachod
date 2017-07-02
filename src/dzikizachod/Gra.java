// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.Collections;
import java.util.List;

public class Gra {

	static int max_liczba_tur = 42;
	public Gra() {}
	
	private void wypisz_opisy_graczy(List<Gracz> gracze)
	{
		System.out.println("  Gracze:");
		int numer = 1;
		for(Gracz gracz : gracze)
		{
			System.out.println("    " + numer + ": " + gracz.getOpis());
			numer++;
		}
		System.out.println("");
	}

	private boolean nie_ma_bandytow(List<Gracz> gracze)
	{
		for(Gracz g : gracze)
		{
			if(g.getClass() == Bandyta.class && g.getZycia() != 0)
			{
				return false;
			}
		}
		return true;
	}

	private boolean sprawdz_czy_koniec(List<Gracz> gracze, Gracz szeryf)
	{
//		System.out.println("Sprawdzam czy koniec");
		if(szeryf.getZycia() == 0)
		{
			System.out.println("** KONIEC");
			System.out.println("  WYGRANA STRONA: bandyci");
			return true;
		}
		else if(nie_ma_bandytow(gracze))
		{
			System.out.println("** KONIEC");
			System.out.println("  WYGRANA STRONA: szeryf i pomocnicy");
			return true;
		}
		else
		{
//			System.out.println("NIE         KONIEC");
			return false;
		}
	}

	public void rozgrywka(List<Gracz> gracze, PulaAkcji pulaAkcji)
	{	
		Collections.shuffle(gracze);
		
		int gdzie_szeryf = 0;
		while((gracze.get(gdzie_szeryf) instanceof Szeryf) == false) gdzie_szeryf++;
		Gracz szeryf = gracze.get(gdzie_szeryf);
		int numer_tury = 1;
		
		System.out.println("** START");
		wypisz_opisy_graczy(gracze);
		boolean czy_wypisac_opisy;
		int numer_gracza;
		boolean czy_koniec = false;
		while(numer_tury <= max_liczba_tur && czy_koniec == false)
		{
			System.out.println("** TURA " + numer_tury);
			for(int i = 0; i < gracze.size() && czy_koniec == false; i++)
			{
				numer_gracza = (i + gdzie_szeryf) % gracze.size();
				Gracz gracz = gracze.get(numer_gracza);
				if(gracz.getZycia() == 0)
				{
					gracz.wypisz_opis_gracza(gracze, numer_gracza);
					System.out.println("");
				}
				else
				{
					czy_wypisac_opisy = gracz.wykonaj_ruch_zgodny_ze_strategia(gracze, numer_gracza, pulaAkcji, szeryf);
					System.out.println("");
					if(czy_wypisac_opisy) wypisz_opisy_graczy(gracze);
					czy_koniec = sprawdz_czy_koniec(gracze, szeryf);
				}
			}
			numer_tury++;
		}
		if(czy_koniec == false)
		{
			System.out.println("** KONIEC");
			System.out.println("  REMIS - OSIAGNIETO LIMIT TUR");
		}
	}
}

// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PulaAkcji {
	
	//ULECZ, STRZEL, ZASIEG_PLUS_JEDEN, ZASIEG_PLUS_DWA, DYNAMIT;
	private Map<Akcja, Integer> oddane_karty = new HashMap<>();
	private Map<Akcja, Integer> oryginalna_talia = new HashMap<>();
	private Map<Akcja, Integer> talia = new HashMap<Akcja, Integer>();
	private int ile_pozostalych_kart;
	
	public PulaAkcji()
	{
		talia.put(Akcja.ULECZ, 0);
		talia.put(Akcja.STRZEL, 0);
		talia.put(Akcja.ZASIEG_PLUS_JEDEN, 0);
		talia.put(Akcja.ZASIEG_PLUS_DWA, 0);
		talia.put(Akcja.DYNAMIT, 0);
		oddane_karty.put(Akcja.ULECZ, 0);
		oddane_karty.put(Akcja.STRZEL, 0);
		oddane_karty.put(Akcja.ZASIEG_PLUS_JEDEN, 0);
		oddane_karty.put(Akcja.ZASIEG_PLUS_DWA, 0);
		oddane_karty.put(Akcja.DYNAMIT, 0);
		this.ile_pozostalych_kart = 0;
	}

	public void wypisz_talie()
	{
		System.out.println("TALIA: *******************************************");
		for(Map.Entry<Akcja, Integer> entry : this.talia.entrySet())
		{
			System.out.println(entry.getKey() + ", " + entry.getValue());
		}
		System.out.println("KONIEC TALII");
	}

	public void dodaj(Akcja typ, int liczba)
	{
		int wartosc = talia.get(typ);
		this.talia.put(typ, wartosc + liczba);
		this.ile_pozostalych_kart += liczba;
	}

	public void przetasuj_talie()
	{
//		System.out.println("****************************TASUJE TALIE**********************************");
		this.ile_pozostalych_kart = 0;
		for(Map.Entry<Akcja, Integer> entry : this.oddane_karty.entrySet())
		{
			this.talia.put(entry.getKey(), entry.getValue());
			this.ile_pozostalych_kart += entry.getValue();
		}
		oddane_karty.put(Akcja.ULECZ, 0);
		oddane_karty.put(Akcja.STRZEL, 0);
		oddane_karty.put(Akcja.ZASIEG_PLUS_JEDEN, 0);
		oddane_karty.put(Akcja.ZASIEG_PLUS_DWA, 0);
		oddane_karty.put(Akcja.DYNAMIT, 0);
	}
	
	public Akcja daj_karte()
	{
//		System.out.println("Pozostalych kart : " + this.ile_pozostalych_kart);
		Akcja akcja = null;
		Random rand = new Random();
		int ile_ulecz = this.talia.get(Akcja.ULECZ);
		int ile_strzel = ile_ulecz + this.talia.get(Akcja.STRZEL);
		int ile_zasieg_plus_jeden = ile_strzel + this.talia.get(Akcja.ZASIEG_PLUS_JEDEN);
		int ile_zasieg_plus_dwa = ile_zasieg_plus_jeden + this.talia.get(Akcja.ZASIEG_PLUS_DWA);
		int ile_dynamit = ile_zasieg_plus_dwa + this.talia.get(Akcja.DYNAMIT);
		while(akcja == null)
		{
			int x = rand.nextInt(ile_dynamit);
			if(x < ile_ulecz) akcja = Akcja.ULECZ;
			else if(x < ile_strzel) akcja = Akcja.STRZEL;
			else if(x < ile_zasieg_plus_jeden) akcja = Akcja.ZASIEG_PLUS_JEDEN;
			else if(x < ile_zasieg_plus_dwa) akcja = Akcja.ZASIEG_PLUS_DWA;
			else if(x < ile_dynamit) akcja = Akcja.DYNAMIT;
			switch(x)
			{
			case 0:
				akcja = Akcja.ULECZ; break;
			case 1:
				akcja = Akcja.STRZEL; break;
			case 2:
				akcja = Akcja.ZASIEG_PLUS_JEDEN; break;
			case 3:
				akcja = Akcja.ZASIEG_PLUS_DWA; break;
			case 4:
				akcja = Akcja.DYNAMIT; break;
			}
			int ile = this.talia.get(akcja);
			if(ile != 0)
			{
				ile--;
				this.talia.put(akcja, ile);
			}
			else akcja = null;
		}
		this.ile_pozostalych_kart--;
//		wypisz_talie();
		if(this.ile_pozostalych_kart == 0) przetasuj_talie();
		return akcja;
	}

	public void oddaje_karte(Akcja karta)
	{
		int wartosc = this.oddane_karty.get(karta);
		this.oddane_karty.put(karta, wartosc + 1);
	}
}

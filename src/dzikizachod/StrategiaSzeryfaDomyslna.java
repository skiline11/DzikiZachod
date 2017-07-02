// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class StrategiaSzeryfaDomyslna extends StrategiaSzeryfa {

	public Set<Gracz> dodaj_kandydatow_do_ataku(List<Gracz> gracze, int numer_gracza, int zasieg)
	{
		Set<Gracz> kandydaci_do_ataku = new HashSet<>();
		int w_lewo = 0;
		int nr_przeciwnika = (numer_gracza + gracze.size() - 1) % gracze.size();
		while(w_lewo != zasieg && nr_przeciwnika != numer_gracza)
		{
			if(gracze.get(nr_przeciwnika).getZycia() != 0)
			{
				kandydaci_do_ataku.add(gracze.get(nr_przeciwnika));
				w_lewo++;
			}
			nr_przeciwnika = (nr_przeciwnika + gracze.size() - 1) % gracze.size();
		}
		
		int w_prawo = 0;
		nr_przeciwnika = (numer_gracza + 1) % gracze.size();
		while(w_prawo != zasieg && nr_przeciwnika != numer_gracza)
		{
			if(gracze.get(nr_przeciwnika).getZycia() != 0)
			{
				kandydaci_do_ataku.add(gracze.get(nr_przeciwnika));
				w_prawo++;
			}
			nr_przeciwnika = (nr_przeciwnika + 1) % gracze.size();
		}
		return kandydaci_do_ataku;
	}
	
	public boolean czy_gracze_ktorzy_do_mnie_strzelali_sa_w_zasiegu(Set<Gracz> kandydaci_do_ataku, Set<Gracz> gracze_ktorzy_strzelali_do_mnie)
	{
		for(Gracz g : kandydaci_do_ataku)
		{
			if(gracze_ktorzy_strzelali_do_mnie.contains(g)) return true;
		}
		return false;
	}
	
	public Set<Gracz> usun_tych_ktorych_nie_strzelali_do_mnie(Set<Gracz> kandydaci_do_ataku, Set<Gracz> gracze_ktorzy_strzelali_do_mnie)
	{
		Set<Gracz> do_usuniecia = new HashSet<Gracz>();
		for(Gracz g : kandydaci_do_ataku)
		{
			if(gracze_ktorzy_strzelali_do_mnie.contains(g) == false)
			{
				do_usuniecia.add(g);
			}
		}
		for(Gracz g : do_usuniecia)
		{
			kandydaci_do_ataku.remove(g);
		}
		return kandydaci_do_ataku;
	}

	public Set<Gracz> kto_z_nich_jeszcze_zyje(Set<Gracz> ci_ktorzy_mnie_atakowali)
	{
		Set<Gracz> kto_zyje = new HashSet<>();
		for(Gracz g : ci_ktorzy_mnie_atakowali)
		{
			if(g.getZycia() != 0)
			{
				kto_zyje.add(g);
			}
		}
		return kto_zyje;
	}

	public Set<Gracz> kto_zyje_i_jest_w_poblizu(List<Gracz> gracze, int numer_gracza, Set<Gracz> ci_ktorzy_mnie_atakowali, int zasieg)
	{
		Set<Gracz> set_z_atak = new HashSet<>();
		Set<Gracz> set = new HashSet<>();
		int nr_przeciwnika_w_lewo = (numer_gracza + gracze.size() - 1) % gracze.size();
		int nr_przeciwnika_w_prawo = (numer_gracza + 1)%gracze.size();
		int org_zasieg = zasieg;

		while(nr_przeciwnika_w_lewo != numer_gracza && zasieg != 0)
		{
			while(gracze.get(nr_przeciwnika_w_lewo).getZycia() == 0 && nr_przeciwnika_w_lewo != numer_gracza)
			{
				nr_przeciwnika_w_lewo = (nr_przeciwnika_w_lewo + gracze.size() - 1) % gracze.size();
			}
			if(nr_przeciwnika_w_lewo != numer_gracza)
			{
				if(gracze.get(nr_przeciwnika_w_lewo).getClass() == Bandyta.class)
				{
					if(ci_ktorzy_mnie_atakowali.contains(gracze.get(nr_przeciwnika_w_lewo)))
					{
						set_z_atak.add(gracze.get(nr_przeciwnika_w_lewo));
					}
					set.add(gracze.get(nr_przeciwnika_w_lewo));
				}
				nr_przeciwnika_w_lewo = (nr_przeciwnika_w_lewo + gracze.size() - 1) % gracze.size();
			}
			zasieg--;
		}
		zasieg = org_zasieg;
		while(nr_przeciwnika_w_prawo != numer_gracza && zasieg != 0)
		{
			while(gracze.get(nr_przeciwnika_w_prawo).getZycia() == 0 && nr_przeciwnika_w_prawo != numer_gracza)
			{
				nr_przeciwnika_w_prawo = (nr_przeciwnika_w_prawo + 1) % gracze.size();
			}
			if(nr_przeciwnika_w_prawo != numer_gracza)
			{
				if(gracze.get(nr_przeciwnika_w_prawo).getClass() == Bandyta.class)
				{
					if(ci_ktorzy_mnie_atakowali.contains(gracze.get(nr_przeciwnika_w_prawo)))
					{
						set_z_atak.add(gracze.get(nr_przeciwnika_w_prawo));
					}
					set.add(gracze.get(nr_przeciwnika_w_prawo));
					nr_przeciwnika_w_prawo = (nr_przeciwnika_w_prawo + 1) % gracze.size();
				}
			}
			zasieg--;
		}

		if(set_z_atak.isEmpty() == false)
		{
			return set_z_atak;
		}
		else return set;
	}

	@Override
	public boolean wykonaj_ruch(List<Gracz> gracze, int numer_gracza, PulaAkcji pulaAkcji,
											   Set<Gracz> gracze_ktorzy_strzelali_do_mnie, Gracz szeryf) {

		Random rand = new Random();
		boolean zaatakowalem_kogos = false;

		Gracz ja = gracze.get(numer_gracza);
		int zasieg = ja.getZasieg();
		Set<Gracz> ci_ktorzy_mnie_atakowali = ja.getGracze_ktorzy_mnie_atakowali();

		Akcja karty[] = ja.getKarty();
		for(int i = 0; i < 5; i++)
		{
			if(karty[i] == Akcja.STRZEL)
			{
				// Jesli z posrod tych ktorzy mnie atakowali są ludzie w poblizu to zwróci mi tych ludzi
				// Jesli są oni daleko to zwraca po prostu tych którzy sa blisko
				Set<Gracz> kto_zyje_i_jest_w_poblizu = kto_zyje_i_jest_w_poblizu(gracze, numer_gracza, ci_ktorzy_mnie_atakowali, zasieg);

				int ile = kto_zyje_i_jest_w_poblizu.size();
				if(ile != 0)
				{
					int nr_skazanego = rand.nextInt(ile);
					Iterator<Gracz> iter = kto_zyje_i_jest_w_poblizu.iterator();
					while(nr_skazanego != 0)
					{
						iter.next();
						nr_skazanego--;
					}
					Gracz skazany = iter.next();
					skazany.przyjmij_obrazenia(ja);
					if(skazany.getZycia() == 0)
					{
						if(skazany.getClass() == Bandyta.class) ja.zabilem_bandyte();
						else if(skazany.getClass() == PomocnikSzeryfa.class) ja.zabilem_pomocnika_szeryfa();
						else if(skazany.getClass() == Szeryf.class) ja.zabilem_szeryfa();
					}
					System.out.println("      STRZEL " + (gracze.indexOf(skazany) + 1));
					zaatakowalem_kogos = true;
					pulaAkcji.oddaje_karte(Akcja.STRZEL);
				}
			}
		}
		return zaatakowalem_kogos;
	}
}

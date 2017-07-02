// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StrategiaBandytyCierpliwa extends StrategiaBandyty
{
	@Override
	public boolean wykonaj_ruch(List<Gracz> gracze, int numer_gracza, PulaAkcji pulaAkcji, Set<Gracz> gracze_ktorzy_strzelali_do_mnie, Gracz szeryf) {

		Gracz ja = gracze.get(numer_gracza);
		int zasieg = ja.getZasieg();
		Akcja karty[] = ja.getKarty();
		for(int i = 0; i < 5; i++)
		{
			if(karty[i] == Akcja.STRZEL)
			{
				boolean czy_mam_szeryfa_w_zasiegu = czy_szeryf_w_zasiegu(gracze, numer_gracza, zasieg, szeryf);
				if(czy_mam_szeryfa_w_zasiegu)
				{
					szeryf.przyjmij_obrazenia(gracze.get(numer_gracza));
					return true;
				}
			}
		}
		uzyj_dynamitu(gracze, numer_gracza, szeryf, karty);
		return false;
	}
}

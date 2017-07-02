// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.*;

public class StrategiaBandytyDomyslna extends StrategiaBandyty
{

    @Override
    public boolean wykonaj_ruch(List<Gracz> gracze, int numer_gracza, PulaAkcji pulaAkcji,
                                Set<Gracz> gracze_ktorzy_strzelali_do_mnie, Gracz szeryf) {
        boolean zaatakowalem_kogos = wykonaj_ruch_domyslny(gracze, numer_gracza, pulaAkcji, gracze_ktorzy_strzelali_do_mnie, szeryf);
        Akcja[] karty = gracze.get(numer_gracza).getKarty();
        uzyj_dynamitu(gracze, numer_gracza, szeryf, karty);
        return zaatakowalem_kogos;
    }
}

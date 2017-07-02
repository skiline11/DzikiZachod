// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.*;

public class StrategiaPomocnikaSzeryfaZliczajaca extends StrategiaPomocnikaSzeryfa {

    @Override
    public boolean wykonaj_ruch(List<Gracz> gracze, int numer_gracza, PulaAkcji pulaAkcji, Set<Gracz> gracze_ktorzy_strzelali_do_mnie, Gracz szeryf)
    {
        return ruch_strategii_zliczajacej(gracze, numer_gracza, pulaAkcji, gracze_ktorzy_strzelali_do_mnie, szeryf);
    }
}

// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.List;
import java.util.Set;

public abstract class Strategia {
	public abstract boolean wykonaj_ruch(List<Gracz> gracze, int numer_gracza, PulaAkcji pulaAkcji,
			Set<Gracz> gracze_ktorzy_strzelali_do_mnie, Gracz szeryf);
}

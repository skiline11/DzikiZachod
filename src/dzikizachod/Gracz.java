// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.List;
import java.util.Set;

public abstract class Gracz {

	public abstract String getOpis();

	public abstract void postawDynamit();

	public abstract void przechodziDynamit();

	public abstract int getMaxZycia();

	public abstract int getZycia();

	public abstract int getZasieg();

	public abstract Akcja[] getKarty();

	public abstract void dobierz_karty(PulaAkcji pulaAkcji);

	public abstract void sprubuj_zwiekszyc_zasieg(PulaAkcji pulaAkcji);

	public abstract boolean sprubuj_uleczyc(Class klasa, List<Gracz> gracze, PulaAkcji pulaAkcji);

	public abstract boolean wypisz_opis_gracza(List<Gracz> gracze, int numer_gracza);

	public abstract boolean wykonaj_ruch_zgodny_ze_strategia(List<Gracz> gracze, int numer_gracza, PulaAkcji pulaAkcji,
															 Gracz szeryf);

	public abstract void przyjmij_obrazenia(Gracz gracz);

	public abstract boolean dodaj_zycie();

	public abstract int getIlu_zabitych_szeryfow();

	public abstract int getIlu_zabitych_pomocnikow();

	public abstract int getIlu_zabitych_bandytow();

	public abstract Set<Gracz> getGracze_ktorzy_mnie_atakowali();

	public abstract void zabilem_bandyte();

	public abstract void zabilem_pomocnika_szeryfa();

	public abstract void zabilem_szeryfa();

}
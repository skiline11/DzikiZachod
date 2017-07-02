// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.*;

public class StrategiaPomocnikaSzeryfaDomyslna extends StrategiaPomocnikaSzeryfa {

    private Set<Gracz> kto_zyje_i_jest_w_poblizu(List<Gracz> gracze, int numer_gracza, int zasieg, Gracz szeryf)
    {
        Set<Gracz> set = new HashSet<>();
        int nr_przeciwnika_w_lewo = (numer_gracza + gracze.size() - 1) % gracze.size();
        int nr_przeciwnika_w_prawo = (numer_gracza + 1) % gracze.size();
        int org_zasieg = zasieg;

        while(nr_przeciwnika_w_lewo != numer_gracza && zasieg != 0)
        {
            while(gracze.get(nr_przeciwnika_w_lewo).getZycia() == 0 && nr_przeciwnika_w_lewo != numer_gracza)
            {
                nr_przeciwnika_w_lewo = (nr_przeciwnika_w_lewo + gracze.size() - 1) % gracze.size();
            }
            if(nr_przeciwnika_w_lewo != numer_gracza)
            {
                if(gracze.get(nr_przeciwnika_w_lewo) != szeryf)
                {
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
                if(gracze.get(nr_przeciwnika_w_prawo) != szeryf)
                {
                    set.add(gracze.get(nr_przeciwnika_w_prawo));
                }
                nr_przeciwnika_w_prawo = (nr_przeciwnika_w_prawo + 1) % gracze.size();
            }
            zasieg--;
        }
        return set;
    }

    private boolean czy_zyja_bandyci(List<Gracz> gracze)
    {
        for(Gracz g : gracze)
        {
            if(g.getZycia() != 0 && g.getClass() == Bandyta.class) return true;
        }
        return false;
    }

    public boolean wykonaj_ruch(List<Gracz> gracze, int numer_gracza, PulaAkcji pulaAkcji,
                                               Set<Gracz> gracze_ktorzy_strzelali_do_mnie, Gracz szeryf)
    {
        Random rand = new Random();
        boolean zaatakowalem_kogos = false;

        Gracz ja = gracze.get(numer_gracza);
        int zasieg = ja.getZasieg();
        Akcja karty[] = ja.getKarty();
        for(int i = 0; i < 5 && czy_zyja_bandyci(gracze) == true; i++) {
            if (karty[i] == Akcja.STRZEL) {
                Set<Gracz> kto_zyje_i_jest_w_poblizu = kto_zyje_i_jest_w_poblizu(gracze, numer_gracza, zasieg, szeryf);
                int ile = kto_zyje_i_jest_w_poblizu.size();
                if (ile != 0) {
                    int nr_skazanego = rand.nextInt(ile);
                    Iterator<Gracz> iter = kto_zyje_i_jest_w_poblizu.iterator();
                    while (nr_skazanego != 0) {
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
                    zaatakowalem_kogos = true;
                    System.out.println("      STRZEL " + (gracze.indexOf(skazany) + 1));
                    pulaAkcji.oddaje_karte(Akcja.STRZEL);
                }
            }
        }
        uzyj_dynamitu(gracze, numer_gracza, szeryf, karty);
        return zaatakowalem_kogos;
    }
}

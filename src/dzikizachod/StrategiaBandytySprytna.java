// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.*;

public class StrategiaBandytySprytna extends StrategiaBandyty {


    private int policz_ataki(Akcja karty[])
    {
        int ile = 0;
        for(int i = 0; i < 5; i++)
        {
            if(karty[i] != null && karty[i] == Akcja.STRZEL) ile++;
        }
        return ile;
    }

    private Gracz znajdz_najlepszego_do_ataku(List<Gracz> gracze, int numer_gracza, int zasieg, int ile_atakow)
    {
        int nr_przeciwnika_w_lewo = numer_gracza;
        int nr_przeciwnika_w_prawo = numer_gracza;
        int org_zasieg = zasieg;

        Gracz kandydat = null;

        while(zasieg != 0 && nr_przeciwnika_w_lewo != numer_gracza && kandydat == null)
        {
            while(gracze.get(nr_przeciwnika_w_lewo).getZycia() == 0)
            {
                nr_przeciwnika_w_lewo = (nr_przeciwnika_w_lewo + gracze.size() - 1) % gracze.size();
            }
            if(nr_przeciwnika_w_lewo != numer_gracza)
            {
                if(gracze.get(nr_przeciwnika_w_lewo).getClass() == Bandyta.class)
                {
                    kandydat = gracze.get(nr_przeciwnika_w_lewo);
                    if(kandydat.getZycia() <= ile_atakow) return kandydat;
                    else kandydat = null;
                }
                nr_przeciwnika_w_lewo = (nr_przeciwnika_w_lewo + gracze.size() - 1) % gracze.size();
            }
            zasieg--;
        }
        zasieg = org_zasieg;
        while(zasieg != 0 && nr_przeciwnika_w_prawo != numer_gracza && kandydat == null)
        {
            while(gracze.get(nr_przeciwnika_w_prawo).getZycia() == 0)
            {
                nr_przeciwnika_w_prawo = (nr_przeciwnika_w_prawo + 1)%gracze.size();
            }
            if(nr_przeciwnika_w_prawo != numer_gracza)
            {
                if(gracze.get(nr_przeciwnika_w_prawo).getClass() == Bandyta.class)
                {
                    kandydat = gracze.get(nr_przeciwnika_w_lewo);
                    if(kandydat.getZycia() <= ile_atakow) return kandydat;
                    else kandydat = null;
                }
                nr_przeciwnika_w_prawo = (nr_przeciwnika_w_prawo + 1)%gracze.size();
            }
            zasieg--;
        }
        return null;
    }

    @Override
    public boolean wykonaj_ruch(List<Gracz> gracze, int numer_gracza, PulaAkcji pulaAkcji, Set<Gracz> gracze_ktorzy_strzelali_do_mnie, Gracz szeryf)
    {
        Random rand = new Random();
        boolean zaatakowalem_kogos = false;

        Gracz ja = gracze.get(numer_gracza);
        int zasieg = ja.getZasieg();
        Akcja karty[] = ja.getKarty();

        int ile_atakow = policz_ataki(karty);
        Gracz do_ataku = znajdz_najlepszego_do_ataku(gracze, numer_gracza, zasieg, ile_atakow);
        if(do_ataku != null)
        {
            int ile_atakow_wykorzystuje = ile_atakow - do_ataku.getZycia();
            int nr_karty = 0;
            for(int i = 0; i < ile_atakow_wykorzystuje; i++)
            {
                do_ataku.przyjmij_obrazenia(ja);
                while(karty[nr_karty] != Akcja.STRZEL) nr_karty++;
                karty[nr_karty] = null;
                pulaAkcji.oddaje_karte(Akcja.STRZEL);
            }
            ja.zabilem_bandyte();
            zaatakowalem_kogos = true;
        }
        zaatakowalem_kogos = zaatakowalem_kogos || wykonaj_ruch_domyslny(gracze, numer_gracza, pulaAkcji, gracze_ktorzy_strzelali_do_mnie, szeryf);
        uzyj_dynamitu(gracze, numer_gracza, szeryf, karty);
        return zaatakowalem_kogos;
    }
}

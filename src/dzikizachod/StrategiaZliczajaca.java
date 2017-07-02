// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.*;

public class StrategiaZliczajaca extends Strategia {

    public Set<Gracz> kto_zaatakowal_szeryfa_lub_zabil_wiecej_pomoc_niz_ban(List<Gracz> gracze, int numer_gracza, Set<Gracz> ci_ktorzy_mnie_atakowali, int zasieg, Gracz szeryf)
    {
        int nr_przeciwnika_w_lewo = (numer_gracza + gracze.size() - 1) % gracze.size();
        int nr_przeciwnika_w_prawo = (numer_gracza + 1)%gracze.size();
        int org_zasieg = zasieg;
        for(Gracz g : gracze)
        {
            if(g != szeryf && g.getIlu_zabitych_pomocnikow() > g.getIlu_zabitych_bandytow())
            {
                ci_ktorzy_mnie_atakowali.add(g);
            }
        }

        Set<Gracz> dystans_ok = new HashSet<>();
        while(nr_przeciwnika_w_lewo != numer_gracza && zasieg != 0)
        {
            while(gracze.get(nr_przeciwnika_w_lewo).getZycia() == 0 && nr_przeciwnika_w_lewo != numer_gracza)
            {
                nr_przeciwnika_w_lewo = (nr_przeciwnika_w_lewo + gracze.size() - 1) % gracze.size();
            }
            if(nr_przeciwnika_w_lewo != numer_gracza)
            {
                if(ci_ktorzy_mnie_atakowali.contains(gracze.get(nr_przeciwnika_w_lewo)))
                {
                    dystans_ok.add(gracze.get(nr_przeciwnika_w_lewo));
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
                if(ci_ktorzy_mnie_atakowali.contains(gracze.get(nr_przeciwnika_w_prawo)))
                {
                    dystans_ok.add(gracze.get(nr_przeciwnika_w_prawo));
                }
                nr_przeciwnika_w_prawo = (nr_przeciwnika_w_prawo + 1) % gracze.size();
            }
            zasieg--;
        }
        return dystans_ok;
    }

    public boolean ruch_strategii_zliczajacej(List<Gracz> gracze, int numer_gracza, PulaAkcji pulaAkcji, Set<Gracz> gracze_ktorzy_strzelali_do_mnie, Gracz szeryf)
    {
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
                Set<Gracz> kto_mnie_zaatakowal_lub_zabil_wiecej_pomoc_niz_ban = kto_zaatakowal_szeryfa_lub_zabil_wiecej_pomoc_niz_ban(gracze, numer_gracza, ci_ktorzy_mnie_atakowali, zasieg, szeryf);

                int ile = kto_mnie_zaatakowal_lub_zabil_wiecej_pomoc_niz_ban.size();
                if(ile != 0)
                {
                    int nr_skazanego = rand.nextInt(ile);
                    Iterator<Gracz> iter = kto_mnie_zaatakowal_lub_zabil_wiecej_pomoc_niz_ban.iterator();
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

    @Override
    public boolean wykonaj_ruch(List<Gracz> gracze, int numer_gracza, PulaAkcji pulaAkcji, Set<Gracz> gracze_ktorzy_strzelali_do_mnie, Gracz szeryf) {
        return false;
    }
}

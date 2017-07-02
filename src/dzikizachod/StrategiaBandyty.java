// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.*;

public abstract class StrategiaBandyty extends StrategiaZliczajaca {

    public int ile_osob_miedzy_bandyta_a_szeryfem(List<Gracz> gracze, int numer_gracza, Gracz szeryf)
    {
        int nr_przeciwnika_w_prawo = numer_gracza;
        int dystans_do_szeryfa_z_prawej = 0;
        while(gracze.get(nr_przeciwnika_w_prawo) != szeryf)
        {
            nr_przeciwnika_w_prawo = (nr_przeciwnika_w_prawo + 1) % gracze.size();
            while(gracze.get(nr_przeciwnika_w_prawo).getZycia() == 0)
            {
                nr_przeciwnika_w_prawo = (nr_przeciwnika_w_prawo + 1) % gracze.size();
            }
            if(gracze.get(nr_przeciwnika_w_prawo).getZycia() != 0) dystans_do_szeryfa_z_prawej++;
        }
        return dystans_do_szeryfa_z_prawej;
    }

    public void uzyj_dynamitu(List<Gracz> gracze, int numer_gracza, Gracz szeryf, Akcja[] karty)
    {
        for(int i = 0; i < 5; i++)
        {
            if(karty[i] == Akcja.DYNAMIT)
            {
                int ile_osob = ile_osob_miedzy_bandyta_a_szeryfem(gracze, numer_gracza, szeryf);
                if(ile_osob < 3)
                {
                    int numer_ofiary = (numer_gracza + 1) % gracze.size();
                    while(gracze.get(numer_ofiary).getZycia() == 0)
                    {
                        numer_ofiary = (numer_ofiary + 1) % gracze.size();
                    }
                    karty[i] = null;
                    gracze.get(numer_ofiary).postawDynamit();
                }
            }
        }
    }

    public boolean czy_szeryf_w_zasiegu(List<Gracz> gracze, int numer_gracza, int zasieg, Gracz szeryf)
    {
        int nr_przeciwnika_w_lewo = numer_gracza - 1;
        if(nr_przeciwnika_w_lewo == -1) nr_przeciwnika_w_lewo = gracze.size() - 1;
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
                if(gracze.get(nr_przeciwnika_w_lewo) == szeryf) return true;
                else
                {
                    nr_przeciwnika_w_lewo = (nr_przeciwnika_w_lewo + gracze.size() - 1) % gracze.size();
                }
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
                if(gracze.get(nr_przeciwnika_w_prawo) == szeryf) return true;
                else
                {
                    nr_przeciwnika_w_prawo = (nr_przeciwnika_w_prawo + 1) % gracze.size();
                }

            }
            zasieg--;
        }
        return false;
    }

    public Set<Gracz> kto_zyje_i_jest_w_poblizu(List<Gracz> gracze, int numer_gracza, int zasieg, Gracz szeryf)
    {
        Set<Gracz> set = new HashSet<>();
        int nr_przeciwnika_w_lewo = numer_gracza;
        int nr_przeciwnika_w_prawo = numer_gracza;
        int org_zasieg = zasieg;

        int dystans_do_szeryfa_z_lewej = 0;
        while(gracze.get(nr_przeciwnika_w_lewo) != szeryf)
        {
            nr_przeciwnika_w_lewo = (nr_przeciwnika_w_lewo + gracze.size() - 1) % gracze.size();
            while(gracze.get(nr_przeciwnika_w_lewo).getZycia() == 0)
            {
                nr_przeciwnika_w_lewo = (nr_przeciwnika_w_lewo + gracze.size() - 1) % gracze.size();
            }
            if(gracze.get(nr_przeciwnika_w_lewo).getZycia() != 0) dystans_do_szeryfa_z_lewej++;
        }
        int dystans_do_szeryfa_z_prawej = 0;
        while(gracze.get(nr_przeciwnika_w_prawo) != szeryf)
        {
            nr_przeciwnika_w_prawo = (nr_przeciwnika_w_prawo + 1) % gracze.size();
            while(gracze.get(nr_przeciwnika_w_prawo).getZycia() == 0)
            {
                nr_przeciwnika_w_prawo = (nr_przeciwnika_w_prawo + 1) % gracze.size();
            }
            if(gracze.get(nr_przeciwnika_w_prawo).getZycia() != 0) dystans_do_szeryfa_z_prawej++;
        }
        if(dystans_do_szeryfa_z_lewej <= dystans_do_szeryfa_z_prawej)
        {
            nr_przeciwnika_w_lewo = (numer_gracza + gracze.size() - 1) % gracze.size();
            while(zasieg != 0 && nr_przeciwnika_w_lewo != numer_gracza)
            {
                while(gracze.get(nr_przeciwnika_w_lewo).getZycia() == 0)
                {
                    nr_przeciwnika_w_lewo = (nr_przeciwnika_w_lewo + gracze.size() - 1) % gracze.size();
                }
                if(nr_przeciwnika_w_lewo != numer_gracza)
                {
                    if(gracze.get(nr_przeciwnika_w_lewo).getClass() != Bandyta.class)
                    {
                        set.add(gracze.get(nr_przeciwnika_w_lewo));
                    }
                    nr_przeciwnika_w_lewo = (nr_przeciwnika_w_lewo + gracze.size() - 1) % gracze.size();
                }
                zasieg--;
            }
        }

        else if(dystans_do_szeryfa_z_lewej > dystans_do_szeryfa_z_prawej || set.isEmpty())
        {
            nr_przeciwnika_w_prawo = (numer_gracza + 1)%gracze.size();
            while(zasieg != 0 && nr_przeciwnika_w_prawo != numer_gracza)
            {
                while(gracze.get(nr_przeciwnika_w_prawo).getZycia() == 0)
                {
                    nr_przeciwnika_w_prawo = (nr_przeciwnika_w_prawo + 1)%gracze.size();
                }
                if(nr_przeciwnika_w_prawo != numer_gracza)
                {
                    if(gracze.get(nr_przeciwnika_w_prawo).getClass() != Bandyta.class)
                    {
                        set.add(gracze.get(nr_przeciwnika_w_prawo));
                    }
                    nr_przeciwnika_w_prawo = (nr_przeciwnika_w_prawo + 1) % gracze.size();
                }
                zasieg--;
            }
        }

        return set;
    }

    @Override
    public boolean wykonaj_ruch(List<Gracz> gracze, int numer_gracza, PulaAkcji pulaAkcji, Set<Gracz> gracze_ktorzy_strzelali_do_mnie, Gracz szeryf) {
        return false;
    }

    public boolean wykonaj_ruch_domyslny(List<Gracz> gracze, int numer_gracza, PulaAkcji pulaAkcji,
                                         Set<Gracz> gracze_ktorzy_strzelali_do_mnie, Gracz szeryf)
    {
        Random rand = new Random();
        boolean zaatakowalem_kogos = false;

        Gracz ja = gracze.get(numer_gracza);
        int zasieg = ja.getZasieg();
        Akcja karty[] = ja.getKarty();

        // NORMALNA STRATEGIA DOMYSLNA
        for(int i = 0; i < 5 && szeryf.getZycia() != 0; i++) {
            if (karty[i] == Akcja.STRZEL) {
                Gracz skazany = null;
                boolean czy_mam_szeryfa_w_zasiegu = czy_szeryf_w_zasiegu(gracze, numer_gracza, zasieg, szeryf);
                if(czy_mam_szeryfa_w_zasiegu)
                {
                    skazany = szeryf;
                }
                else
                {
                    Set<Gracz> kto_zyje_i_jest_w_poblizu = kto_zyje_i_jest_w_poblizu(gracze, numer_gracza, zasieg, szeryf);
                    int ile = kto_zyje_i_jest_w_poblizu.size();
                    if(ile != 0)
                    {
                        int nr_skazanego = rand.nextInt(ile);
                        Iterator<Gracz> iter = kto_zyje_i_jest_w_poblizu.iterator();
                        while (nr_skazanego != 0) {
                            iter.next();
                            nr_skazanego--;
                        }
                        skazany = iter.next();
                    }
                }
                if(skazany != null)
                {
                    skazany.przyjmij_obrazenia(ja);
                    if(skazany.getZycia() == 0)
                    {
                        if(skazany.getClass() == Bandyta.class) ja.zabilem_bandyte();
                        else if(skazany.getClass() == PomocnikSzeryfa.class) ja.zabilem_pomocnika_szeryfa();
                        else if(skazany.getClass() == Szeryf.class) ja.zabilem_szeryfa();
                    }
                    zaatakowalem_kogos = true;
                    System.out.println("      STRZEL " + (gracze.indexOf(skazany) + 1));

                    karty[i] = null;
                    pulaAkcji.oddaje_karte(Akcja.STRZEL);
                }
            }
        }
        //STAWIAM DYNAMIT
        for(int i = 0; i < 5; i++)
        {
            if(karty[i] != null)
            {
                if(karty[i] == Akcja.DYNAMIT)
                {
                    int numer_ofiary = (numer_gracza + 1) % gracze.size();
                    while(gracze.get(numer_ofiary).getZycia() == 0)
                    {
                        numer_ofiary = (numer_ofiary + 1) % gracze.size();
                    }
                    gracze.get(numer_ofiary).postawDynamit();
                    karty[i] = null;
                }
            }
        }
        return zaatakowalem_kogos;
    }
}

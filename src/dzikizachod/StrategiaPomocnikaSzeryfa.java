// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.List;
import java.util.Set;

public abstract class StrategiaPomocnikaSzeryfa extends StrategiaZliczajaca {
    public int ile_osob_miedzy_pomocnikiem_a_szeryfem_z_prawej(List<Gracz> gracze, int numer_gracza, Gracz szeryf)
    {
        int ile_osob = 0;
        int numer = (numer_gracza + 1) % gracze.size();
        while(gracze.get(numer) != szeryf)
        {
            if(gracze.get(numer).getZycia() != 0) ile_osob++;
            numer = (numer + 1) % gracze.size();
        }
        return ile_osob;
    }

    public int ile_osob_jest_podejrzewanych(List<Gracz> gracze, int numer_gracza, Gracz szeryf)
    {
        int ile_osob = 0;
        int numer = (numer_gracza + 1) % gracze.size();
        while(gracze.get(numer) != szeryf)
        {
            if(gracze.get(numer).getZycia() != 0)
            {
                if(szeryf.getGracze_ktorzy_mnie_atakowali().contains(gracze.get(numer))) ile_osob++;
                else if(gracze.get(numer).getIlu_zabitych_pomocnikow() - gracze.get(numer).getIlu_zabitych_bandytow() > 0)
                {
                    ile_osob++;
                }
            }
            numer = (numer + 1) % gracze.size();
        }
        return ile_osob;
    }

    public void uzyj_dynamitu(List<Gracz> gracze, int numer_gracza, Gracz szeryf, Akcja[] karty)
    {
        for(int i = 0; i < 5; i++)
        {
            if(karty[i] == Akcja.DYNAMIT)
            {
                int ile_osob_z_prawej = ile_osob_miedzy_pomocnikiem_a_szeryfem_z_prawej(gracze, numer_gracza, szeryf);
                if(ile_osob_z_prawej > 3)
                {
                    int ile_osob_jest_podejrzewanych_o_bycie_bandyta = ile_osob_jest_podejrzewanych(gracze, numer_gracza, szeryf);
                    if(2 * ile_osob_z_prawej <= 3 * ile_osob_jest_podejrzewanych_o_bycie_bandyta)
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
    }

    @Override
    public boolean wykonaj_ruch(List<Gracz> gracze, int numer_gracza, PulaAkcji pulaAkcji, Set<Gracz> gracze_ktorzy_strzelali_do_mnie, Gracz szeryf) {
        return false;
    }
}

// Michał Radecki - mr371591 - Zadanie nr 1 (DUZE z PO)

package dzikizachod;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class Gracz_Standardowy extends Gracz {
    private int max_zycia;
    private int zycia;
    private Strategia strategia;
    private Akcja[] karty;
    private int zasieg, domyslny_zasieg;
    private int ilu_zabitych_szeryfow, ilu_zabitych_pomocnikow, ilu_zabitych_bandytow;
    private boolean dynamit_przede_mna;
    private Set<Gracz> gracze_ktorzy_atakowali_mnie;

    public Gracz_Standardowy(Class klasa)
    {
        this.ilu_zabitych_bandytow = this.ilu_zabitych_pomocnikow = this.ilu_zabitych_szeryfow = 0;

        Random rand = new Random();

        if(klasa == Bandyta.class)
        {
            if(rand.nextBoolean() == true) this.zycia = 3;
            else this.zycia = 4;
            this.strategia = new StrategiaBandytyDomyslna();
        }
        else if(klasa == PomocnikSzeryfa.class)
        {
            if(rand.nextBoolean()) this.zycia = 3;
            else this.zycia = 4;
            this.strategia = new StrategiaPomocnikaSzeryfaDomyslna();
        }
        else if(klasa == Szeryf.class)
        {
            this.zycia = 5;
            this.strategia = new StrategiaSzeryfaDomyslna();
        }
        this.max_zycia = this.zycia;
        this.karty = new Akcja[5];
        for(int i = 0; i < 5; i++) this.karty[i] = null;
        this.gracze_ktorzy_atakowali_mnie = new HashSet<>();
        this.zasieg = this.domyslny_zasieg = 1;
        this.dynamit_przede_mna = false;
    }

    public Gracz_Standardowy(Class klasa, Class klasa_strategii)
    {
        this.ilu_zabitych_bandytow = this.ilu_zabitych_pomocnikow = this.ilu_zabitych_szeryfow = 0;
        Random rand = new Random();

        if(klasa == Bandyta.class)
        {
            if(rand.nextBoolean()) this.zycia = 3;
            else this.zycia = 4;
            if(klasa_strategii == StrategiaBandytyCierpliwa.class) this.strategia = new StrategiaBandytyCierpliwa();
            else if(klasa_strategii == StrategiaBandytySprytna.class) this.strategia = new StrategiaBandytySprytna();
        }
        else if(klasa == PomocnikSzeryfa.class)
        {
            if(rand.nextBoolean()) this.zycia = 3;
            else this.zycia = 4;
            if(klasa_strategii == StrategiaPomocnikaSzeryfaDomyslna.class) this.strategia = new StrategiaSzeryfaDomyslna();
            else if(klasa_strategii == StrategiaPomocnikaSzeryfaZliczajaca.class) this.strategia = new StrategiaPomocnikaSzeryfaZliczajaca();
        }
        else if(klasa == Szeryf.class)
        {
            this.zycia = 5;
            if(klasa_strategii == StrategiaSzeryfaZliczajaca.class) this.strategia = new StrategiaSzeryfaZliczajaca();

        }
        this.max_zycia = this.zycia;
        this.karty = new Akcja[5];
        this.gracze_ktorzy_atakowali_mnie = new HashSet<>();
        this.zasieg = this.domyslny_zasieg = 1;
        this.dynamit_przede_mna = false;
    }

    public Set<Gracz> getGracze_ktorzy_mnie_atakowali() {return this.gracze_ktorzy_atakowali_mnie;};

    public void postawDynamit() {
        this.dynamit_przede_mna = true;
        System.out.println("      DYNAMIT");
    }

    public void przechodziDynamit() {
        this.dynamit_przede_mna = true;
    }

    public int getMaxZycia() { return this.max_zycia; }
    public int getZycia() {
        return this.zycia;
    }
    public int getZasieg() {
        return this.zasieg;
    }
    public Akcja[] getKarty() { return this.karty; }
    public boolean czyDynamitPrzedeMna() { return this.dynamit_przede_mna; }

    public void dobierz_karty(PulaAkcji pulaAkcji) {
        for(int i = 0; i < 5; i++)
        {
            if(this.karty[i] == null) this.karty[i] = pulaAkcji.daj_karte();
        }
    }

    public void sprubuj_zwiekszyc_zasieg(PulaAkcji pulaAkcji) {
        for(int i = 0; i < 5; i++)
        {
            if(this.karty[i] != null)
            {
                if(this.karty[i] == Akcja.ZASIEG_PLUS_JEDEN)
                {
                    this.zasieg += 1;
                    pulaAkcji.oddaje_karte(this.karty[i]);
                    this.karty[i] = null;
                    System.out.println("      ZASIEG_PLUS_JEDEN -> zasieg = " + this.zasieg);
                }
                else if(this.karty[i] == Akcja.ZASIEG_PLUS_DWA)
                {
                    this.zasieg += 2;
                    pulaAkcji.oddaje_karte(this.karty[i]);
                    this.karty[i] = null;
                    System.out.println("      ZASIEG_PLUS_DWA -> zasieg = " + this.zasieg);
                }
            }
        }
    }

    public boolean sprubuj_uleczyc(Class klasa, List<Gracz> gracze, PulaAkcji pulaAkcji)
    {
        boolean czy_leczylem = false;
        if(klasa == PomocnikSzeryfa.class)
        {
            Gracz szeryf = gracze.get(0);
            for(Gracz gracz : gracze)
            {
                if(gracz.getClass() == Szeryf.class)
                {
                    szeryf = gracz;
                }
            }
            for(int i = 0; i < 5; i++)
            {
                if(this.karty[i] != null)
                {
                    if(this.karty[i] == Akcja.ULECZ)
                    {
                        if(szeryf.dodaj_zycie() == true)
                        {
                            pulaAkcji.oddaje_karte(this.karty[i]);
                            this.karty[i] = null;
                            czy_leczylem = true;
                            System.out.println("      ULECZ " + (gracze.indexOf(szeryf) + 1));
                        }
                        else if(this.zycia != this.max_zycia)
                        {
                            this.zycia++;
                            pulaAkcji.oddaje_karte(this.karty[i]);
                            this.karty[i] = null;
                            czy_leczylem = true;
                            System.out.println("      ULECZ");
                        }
                    }
                }
            }
        }
        else
        {
            for(int i = 0; i < 5; i++)
            {
                if(this.karty[i] != null)
                {
                    if(this.karty[i] == Akcja.ULECZ)
                    {
                        if(this.zycia != this.max_zycia)
                        {
                            this.zycia++;
                            pulaAkcji.oddaje_karte(this.karty[i]);
                            this.karty[i] = null;
                            czy_leczylem = true;
                            System.out.println("      ULECZ");
                        }
                    }
                }
            }
        }
        return czy_leczylem;
    }

    public boolean wypisz_opis_gracza(List<Gracz> gracze, int numer_gracza)
    {
        System.out.println("  GRACZ " + (numer_gracza + 1) + " (" + gracze.get(numer_gracza).getClass().getSimpleName() + "):");
        if(this.getZycia() == 0)
        {
            System.out.println("      MARTWY");
            return false;
        }
        else
        {
            System.out.print("    Akcje: [");
            boolean pierwszy = true;
            for(int i = 0; i < 5; i++)
            {
                if(this.karty[i] != null)
                {
                    if(pierwszy == false)
                    {
                        System.out.print(", ");
                    }
                    System.out.print(this.karty[i]);
                    pierwszy = false;
                }
                else System.out.print("ERROR,");
            }
            System.out.println("]");
            boolean oberwalem_dynamitem = odpalamy_dynamit(gracze, numer_gracza);
            System.out.println("    Ruchy:");
            return oberwalem_dynamitem;
        }

    }

    public boolean odpalamy_dynamit(List<Gracz> gracze, int numer_gracza)
    {
        if(this.dynamit_przede_mna == true)
        {
            Random rand = new Random();
            int los = rand.nextInt(6) + 1;
            if(los == 1) // zadaje mi obrazenia
            {
                System.out.println("    Dynamit: WYBUCHŁ");
                this.dynamit_przede_mna = false;
                this.zycia -= 3;
                if(this.zycia < 0) this.zycia = 0;
                return true;
            }
            else
            {
                System.out.println("    Dynamit: PRZECHODZI DALEJ");
                this.dynamit_przede_mna = false;
                int gdzie_dynamit = (numer_gracza + 1) % gracze.size();
                while(gracze.get(gdzie_dynamit).getZycia() == 0)
                {
                    gdzie_dynamit = (gdzie_dynamit + 1) % gracze.size();
                }
                gracze.get(gdzie_dynamit).przechodziDynamit();
            }
        }
        return false;
    }

    public boolean wykonaj_ruch_zgodny_ze_strategia(List<Gracz> gracze, int numer_gracza, PulaAkcji pulaAkcji, Gracz szeryf) {
//            System.out.println("Dobieram karty");
        dobierz_karty(pulaAkcji);
//            System.out.println("Ustawiam domyslny zasieg");
        this.zasieg = this.domyslny_zasieg;
//            System.out.println("Wypisuje opis gracza");
        boolean oberwalem_dynamitem = wypisz_opis_gracza(gracze, numer_gracza);
        if(this.zycia == 0)
        {
            System.out.println("     MARTWY");
            return true;
        }
        else
        {
//            System.out.println("Prubuje zwiekszyc zasieg");
            sprubuj_zwiekszyc_zasieg(pulaAkcji);
//            System.out.println("Prubuje leczyc");
            Boolean czy_leczylem = sprubuj_uleczyc(gracze.get(numer_gracza).getClass(), gracze, pulaAkcji);
//            System.out.println("Czy leczylem :" + czy_leczylem);
            Boolean czy_zaszly_zmiany = this.strategia.wykonaj_ruch(gracze, numer_gracza, pulaAkcji, this.gracze_ktorzy_atakowali_mnie,
                    szeryf);
//            System.out.println("Czy zaszly zmiany : " + czy_zaszly_zmiany);
            return czy_leczylem || czy_zaszly_zmiany || oberwalem_dynamitem;
        }



    }

    public void przyjmij_obrazenia(Gracz gracz) {
        this.gracze_ktorzy_atakowali_mnie.add(gracz);
        this.zycia--;
    }

    public boolean dodaj_zycie() {
        if(this.zycia == this.max_zycia) return false;
        else
        {
            this.zycia++;
            return true;
        }
    }

    @Override
    public int getIlu_zabitych_szeryfow() {
        return ilu_zabitych_szeryfow;
    }

    @Override
    public int getIlu_zabitych_pomocnikow() {
        return ilu_zabitych_pomocnikow;
    }

    @Override
    public int getIlu_zabitych_bandytow() {
        return ilu_zabitych_bandytow;
    }

    public void zabilem_bandyte() {this.ilu_zabitych_bandytow++; }

    public void zabilem_pomocnika_szeryfa() {this.ilu_zabitych_szeryfow++; }

    public void zabilem_szeryfa() {this.ilu_zabitych_szeryfow++; }
}

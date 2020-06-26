package pdm.uninsubria.progetto;

import java.io.Serializable;
import java.util.ArrayList;

public class Categoria implements Serializable {
    private ArrayList<String> elenco = new ArrayList<>();

    public ArrayList<String> getElenco() {
        return elenco;
    }

    public void aggiungiElenco(String categoria) {
        this.elenco.add(categoria);
    }

    public void rimuoviElenco(String categoria) {
        this.elenco.remove(categoria);
    }
}

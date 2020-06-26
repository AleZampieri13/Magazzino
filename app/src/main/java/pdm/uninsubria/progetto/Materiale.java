package pdm.uninsubria.progetto;

import java.io.Serializable;

public class Materiale implements Serializable {
    private String nome;
    private String codice;
    private String descrizione;
    private String categoria;
    private int quantita;
    private int quantitaMinima;

    public Materiale(String nome, String codice, String descrizione, String categoria, int quantita, int quantitaMinima) {
        this.nome = nome;
        this.codice = codice;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.quantita = quantita;
        this.quantitaMinima = quantitaMinima;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantitaMinima() {
        return quantitaMinima;
    }

    public void setQuantitaMinima(int quantitaMinima) {
        this.quantitaMinima = quantitaMinima;
    }
}

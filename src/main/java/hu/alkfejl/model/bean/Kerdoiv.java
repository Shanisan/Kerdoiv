package hu.alkfejl.model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Kerdoiv {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nev = new SimpleStringProperty();

    public int getKerdesekSzama() {
        return kerdesekSzama.get();
    }

    public IntegerProperty kerdesekSzamaProperty() {
        return kerdesekSzama;
    }

    public void setKerdesekSzama(int kerdesekSzama) {
        this.kerdesekSzama.set(kerdesekSzama);
    }

    private IntegerProperty kerdesekSzama = new SimpleIntegerProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNev() {
        return nev.get();
    }

    public StringProperty nevProperty() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev.set(nev);
    }

    @Override
    public String toString() {
        return "Kerdoiv{" +
                "id=" + id +
                ", nev=" + nev +
                ", kerdesek szama=" + kerdesekSzama +
                '}';
    }

    public Kerdoiv(int id, String nev, int kerdesekSzama) {
        this.setId(id);
        this.setNev(nev);
        this.setKerdesekSzama(kerdesekSzama);
    }

    public Kerdoiv(String nev) {
        this.setNev(nev);
    }
}

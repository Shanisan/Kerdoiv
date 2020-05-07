package hu.alkfejl.model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Time;
import java.sql.Timestamp;

public class Kerdoiv {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nev = new SimpleStringProperty();
    private IntegerProperty kitoltesek = new SimpleIntegerProperty();
    private Timestamp kezdet;
    private Timestamp vege;
    private IntegerProperty ido = new SimpleIntegerProperty();
    private StringProperty letrehozo = new SimpleStringProperty();
    private StringProperty link = new SimpleStringProperty();
//region getter-setter

    public String getLink() {return link.get();}

    public StringProperty linkProperty() {return link;}

    public void setLink(String link) {this.link.set(link);}

    public int getKitoltesek() {return kitoltesek.get();}

    public Timestamp getKezdet() {
        return kezdet;
    }

    public void setKezdet(Timestamp kezdet) {
        this.kezdet = kezdet;
    }

    public Timestamp getVege() {
        return vege;
    }

    public void setVege(Timestamp vege) {
        this.vege = vege;
    }

    public int getIdo() {
        return ido.get();
    }

    public IntegerProperty idoProperty() {
        return ido;
    }

    public void setIdo(int ido) {
        this.ido.set(ido);
    }

    public String getLetrehozo() {
        return letrehozo.get();
    }

    public StringProperty letrehozoProperty() {
        return letrehozo;
    }

    public void setLetrehozo(String letrehozo) {
        this.letrehozo.set(letrehozo);
    }

    public IntegerProperty kitoltesekProperty() {return kitoltesek;}

    public void setKitoltesek(int kitoltesek) {this.kitoltesek.set(kitoltesek);}

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
    //endregion
    @Override
    public String toString() {
        return "Kerdoiv{" +
                "id=" + id +
                ", nev=" + nev +
                ", kerdesek szama=" + kerdesekSzama +
                '}';
    }

    public Kerdoiv(int id, String nev, int kerdesekSzama, int kitoltesek) {
        this.setId(id);
        this.setNev(nev);
        this.setKerdesekSzama(kerdesekSzama);
        this.setKitoltesek(kitoltesek);
    }

    public Kerdoiv(int id, String nev, int kerdesekSzama, int kitoltesek,
                   Timestamp kezdet, Timestamp vege, int ido, String link, String letrehozo) {
        this.setId(id);
        this.setNev(nev);
        this.setKerdesekSzama(kerdesekSzama);
        this.setKitoltesek(kitoltesek);
        this.setIdo(ido);
        this.setKezdet(kezdet);
        this.setVege(vege);
        this.setLetrehozo(letrehozo);
    }

    public Kerdoiv(String nev) {
        this.setNev(nev);
    }
}

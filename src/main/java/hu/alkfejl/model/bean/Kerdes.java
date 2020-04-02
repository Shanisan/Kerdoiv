package hu.alkfejl.model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Kerdes {
    private StringProperty szoveg = new SimpleStringProperty();
    private IntegerProperty tipus = new SimpleIntegerProperty();
    private IntegerProperty sorszam = new SimpleIntegerProperty();
    private StringProperty kep = new SimpleStringProperty();

    public Kerdes(StringProperty szoveg, IntegerProperty tipus, IntegerProperty sorszam, StringProperty kep) {
        this.szoveg = szoveg;
        this.tipus = tipus;
        this.sorszam = sorszam;
        this.kep = kep;
    }

    public String getSzoveg() {
        return szoveg.get();
    }

    public StringProperty szovegProperty() {
        return szoveg;
    }

    public void setSzoveg(String szoveg) {
        this.szoveg.set(szoveg);
    }

    public int getTipus() {
        return tipus.get();
    }

    public IntegerProperty tipusProperty() {
        return tipus;
    }

    public void setTipus(int tipus) {
        this.tipus.set(tipus);
    }

    public int getSorszam() {
        return sorszam.get();
    }

    public IntegerProperty sorszamProperty() {
        return sorszam;
    }

    public void setSorszam(int sorszam) {
        this.sorszam.set(sorszam);
    }

    public String getKep() {
        return kep.get();
    }

    public StringProperty kepProperty() {
        return kep;
    }

    public void setKep(String kep) {
        this.kep.set(kep);
    }
}

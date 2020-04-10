package hu.alkfejl.model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Valasz {
    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty kerdesID = new SimpleIntegerProperty();
    private StringProperty szoveg = new SimpleStringProperty();
    private IntegerProperty sorszam = new SimpleIntegerProperty();

    public Valasz(int id, int kerdesID, String szoveg, int sorszam) {
        this.setId(id);
        this.setKerdesID(kerdesID);
        this.setSzoveg(szoveg);
        this.setSorszam(sorszam);
    }

    public Valasz(int kerdesID, String szoveg, int sorszam) {
        this.setKerdesID(kerdesID);
        this.setSzoveg(szoveg);
        this.setSorszam(sorszam);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getKerdesID() {
        return kerdesID.get();
    }

    public IntegerProperty kerdesIDProperty() {
        return kerdesID;
    }

    public void setKerdesID(int kerdesID) {
        this.kerdesID.set(kerdesID);
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

    public int getSorszam() {
        return sorszam.get();
    }

    public IntegerProperty sorszamProperty() {
        return sorszam;
    }

    public void setSorszam(int sorszam) {
        this.sorszam.set(sorszam);
    }
}

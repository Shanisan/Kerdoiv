package hu.alkfejl.model.bean;

import hu.alkfejl.model.KerdesTipus;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Kerdes implements DatabaseObject{
    private StringProperty szoveg = new SimpleStringProperty();
    private IntegerProperty tipus = new SimpleIntegerProperty();
    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty kerdoivId = new SimpleIntegerProperty();
    private IntegerProperty sorszam = new SimpleIntegerProperty();
    private IntegerProperty valaszokSzama = new SimpleIntegerProperty();
    private StringProperty kep = new SimpleStringProperty();

    public Kerdes(int id, String szoveg, int kerdoivId, int tipus, int sorszam, String kep, int valaszokSzama) {
        this.setId(id);
        this.setKerdoivId(kerdoivId);
        this.setSzoveg(szoveg);
        this.setTipus(tipus);
        this.setSorszam(sorszam);
        this.setValaszokSzama(valaszokSzama);
        this.setKep(kep);
    }

    public Kerdes(String szoveg, String tipus, int sorszam, String kep) {
        this.setSzoveg(szoveg);
        int i=0;
        for (;i<KerdesTipus.tipusStringek.length;i++) {
            if(tipus.equals(KerdesTipus.tipusStringek[i])){break;}
        }
        this.setTipus(i);
        this.setSorszam(sorszam);
        this.setKep(kep);
    }

    //region getter-setter

    public int getKerdoivId() {return kerdoivId.get();}

    public IntegerProperty kerdoivIdProperty() {return kerdoivId;}

    public void setKerdoivId(int kerdoivId) {this.kerdoivId.set(kerdoivId);}

    public int getValaszokSzama() {return valaszokSzama.get();}

    public IntegerProperty valaszokSzamaProperty() {return valaszokSzama;}

    public void setValaszokSzama(int valaszokSzama) {this.valaszokSzama.set(valaszokSzama);}

    public int getId() {return id.get();}

    public IntegerProperty idProperty() {return id;}

    public void setId(int id) {this.id.set(id);}

    public String getSzoveg() {return szoveg.get();}

    public StringProperty szovegProperty() {return szoveg;}

    public void setSzoveg(String szoveg) {this.szoveg.set(szoveg);}

    public int getTipus() {return tipus.get();}

    public IntegerProperty tipusProperty() {return tipus;}

    public void setTipus(int tipus) {this.tipus.set(tipus);}

    public int getSorszam() {return sorszam.get();}

    public IntegerProperty sorszamProperty() {return sorszam;}

    public void setSorszam(int sorszam) {this.sorszam.set(sorszam);}

    public String getKep() {return kep.get();}

    public StringProperty kepProperty() {return kep;}

    public void setKep(String kep) {this.kep.set(kep);}
    //endregion
}

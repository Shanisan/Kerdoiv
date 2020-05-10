package hu.alkfejl.model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.util.Map;

public class Kitoltes {
    IntegerProperty id = new SimpleIntegerProperty();
    IntegerProperty kerdoivID = new SimpleIntegerProperty();
    StringProperty kitolto = new SimpleStringProperty();
    Map<String, String> valaszok;

    public Kitoltes(int id, int kerdoivID, String kitolto, Map<String, String> valaszok) {
        setId(id);
        setKerdoivID(kerdoivID);
        setKitolto(kitolto);
        this.valaszok = valaszok;
    }

    @Override
    public String toString() {
        return "Kitoltes{" +
                "id=" + id +
                ", kerdoivID=" + kerdoivID +
                ", kitolto=" + kitolto +
                ", valaszok=" + valaszok +
                '}';
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

    public int getKerdoivID() {
        return kerdoivID.get();
    }

    public IntegerProperty kerdoivIDProperty() {
        return kerdoivID;
    }

    public void setKerdoivID(int kerdoivID) {
        this.kerdoivID.set(kerdoivID);
    }

    public String getKitolto() {
        return kitolto.get();
    }

    public StringProperty kitoltoProperty() {
        return kitolto;
    }

    public void setKitolto(String kitolto) {
        this.kitolto.set(kitolto);
    }

    public Map<String, String> getValaszok() {
        return valaszok;
    }

    public void setValaszok(Map<String, String> valaszok) {
        this.valaszok = valaszok;
    }

    public StringProperty valaszokProperty() {
        String value = "";
        for (Map.Entry<String, String> entry:valaszok.entrySet()) {
            value+=entry.getKey()+" - "+entry.getValue()+"\n";
        }
        value.substring(0, value.length()-2);
        return new SimpleStringProperty(value);
    }
}

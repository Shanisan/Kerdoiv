package hu.alkfejl.model;

import hu.alkfejl.model.bean.Kerdes;
import hu.alkfejl.model.bean.Kerdoiv;

import java.util.List;

public interface DAO {
    boolean addKerdoiv(Kerdoiv k);
    List<Kerdes> getKerdesek(int kerdoivID);
    boolean addKerdes(Kerdes k);
    List<Kerdoiv> getKerdoiv();
}

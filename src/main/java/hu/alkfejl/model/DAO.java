package hu.alkfejl.model;

import hu.alkfejl.model.bean.Kerdoiv;

import java.util.List;

public interface DAO {
    boolean addKerdoiv(Kerdoiv k);
    List<Kerdoiv> getKerdesek();
}

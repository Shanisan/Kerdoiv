package hu.alkfejl.model;

import hu.alkfejl.model.bean.Kerdes;
import hu.alkfejl.model.bean.Kerdoiv;

import java.util.List;

public interface DAO {
    boolean addKerdoiv(Kerdoiv k);
    boolean addKerdes(Kerdes k);
    List<Kerdes> getKerdesekList(int kerdoivID);
    List<Kerdoiv> getKerdoivList(int adminID);
    Kerdes getKerdes(int kerdesID);
    Kerdoiv getKerdoiv(int kerdoivID);
    boolean deleteRow(String typeToDelete, int id);
    boolean editKerdoiv(Kerdoiv k);
}

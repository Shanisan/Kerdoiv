package hu.alkfejl.model;

import hu.alkfejl.model.bean.Kerdes;
import hu.alkfejl.model.bean.Kerdoiv;
import hu.alkfejl.model.bean.Kitoltes;
import hu.alkfejl.model.bean.Valasz;

import java.sql.SQLException;
import java.util.List;

public interface DAO {
    boolean addKerdoiv(Kerdoiv k);
    boolean addKerdes(Kerdes k);
    List<Kerdes> getKerdesekList(int kerdoivID);
    List<Kerdoiv> getKerdoivList(int adminID);
    Kerdes getKerdes(int kerdesID);
    Kerdoiv getKerdoiv(int kerdoivID);
    boolean deleteEverything(Kerdoiv delKerdoiv, List<Kerdes> delKerdes, List<Valasz> delValasz);
    boolean editKerdoiv(Kerdoiv k);

    boolean editKerdes(Kerdes k, int kerdesID);

    List<Valasz> getValaszokList(int kerdesID);

    Valasz getValasz(int valaszID);

    boolean addValasz(Valasz v);

    boolean editValasz(Valasz v);

    List<Kitoltes> getKitoltesek(int kerdoivID);
}

package hu.alkfejl.controller;

import hu.alkfejl.model.bean.Kerdes;
import hu.alkfejl.model.bean.Kerdoiv;
import hu.alkfejl.model.bean.Valasz;

import java.util.List;

public interface Controller {
    boolean addKerdoiv(Kerdoiv k);
    boolean addKerdes(Kerdes k);
    List<Kerdoiv> getKerdoivList(int adminID);
    List<Kerdes> getKerdesList(int kerdoivID);
    Kerdoiv getKerdoiv(int kerdoivID);
    Kerdes getKerdes(int kerdesID);
    Valasz getValasz(int valaszID);
    int searchUser(String username, String password);
    public List<String> getAdminList();
    public boolean addAdmin(String u, String e, String p);
    public boolean deleteRow(String typeToDelete, int id);
    boolean editKerdoiv(Kerdoiv k);

    boolean editKerdes(Kerdes k, int kerdesID, String ujKep);

    List<Valasz> getValaszList(int kerdesID);
}

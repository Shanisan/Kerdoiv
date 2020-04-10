package hu.alkfejl.controller;

import hu.alkfejl.model.bean.Kerdes;
import hu.alkfejl.model.bean.Kerdoiv;

import java.util.List;

public interface Controller {
    boolean addKerdoiv(Kerdoiv k);
    boolean addKerdes(Kerdes k);
    List<Kerdoiv> getKerdoiv();
    List<Kerdes> getKerdes(int kerdoivID);
    int searchUser(String username, String password);
    public List<String> getAdminList();
    public boolean addAdmin(String u, String e, String p);
}

package hu.alkfejl.controller;

import hu.alkfejl.model.DAO_DB;
import hu.alkfejl.model.bean.Kerdoiv;

import java.util.List;

public class ControllerImpl implements Controller {
    private DAO_DB dao = new DAO_DB();

    @Override
    public boolean addKerdoiv(Kerdoiv k) {return dao.addKerdoiv(k);}

    @Override
    public List<Kerdoiv> getKerdoiv() {return dao.getKerdesek();}
}

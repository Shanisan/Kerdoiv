package hu.alkfejl.controller;

import hu.alkfejl.model.bean.Kerdoiv;

import java.util.List;

public interface Controller {
    boolean addKerdoiv(Kerdoiv k);
    List<Kerdoiv> getKerdoiv();
}

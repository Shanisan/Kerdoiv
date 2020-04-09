package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.model.DAO_DB;
import hu.alkfejl.model.bean.Kerdes;
import hu.alkfejl.model.bean.Kerdoiv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.List;

public class ControllerImpl implements Controller {
    private DAO_DB dao = new DAO_DB();

    @Override
    public boolean addKerdoiv(Kerdoiv k) {return dao.addKerdoiv(k);}

    private static int numberOfImages = 0;
    //TODO: fájlban tároljuk hogy hány képnél járunk
    @Override
    public boolean addKerdes(Kerdes k) {
        Path source = Paths.get(k.getKep());
        DecimalFormat formater = new DecimalFormat("0000000");
        String filename = "data/img_"+formater.format(numberOfImages);
        Path destination = Paths.get(filename);
        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            k.setKep(filename);
            numberOfImages++;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        k.setKerdoivId(App.TVC.getKerdoivID());
        return dao.addKerdes(k);
    }

    @Override
    public List<Kerdoiv> getKerdoiv() {return dao.getKerdoiv();}

    @Override
    public List<Kerdes> getKerdes(int kerdoivID) {return dao.getKerdesek(kerdoivID);}
}

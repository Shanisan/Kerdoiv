package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.model.DAO_DB;
import hu.alkfejl.model.bean.Kerdes;
import hu.alkfejl.model.bean.Kerdoiv;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.List;

public class ControllerImpl implements Controller {
    private DAO_DB dao = new DAO_DB();
    private static String errorMesage;

    public static String getErrorMesage() {
        return errorMesage;
    }

    public static void setErrorMesage(String errorMesage) {
        ControllerImpl.errorMesage = errorMesage;
    }

    @Override
    public boolean addKerdoiv(Kerdoiv k) {return dao.addKerdoiv(k);}

    //region kerdes hozzaadasa
    File numPics = new File("data/pics.data");
    @Override
    public boolean addKerdes(Kerdes k) {
        if(k.getKep()!=""){
            Path source = Paths.get(k.getKep());
            DecimalFormat formater = new DecimalFormat("0000000");
            try {
                numPics.createNewFile();
                String num = Files.readString(numPics.toPath());
                int numberOfImages;
                if(num.equals("")){
                    numberOfImages=0;
                }else{
                    numberOfImages = Integer.parseInt(num);
                }
                String filename = "data/img_"+formater.format(numberOfImages)+k.getKep().substring(k.getKep().length()-4);
                Path destination = Paths.get(filename);
                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                k.setKep(filename);
                numberOfImages++;
                Files.write(numPics.toPath(), Integer.toString(numberOfImages).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        k.setKerdoivId(App.TVC.getKerdoivID());
        return dao.addKerdes(k);
    }
    //endregion

    @Override
    public List<Kerdoiv> getKerdoiv() {return dao.getKerdoiv();}

    @Override
    public List<Kerdes> getKerdes(int kerdoivID) {return dao.getKerdesek(kerdoivID);}

    @Override
    public int searchUser(String username, String password) {return DAO_DB.searchUser(username, password);}

    @Override
    public List<String> getAdminList() {return dao.getAdminList();}

    @Override
    public boolean addAdmin(String u, String e, String p) {
        return dao.addAdmin(u,e,p);
    }


}

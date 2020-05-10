package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.model.DAO_DB;
import hu.alkfejl.model.bean.Kerdes;
import hu.alkfejl.model.bean.Kerdoiv;
import hu.alkfejl.model.bean.Kitoltes;
import hu.alkfejl.model.bean.Valasz;
import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
    File numPics = new File(DAO_DB.APP_HOME+"data/pics.data");
    @Override
    public boolean addKerdes(Kerdes k) {
        //todo kep url-bol
        if(k.getKep()!=""){
            Path source = Paths.get(k.getKep());
            DecimalFormat formater = new DecimalFormat("0000000");
            try {
                numPics.getParentFile().mkdirs();
                numPics.createNewFile();
                String num = Files.readString(numPics.toPath());
                int numberOfImages;
                if(num.equals("")){
                    numberOfImages=0;
                }else{
                    numberOfImages = Integer.parseInt(num);
                }
                String filename = DAO_DB.APP_HOME+"data/img_"+formater.format(numberOfImages)+k.getKep().substring(k.getKep().length()-4);
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
    public List<Kerdoiv> getKerdoivList(int adminID) {return dao.getKerdoivList(adminID);}

    @Override
    public List<Kerdes> getKerdesList(int kerdoivID) {return dao.getKerdesekList(kerdoivID);}

    @Override
    public Kerdoiv getKerdoiv(int kerdoivID) {
        return dao.getKerdoiv(kerdoivID);
    }

    @Override
    public Kerdes getKerdes(int kerdesID) {
        return dao.getKerdes(kerdesID);
    }

    @Override
    public Valasz getValasz(int valaszID) {return dao.getValasz(valaszID);}

    @Override
    public int searchUser(String username, String password) {return DAO_DB.searchUser(username, password);}

    @Override
    public List<String> getAdminList() {return dao.getAdminList();}

    @Override
    public boolean addAdmin(String u, String e, String p) {return dao.addAdmin(u,e,p);}

    @Override
    public boolean deleteRow(String typeToDelete, int id) {
        App.lockdown();
        //gathering what to delete
        Kerdoiv delKerdoiv = null;
        List<Kerdes> delKerdesek = new ArrayList<>();
        List<Valasz> delValaszok = new ArrayList<>();
        List<File> delKepek = new ArrayList<>();
        switch (typeToDelete) {
            case "KERDOIV":
                //torolni kell kerdoivet, kerdest, valaszt, kitoltest
                delKerdoiv = getKerdoiv(id);
                delKerdesek = getKerdesList(id);
                delValaszok = new ArrayList<>();
                for (Kerdes k : delKerdesek) {
                    if (k.getTipus() == 1 || k.getTipus() == 2) {
                        delValaszok.addAll(getValaszList(k.getId()));
                    }
                    System.out.println(k.toString());
                    if (!k.getKep().equals("")) {
                        delKepek.add(new File(k.getKep()));
                    }
                }
                break;
            case "KERDES":
                //torolni kell kerdest, kepet es esetleg valaszlehetoseget
                delKerdesek.add(getKerdes(id));
                if (!delKerdesek.get(0).getKep().equals("")) {
                    delKepek.add(new File(delKerdesek.get(0).getKep()));
                }
                if (delKerdesek.get(0).getTipus() == 1 || delKerdesek.get(0).getTipus() == 2) {
                    delValaszok = getValaszList(id);
                }
                break;
            case "VALASZ":
                //csak valaszt kell torolni
                delValaszok.add(getValasz(id));
        }
        try {
            //return new Deleter(delKerdoiv, delKerdesek, delValaszok, delKepek).call();
            boolean ret = dao.deleteEverything(delKerdoiv, delKerdesek, delValaszok);
            if(ret){
                for (File f:delKepek) {
                    ret = ret && f.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            App.release();
            return false;
        }
        App.release();
        return true;
    }

    @Override
    public boolean editKerdoiv(Kerdoiv k) {
        //System.out.println("Controllerben: "+k.toString());
        return dao.editKerdoiv(k);
    }

    @Override
    public boolean editKerdes(Kerdes k, int kerdesID, String ujKep) {
        //System.out.println("Regi kep: '"+k.getKep()+"'\nUj kep: '"+ujKep+"'");
        if(!k.getKep().equals("")){
            Path source = Paths.get(ujKep);
            try {
                if(!ujKep.equals("")){
                    Path destination = Paths.get(k.getKep());
                    Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }else if(!ujKep.equals("")){
            Path source = Paths.get(ujKep);
            DecimalFormat formater = new DecimalFormat("0000000");
            try {
                numPics.getParentFile().mkdirs();
                numPics.createNewFile();
                String num = Files.readString(numPics.toPath());
                int numberOfImages;
                if(num.equals("")){
                    numberOfImages=0;
                }else{
                    numberOfImages = Integer.parseInt(num);
                }
                String filename = "data/img_"+formater.format(numberOfImages)+ujKep.substring(ujKep.length()-4);
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
        //System.out.println("Controllerben: "+k.toString());
        return dao.editKerdes(k, kerdesID);
    }

    @Override
    public List<Valasz> getValaszList(int kerdesID) {
        return dao.getValaszokList(kerdesID);
    }

    @Override
    public boolean addValasz(Valasz v) {
        return dao.addValasz(v);
    }

    @Override
    public boolean editValasz(Valasz v) {
        return dao.editValasz(v);
    }

    @Override
    public List<Kitoltes> getKitoltesek(int kerdoivID) {
        List<Kitoltes> k =dao.getKitoltesek(kerdoivID);
        for (Kitoltes ksk:k) {
            System.out.println(ksk.toString());
        }
        return k;
    }
}

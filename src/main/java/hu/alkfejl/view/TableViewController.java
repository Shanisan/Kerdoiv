package hu.alkfejl.view;

import hu.alkfejl.App;
import hu.alkfejl.controller.ControllerImpl;
import hu.alkfejl.model.bean.Kerdes;
import hu.alkfejl.model.bean.Kerdoiv;
import hu.alkfejl.model.bean.TableTypes;
import hu.alkfejl.view.dialogs.WarningShower;
import hu.alkfejl.view.tables.KerdesekTablazat;
import hu.alkfejl.view.tables.KerdoivekTablazat;
import hu.alkfejl.view.tables.ValaszokTablazat;
import javafx.scene.control.TableView;

public class TableViewController {
    private ControllerImpl controller;
    private TableTypes currentlyActiveTable = TableTypes.KERDOIV;
    KerdoivekTablazat kivtable;
    KerdesekTablazat kstable;
    ValaszokTablazat vtable;
    int kerdoivID;
    int kerdesID;

    public TableViewController(ControllerImpl controller) {
        this.controller = controller;
        setKerdoivTablazat();
    }

    public void setKerdoivTablazat(){
        kivtable=new KerdoivekTablazat(controller.getKerdoivList(App.adminID));
    }

    public TableTypes getCurrentlyActiveTable() {return currentlyActiveTable;}

    public boolean setCurrentlyActiveTable(TableTypes currentlyActiveTable) {
        this.currentlyActiveTable = currentlyActiveTable;
        try {
            switch (currentlyActiveTable) {
                case KERDOIV:
                    App.refreshTable("Kérdőívek listája");
                    break;
                case KERDES:
                    try{
                        Kerdoiv k = (Kerdoiv) kivtable.getSelectionModel().getSelectedItem();
                        kerdoivID = k.getId();
                        kstable = new KerdesekTablazat(controller.getKerdesList(kerdoivID));
                        App.refreshTable("A '"+k.getNev()+"' kérdőívhez kapcsolódó kérdések");
                        System.out.println("Set the table to show questions from questionare " + k.getId());
                        //this.currentlyActiveTable=TableSetter.KERDOIV;
                    }catch(NullPointerException npe){
                        WarningShower.showWarning("Nincs kiválasztott elem");
                        return false;
                    }
                    break;
                case VALASZ:
                    try{
                        Kerdes k = (Kerdes) kstable.getSelectionModel().getSelectedItem();
                        if(k.getTipus()==1 || k.getTipus()==2){
                            kerdesID = k.getId();
                            vtable = new ValaszokTablazat(controller.getValaszList(kerdesID));
                            App.refreshTable("A '"+k.getSzoveg()+"' kérdéshez kapcsolódó válaszlehetőségek");
                            System.out.println("Set the table to show answers from question " + k.getId());
                        }else{
                            WarningShower.showWarning("Csak a feleletválasztós kérdésekhez tartoznak válaszlehetőségek.");
                        }
                    }catch (NullPointerException npe){
                        WarningShower.showWarning("Nincs kijelölve elem!");
                        npe.printStackTrace();
                        return false;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                    break;
                case VALASZADAS:
                    break;
            }
        }catch(ClassCastException cce){
            cce.printStackTrace();
            return false;
        }
        return true;
    }

    public void refreshTable(){
        try {
            switch (currentlyActiveTable) {
                case KERDOIV:
                    kivtable.refresh(App.controller.getKerdoivList(App.adminID));
                    break;
                case KERDES:
                    kstable.refresh(App.controller.getKerdesList(kerdoivID));
                    break;
                case VALASZ:
                    vtable.refresh(App.controller.getValaszList(kerdesID));
                    break;
                case VALASZADAS:
                    break;
            }
        }catch(ClassCastException cce){
            cce.printStackTrace();
        }
    }

    public TableView getTable() {
        switch (currentlyActiveTable) {
            case KERDOIV:
                return kivtable;
            case KERDES:
                return kstable;
            case VALASZ:
                return vtable;
            case VALASZADAS:
                break;
        }
        return null;
    }

    public int getKerdoivID() {return kerdoivID;}
}

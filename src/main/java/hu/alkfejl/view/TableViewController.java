package hu.alkfejl.view;

import hu.alkfejl.App;
import hu.alkfejl.controller.ControllerImpl;
import hu.alkfejl.model.bean.Kerdoiv;
import hu.alkfejl.view.tables.KerdesekTablazat;
import hu.alkfejl.view.tables.KerdoivekTablazat;
import javafx.scene.control.TableView;

public class TableViewController {
    private ControllerImpl controller;
    private TableTypes currentlyActiveTable = TableTypes.KERDOIV;
    KerdoivekTablazat kivtable;
    KerdesekTablazat kstable;
    int kerdoivID;

    public TableViewController(ControllerImpl controller) {
        this.controller = controller;
        setKerdoivTablazat();
    }

    public void setKerdoivTablazat(){
        kivtable=new KerdoivekTablazat(controller.getKerdoiv());
    }

    public TableTypes getCurrentlyActiveTable() {return currentlyActiveTable;}

    public void setCurrentlyActiveTable(TableTypes currentlyActiveTable) {
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
                        kstable = new KerdesekTablazat(controller.getKerdes(kerdoivID));
                        App.refreshTable("A '"+k.getNev()+"' kérdőívhez kapcsolódó kérdések");
                        System.out.println("Set the table to show questions from questionare " + k.getId());
                        //this.currentlyActiveTable=TableSetter.KERDOIV;
                    }catch(NullPointerException npe){}
                    break;
                case VALASZ:
                    break;
                case VALASZADAS:
                    break;
            }
        }catch(ClassCastException cce){
            cce.printStackTrace();
        }
    }

    public void refreshTable(){
        try {
            switch (currentlyActiveTable) {
                case KERDOIV:
                    kivtable.refresh(App.controller.getKerdoiv());
                    break;
                case KERDES:
                    kstable.refresh(App.controller.getKerdes(kerdoivID));
                    break;
                case VALASZ:
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
                break;
            case VALASZADAS:
                break;
        }
        return null;
    }

    public int getKerdoivID() {return kerdoivID;}
}

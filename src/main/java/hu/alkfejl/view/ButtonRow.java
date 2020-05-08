package hu.alkfejl.view;

import hu.alkfejl.App;
import hu.alkfejl.model.bean.DatabaseObject;
import hu.alkfejl.model.bean.TableTypes;
import hu.alkfejl.view.dialogs.*;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;

import java.util.Optional;

public class ButtonRow extends HBox {
    //kozos gombok
    Button test = new Button("Test");
    Button vissza = new Button("Vissza");
    Button szerkeszt = new Button("Szerkesztés");
    Button torol = new Button("Törlés");
    //kedoives gombok
    Button addKerdoiv = new Button("Új kérdőív");
    Button kerdesek = new Button("Kérdések");
    Button kitoltesek = new Button("Kitöltések");
    //kerdeses gombok
    Button valaszok = new Button("Válaszok");
    Button addKerdes = new Button("Új kérdés");
    //valaszos gombok
    Button addValasz = new Button("Új válaszlehetőság");


    //kitolteses gombok



    public ButtonRow() {
        kerdesek.setOnAction(e->{
            if(App.TVC.setCurrentlyActiveTable(TableTypes.KERDES)){
                switchButtonRow();
                System.out.println("Kerdeseket kellene latnunk");
            }
        });
        vissza.setOnAction(e->{
            switch (App.TVC.getCurrentlyActiveTable()){
                case KERDES:
                    if(App.TVC.setCurrentlyActiveTable(TableTypes.KERDOIV)){
                        switchButtonRow();
                        System.out.println("Kerdoiveket kellene latnunk");
                    }
                    break;
                case VALASZ:
                    if(App.TVC.setCurrentlyActiveTable(TableTypes.KERDES)){
                        switchButtonRow();
                        System.out.println("Kerdeseket kellene latnunk");
                    }
                    break;
            }
        });
        addKerdoiv.setOnAction(e -> {
            new AddKerdoivDialog(App.controller);
        });
        addKerdes.setOnAction(e -> {
            new AddKerdesDialog(App.controller, App.TVC.kerdoivID);
        });
        addValasz.setOnAction(e -> {
            new AddValaszDialog(App.controller, App.TVC.kerdoivID);
        });
        torol.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Biztosan törölni szeretné a kijelölt elemet?",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.YES){
               //App.controller.deleteRow(App.TVC.getCurrentlyActiveTable().toString(), App.TVC.getTable().getSelectionModel().getSelectedIndex());
                String type = App.TVC.getCurrentlyActiveTable().toString();
                int id = -1;
                try {
                    id=((DatabaseObject)App.TVC.getTable().getSelectionModel().getSelectedItem()).getId();
                }catch (NullPointerException npe){}
                if(id!=-1){
                    App.controller.deleteRow(type, id);
                    App.TVC.refreshTable();
                }else{
                    WarningShower.showWarning("Nincs kijelölt elem!");
                }
            }
        });
        szerkeszt.setOnAction(e->{
            int id = -1;
            try {
                id=((DatabaseObject)App.TVC.getTable().getSelectionModel().getSelectedItem()).getId();
            }catch (NullPointerException npe){}
            if(id!=-1){
                switch(App.TVC.getCurrentlyActiveTable()){
                    case KERDOIV:
                        new EditKerdoivDialog(App.controller, id);
                        break;
                    case KERDES:
                        new EditKerdesDialog(App.controller, id);
                        break;
                    case VALASZ:
                        new EditValaszDialog(App.controller, id);
                        break;
                }
            }else{
                WarningShower.showWarning("Nincs kijelölt elem!");
            }
        });
        valaszok.setOnAction(e->{
            if(App.TVC.setCurrentlyActiveTable(TableTypes.VALASZ)){
                switchButtonRow();
                System.out.println("Valaszokat kellene latnunk");
            }
        });
        test.setOnAction(e->{
            //DriveConnection.downloadData();
        });
        switchButtonRow();
        this.setPadding(new Insets(5));
        this.setSpacing(5);
    }

    public void switchButtonRow(){
        switch (App.TVC.getCurrentlyActiveTable()){
            case KERDOIV:
                displayNone();
                this.getChildren().addAll(addKerdoiv, szerkeszt, torol, kerdesek, kitoltesek, test);
                break;
            case KERDES:
                displayNone();
                this.getChildren().addAll(vissza, addKerdes, szerkeszt, torol, valaszok);
                break;
            case VALASZ:
                displayNone();
                this.getChildren().addAll(vissza, addValasz, szerkeszt, torol);
                break;
            case VALASZADAS:
                break;
        }
    }

    private void displayNone(){
        this.getChildren().clear();
    }
}

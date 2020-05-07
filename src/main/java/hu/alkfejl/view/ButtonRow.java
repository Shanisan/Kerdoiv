package hu.alkfejl.view;

import hu.alkfejl.App;
import hu.alkfejl.controller.AES;
import hu.alkfejl.controller.EmailSender;
import hu.alkfejl.model.DriveConnection;
import hu.alkfejl.model.bean.DatabaseObject;
import hu.alkfejl.model.bean.Kerdoiv;
import hu.alkfejl.view.dialogs.AddKerdesDialog;
import hu.alkfejl.view.dialogs.AddKerdoivDialog;
import hu.alkfejl.view.dialogs.EditKerdoivDialog;
import hu.alkfejl.view.dialogs.WarningShower;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

public class ButtonRow extends HBox {
    //kozos gombok
    Button test = new Button("Test");
    Button vissza = new Button("Vissza a kérdőívekhez");
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
    //kitolteses gombok

    public ButtonRow() {
        kerdesek.setOnAction(e->{
            App.TVC.setCurrentlyActiveTable(TableTypes.KERDES);
            switchButtonRow();
            System.out.println("Kerdeseket kellene latnunk");
        });
        vissza.setOnAction(e->{
            App.TVC.setCurrentlyActiveTable(TableTypes.KERDOIV);
            switchButtonRow();
            System.out.println("Kerdoiveket kellene latnunk");
        });
        addKerdoiv.setOnAction(e -> {
            new AddKerdoivDialog(App.controller);
        });
        addKerdes.setOnAction(e -> {
            new AddKerdesDialog(App.controller, App.TVC.kerdoivID);
        });
        torol.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Biztosaj törölni szeretné a kijelölt elemet?",
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
                new EditKerdoivDialog(App.controller, id);
            }else{
                WarningShower.showWarning("Nincs kijelölt elem!");
            }
        });
        /*test.setOnAction(e->{
            try {
                DriveConnection.accessGoogleDrive();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (GeneralSecurityException ex) {
                ex.printStackTrace();
            }
        });*/
        switchButtonRow();
        this.setPadding(new Insets(5));
        this.setSpacing(5);
    }

    public void switchButtonRow(){
        switch (App.TVC.getCurrentlyActiveTable()){
            case KERDOIV:
                displayNone();
                this.getChildren().addAll(addKerdoiv, szerkeszt, torol, kerdesek, kitoltesek);
                break;
            case KERDES:
                displayNone();
                this.getChildren().addAll(vissza, addKerdes, szerkeszt, torol, valaszok);
                break;
            case VALASZ:
                break;
            case VALASZADAS:
                break;
        }
    }

    private void displayNone(){
        this.getChildren().clear();
    }
}

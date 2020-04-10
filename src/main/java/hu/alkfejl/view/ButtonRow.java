package hu.alkfejl.view;

import hu.alkfejl.App;
import hu.alkfejl.controller.AES;
import hu.alkfejl.controller.EmailSender;
import hu.alkfejl.view.dialogs.AddKerdesDialog;
import hu.alkfejl.view.dialogs.AddKerdoivDialog;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ButtonRow extends HBox {
    //kozos gombok
    Button test = new Button("Test");
    Button vissza = new Button("Vissza a kérdőívekhez");
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
        /*test.setOnAction(e->{
            EmailSender.sendEmail("thehellestdk@gmail.com", "Free Valorant keys 100% legit not a scam", "https://www.youtube.com/watch?v=cvh0nX08nRw");
        });*/
        switchButtonRow();
        this.setPadding(new Insets(5));
        this.setSpacing(5);
    }

    public void switchButtonRow(){
        switch (App.TVC.getCurrentlyActiveTable()){
            case KERDOIV:
                displayNone();
                this.getChildren().addAll(addKerdoiv, kerdesek, kitoltesek);
                break;
            case KERDES:
                displayNone();
                this.getChildren().addAll(vissza, addKerdes, valaszok);
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

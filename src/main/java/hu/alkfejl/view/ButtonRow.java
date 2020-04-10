package hu.alkfejl.view;

import hu.alkfejl.App;
import hu.alkfejl.view.dialogs.AddKerdesDialog;
import hu.alkfejl.view.dialogs.AddKerdoivDialog;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

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
            App.TVC.setCurrentlyActiveTable(TableSetter.KERDES);
            switchButtonRow();
            System.out.println("Kerdeseket kellene latnunk");
        });
        vissza.setOnAction(e->{
            App.TVC.setCurrentlyActiveTable(TableSetter.KERDOIV);
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
            File numPics = new File("data/pics.data");
            try {
                int numberOfImages = Integer.parseInt(Files.readString(numPics.toPath()));
                System.out.println("Number of  images as read: "+numberOfImages);
                numberOfImages++;
                System.out.println("Gonna write "+numberOfImages);
                Files.write(numPics.toPath(), Integer.toString(numberOfImages).getBytes());
                numberOfImages = Integer.parseInt(Files.readString(numPics.toPath()));
                System.out.println("Number of  images as read again: "+numberOfImages);
                numberOfImages=100;
                System.out.println("Gonna write "+numberOfImages);
                Files.write(numPics.toPath(), Integer.toString(numberOfImages).getBytes());
                numberOfImages = Integer.parseInt(Files.readString(numPics.toPath()));
                System.out.println("Number of  images as read for the third time: "+numberOfImages);
            }catch(IOException ioe){
                ioe.printStackTrace();
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

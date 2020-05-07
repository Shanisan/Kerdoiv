package hu.alkfejl.view.dialogs;

import hu.alkfejl.App;
import hu.alkfejl.controller.Controller;
import hu.alkfejl.model.bean.KerdesTipus;
import hu.alkfejl.model.bean.Kerdes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class EditKerdesDialog {

    public EditKerdesDialog(Controller controller, int kerdoivID) {createDialog(controller, kerdoivID);}

    private void createDialog(Controller c, int kerdesID) {
        Kerdes k = c.getKerdes(kerdesID);

        Stage stage = new Stage();
        GridPane pane = new GridPane();
        //region mezok
        TextField kerdesTF = new TextField(k.getSzoveg());
        kerdesTF.setPrefWidth(400);
        pane.add(new Label("Kérdés"), 0, 0);
        pane.add(kerdesTF, 1, 0);

        ObservableList<String> options= FXCollections.observableArrayList(KerdesTipus.tipusStringek);
        ComboBox tipusCB = new ComboBox(options);
        tipusCB.setValue(KerdesTipus.tipusStringek[k.getTipus()]);
        pane.add(new Label("Kérdés típusa"),0,2);
        pane.add(tipusCB,1,2);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Fájl kiválasztása");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files","*.jpg", "*.png"));
        Button button = new Button("Fájl kiválasztása");
        Label label = new Label("Nincs fájl kiválasztva");
        final File[] file = new File[1];
        button.setOnAction(e->{
                file[0] = fileChooser.showOpenDialog(stage);
                if(file[0]!=null) {
                    label.setText(file[0].getName());
                }else{
                    label.setText("Nincs fájl kiválasztva");
                }
            });
        pane.add(new Label("Kép (opcionális)"),0,3);
        pane.add(button,1,3);
        pane.add(label,2,3);

        TextField sorszamTF = new TextField(Integer.toString(k.getSorszam()));
        pane.add(new Label("Sorszám (opcionális)"), 0, 4);
        pane.add(sorszamTF, 1, 4);
//endregion
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
            stage.close();
        });
        cancel.setCancelButton(true);
        Button ok = new Button("OK");
        ok.setDefaultButton(true);
        ok.setOnAction(e -> {
            //region ellenorzesek
            if(kerdesTF.getText().isEmpty()){
                WarningShower.showWarning("Nem lehet üres a kérdés szövege!");
                return;
            }
            int sorszam=Integer.MIN_VALUE;
            if(!sorszamTF.getText().equals("")){
                try{
                    sorszam=Integer.parseInt(sorszamTF.getText());
                }catch(Exception exc){
                    WarningShower.showWarning("A sorszámnak egész számnak kell lennie");
                    return;
                }
            }
            if(sorszam<0&&sorszam!=Integer.MIN_VALUE){
                WarningShower.showWarning("A sorszámnak pozitív egész számnak kell lennie");
            return;
            }
//endregion
            k.setSzoveg(kerdesTF.getText());
            k.setSorszam(Integer.parseInt(sorszamTF.getText()));
            k.setTipus((String) tipusCB.getValue());
            k.setId(kerdesID);
            System.out.println("Dialogban: "+k.toString());
            if(App.controller.editKerdes(k, kerdesID, (file[0]==null?"":file[0].getAbsolutePath()))){
                App.TVC.refreshTable();
                stage.close();
            } else {
                WarningShower.showWarning("Hiba történt!");
                return;
            }
        });

        pane.add(cancel, 0, 5);
        pane.add(ok, 1, 5);

        Scene scene = new Scene(pane, 800, 400);
        stage.setScene(scene);

        stage.show();
    }
}
package hu.alkfejl.view.dialogs;

import hu.alkfejl.App;
import hu.alkfejl.controller.Controller;
import hu.alkfejl.model.bean.Kerdoiv;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddKerdoivDialog {

    public AddKerdoivDialog(Controller controller) {createDialog(controller);}

    private void createDialog(Controller c) {
        Stage stage = new Stage();
        GridPane pane = new GridPane();

        TextField nameTF = new TextField();

        pane.add(new Label("Név"), 0, 0);
        pane.add(nameTF, 1, 0);

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
            stage.close();
        });
        cancel.setCancelButton(true);
        Button ok = new Button("OK");
        ok.setDefaultButton(true);
        ok.setOnAction(e -> {
            if(nameTF.getText().isEmpty()){
                WarningShower.showWarning("Nem lehet üres a név!");
                return;
            }
            Kerdoiv k = new Kerdoiv(nameTF.getText());

            if(c.addKerdoiv(k)){
                App.TVC.refreshTable();
                stage.close();
            } else {
                WarningShower.showWarning("Hiba történt!");
                return;
            }
        });

        pane.add(cancel, 0, 1);
        pane.add(ok, 1, 1);

        Scene scene = new Scene(pane, 200, 200);
        stage.setScene(scene);

        stage.show();
    }
}
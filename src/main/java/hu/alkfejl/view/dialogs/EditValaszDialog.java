package hu.alkfejl.view.dialogs;

import hu.alkfejl.App;
import hu.alkfejl.controller.Controller;
import hu.alkfejl.model.bean.Valasz;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EditValaszDialog {

    public EditValaszDialog(Controller controller, int kerdoivID) {createDialog(controller, kerdoivID);}

    private void createDialog(Controller c, int valaszID) {
        Valasz v = c.getValasz(valaszID);
        Stage stage = new Stage();
        GridPane pane = new GridPane();
        //region mezok
        TextField kerdesTF = new TextField(v.getSzoveg());
        kerdesTF.setPrefWidth(400);
        pane.add(new Label("Válasz szövege"), 0, 0);
        pane.add(kerdesTF, 1, 0);

        TextField sorszamTF = new TextField(Integer.toString(v.getSorszam()));
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
            v.setSorszam(sorszam);
            v.setSzoveg(kerdesTF.getText());
            if(App.controller.editValasz(v)){
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
package hu.alkfejl.view.dialogs;

import hu.alkfejl.App;
import hu.alkfejl.controller.Controller;
import hu.alkfejl.model.bean.Kerdes;
import hu.alkfejl.model.bean.KerdesTipus;
import hu.alkfejl.model.bean.Kitoltes;
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
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVDialog {
    public CSVDialog(Controller controller, int kerdoivID) {createDialog(controller, kerdoivID);}

    private void createDialog(Controller c, int kerdoivID) {
        List<Kitoltes> k = c.getKitoltesek(kerdoivID);

        Stage stage = new Stage();
        GridPane pane = new GridPane();
        //region mezok
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Fájl kiválasztása");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File","*.csv"));
        Button button = new Button("Fájl kiválasztása");
        Label label = new Label("Nincs fájl kiválasztva");
        final File[] file = new File[1];
        button.setOnAction(e->{
            file[0] = fileChooser.showSaveDialog(stage);
            if(file[0]!=null) {
                label.setText(file[0].getName());
            }else{
                label.setText("Nincs fájl kiválasztva");
            }
        });
        pane.add(new Label("CSV helye"),0,0);
        pane.add(button,1,0);
        pane.add(label,2,0);
//endregion
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
            stage.close();
        });
        cancel.setCancelButton(true);
        Button ok = new Button("OK");
        ok.setDefaultButton(true);
        ok.setOnAction(e -> {
            try{
                createCSVFile(k, file[0]);
            } catch (IOException ex) {
                WarningShower.showWarning("Hiba törtánt a fájl írása közben.");
            }
            WarningShower.showNotification("Sikeres CSV export!");
            stage.close();
        });

        pane.add(cancel, 0, 1);
        pane.add(ok, 1, 1);
        pane.setHgap(10);
        pane.setVgap(10);
        Scene scene = new Scene(pane, 400, 100);
        stage.setScene(scene);

        stage.show();
    }
    String[] HEADERS = { "kitolto", "kerdes", "valasz"};

    public void createCSVFile(List<Kitoltes> kitoltes, File file) throws IOException {
        FileWriter out = new FileWriter(file);
        out.write('\ufeff');
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                .withHeader(HEADERS))) {
            for (Kitoltes k:kitoltes) {
                for (Map.Entry<String, String> entry:k.getValaszok().entrySet()) {
                    printer.printRecord(k.getKitolto(), entry.getKey(), entry.getValue());
                }
            }
        }
    }
}

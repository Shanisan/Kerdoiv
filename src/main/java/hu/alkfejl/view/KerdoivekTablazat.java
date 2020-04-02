package hu.alkfejl.view;

import hu.alkfejl.model.bean.Kerdoiv;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class KerdoivekTablazat extends TableView {
    public KerdoivekTablazat(List<Kerdoiv> kerdoiv) {
        TableColumn<Kerdoiv, Integer> col_id = new TableColumn("ID");
        TableColumn<Kerdoiv, String> col_nev = new TableColumn("Kérdőív neve");
        TableColumn<Kerdoiv, Integer> col_kerdesek = new TableColumn("Kérdések száma");
        col_nev.setMinWidth(500d);
        col_id.setMinWidth(50d);
        col_kerdesek.setMinWidth(150d);

        col_id.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        col_nev.setCellValueFactory(data -> data.getValue().nevProperty());
        col_kerdesek.setCellValueFactory(data -> data.getValue().kerdesekSzamaProperty().asObject());

        this.setItems(FXCollections.observableList(kerdoiv));

        this.getColumns().addAll(col_id, col_nev, col_kerdesek);
    }

    private void fillWithValues(List<Kerdoiv> k) {
        this.setItems(FXCollections.observableList(k));
    }
}

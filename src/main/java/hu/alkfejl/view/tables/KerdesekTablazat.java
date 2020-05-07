package hu.alkfejl.view.tables;

import hu.alkfejl.model.bean.KerdesTipus;
import hu.alkfejl.model.bean.Kerdes;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class KerdesekTablazat extends TableView {
    public KerdesekTablazat(List<Kerdes> kerdes) {
        TableColumn<Kerdes, Integer> col_id = new TableColumn("ID");
        TableColumn<Kerdes, String> col_kerdes = new TableColumn("Kérdés");
        TableColumn<Kerdes, String> col_tipus = new TableColumn("Típus");
        TableColumn<Kerdes, String> col_kep = new TableColumn("Kép");
        col_kerdes.setMinWidth(500d);
        col_id.setMinWidth(50d);
        col_tipus.setMinWidth(150d);
        col_kep.setMinWidth(300d);
        this.setMinHeight(645);
        col_id.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        col_kerdes.setCellValueFactory(data -> data.getValue().szovegProperty());
        col_tipus.setCellValueFactory(data -> new ReadOnlyStringWrapper(KerdesTipus.tipusStringek[data.getValue().getTipus()]));
        col_kep.setCellValueFactory(data -> data.getValue().kepProperty());

        refresh(kerdes);
        this.getColumns().addAll(col_id, col_kerdes, col_tipus, col_kep);
    }

    public void refresh(List<Kerdes> k) {
        this.setItems(FXCollections.observableList(k));
    }
}

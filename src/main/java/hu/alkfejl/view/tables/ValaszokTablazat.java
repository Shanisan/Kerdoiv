package hu.alkfejl.view.tables;

import hu.alkfejl.model.KerdesTipus;
import hu.alkfejl.model.bean.Kerdes;
import hu.alkfejl.model.bean.Valasz;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class ValaszokTablazat extends TableView {
    public ValaszokTablazat(List<Valasz> valasz) {
        TableColumn<Kerdes, Integer> col_id = new TableColumn("ID");
        TableColumn<Kerdes, String> col_szoveg = new TableColumn("Kérdés");
        col_szoveg.setMinWidth(500d);
        col_id.setMinWidth(50d);
        this.setMinHeight(645);
        col_id.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        col_szoveg.setCellValueFactory(data -> data.getValue().szovegProperty());

        refresh(valasz);
        this.getColumns().addAll(col_id, col_szoveg);
    }

    public void refresh(List<Valasz> v) {
        this.setItems(FXCollections.observableList(v));
    }
}

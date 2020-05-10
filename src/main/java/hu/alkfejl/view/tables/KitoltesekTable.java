package hu.alkfejl.view.tables;

import hu.alkfejl.model.bean.Kitoltes;
import hu.alkfejl.model.bean.Valasz;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class KitoltesekTable extends TableView {
    public KitoltesekTable(List<Kitoltes> valasz) {
        TableColumn<Kitoltes, Integer> col_id = new TableColumn("ID");
        TableColumn<Kitoltes, String> col_szoveg = new TableColumn("Kitöltő neve");
        TableColumn<Kitoltes, String> col_valaszok = new TableColumn("Kitöltő neve");
        col_szoveg.setMinWidth(500d);
        col_id.setMinWidth(50d);
        this.setMinHeight(645);
        col_id.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        col_szoveg.setCellValueFactory(data -> data.getValue().kitoltoProperty());
        col_valaszok.setCellValueFactory(data -> data.getValue().valaszokProperty());

        refresh(valasz);
        this.getColumns().addAll(col_id, col_szoveg, col_valaszok);
    }

    public void refresh(List<Kitoltes> v) {
        this.setItems(FXCollections.observableList(v));
    }
}

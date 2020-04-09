package hu.alkfejl.view;

import hu.alkfejl.model.bean.Kerdoiv;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class KerdoivekTablazat extends TableView{
    public KerdoivekTablazat(List<Kerdoiv> kerdoiv) {
        TableColumn<Kerdoiv, Integer> col_id = new TableColumn("ID");
        TableColumn<Kerdoiv, String> col_nev = new TableColumn("Kérdőív neve");
        TableColumn<Kerdoiv, Integer> col_kerdesek = new TableColumn("Kérdések száma");
        TableColumn<Kerdoiv, Integer> col_kitoltesek = new TableColumn("Kitöltések száma");
        col_nev.setMinWidth(500d);
        col_id.setMinWidth(50d);
        col_kerdesek.setMinWidth(150d);
        col_kitoltesek.setMinWidth(150d);
        this.setMinHeight(645);
        col_id.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        col_nev.setCellValueFactory(data -> data.getValue().nevProperty());
        col_kerdesek.setCellValueFactory(data -> data.getValue().kerdesekSzamaProperty().asObject());
        col_kitoltesek.setCellValueFactory(data -> data.getValue().kitoltesekProperty().asObject());

        refresh(kerdoiv);
        this.getColumns().addAll(col_id, col_nev, col_kerdesek, col_kitoltesek);
    }

    public void refresh(List<Kerdoiv> k) {
        this.setItems(FXCollections.observableList(k));
    }
}

package hu.alkfejl.view;

import hu.alkfejl.model.bean.Kerdoiv;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class KerdoivekTablazat extends TableView {
    List<Kerdoiv> kerdoivek;
    public KerdoivekTablazat(List<Kerdoiv> kerdoiv) {
        this.kerdoivek=kerdoiv;
        TableColumn col_id = new TableColumn("ID");
        TableColumn col_nev = new TableColumn("Kérdőív neve");
        TableColumn col_kerdesek = new TableColumn("Kérdések száma");
        col_nev.setMinWidth(500d);
        col_id.setMinWidth(50d);
        col_kerdesek.setMinWidth(150d);

        col_id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col_nev.setCellValueFactory(new PropertyValueFactory<>("Nev"));
        col_id.setCellValueFactory(new PropertyValueFactory<>("KerdesekSzama"));

        fillWithValues();

        this.getColumns().addAll(col_id, col_nev, col_kerdesek);
    }

    private void fillWithValues() {
        for (Kerdoiv k: kerdoivek) {
            this.getItems().add(k);
        }
    }
}

package hu.alkfejl.view.tables;

import com.google.api.client.util.DateTime;
import hu.alkfejl.model.bean.Kerdoiv;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class KerdoivekTablazat extends TableView{
    public KerdoivekTablazat(List<Kerdoiv> kerdoiv) {
        TableColumn<Kerdoiv, Integer> col_id = new TableColumn("ID");
        TableColumn<Kerdoiv, String> col_nev = new TableColumn("Kérdőív neve");
        TableColumn<Kerdoiv, Integer> col_kerdesek = new TableColumn("Kérdések száma");
        TableColumn<Kerdoiv, Integer> col_kitoltesek = new TableColumn("Kitöltések száma");
        TableColumn<Kerdoiv, String> col_kezdet = new TableColumn("Kitöltés kezdete");
        TableColumn<Kerdoiv, Timestamp> col_vege = new TableColumn("Kitöltés vége");
        TableColumn<Kerdoiv, Integer> col_ido = new TableColumn("Kitöltés ideje (perc)");
        TableColumn<Kerdoiv, String> col_link = new TableColumn("Link");
        TableColumn<Kerdoiv, String> col_letrehozta = new TableColumn("Létrehozta");
        col_nev.setMinWidth(450d);
        col_id.setMinWidth(50d);
        col_kerdesek.setMinWidth(150d);
        col_kitoltesek.setMinWidth(150d);
        this.setMinHeight(645);
        col_id.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        col_nev.setCellValueFactory(data -> data.getValue().nevProperty());
        col_kerdesek.setCellValueFactory(data -> data.getValue().kerdesekSzamaProperty().asObject());
        col_kitoltesek.setCellValueFactory(data -> data.getValue().kitoltesekProperty().asObject());
        col_kezdet.setCellValueFactory(data->{
            SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd hh:mm");
            return new ReadOnlyStringWrapper(df.format(data.getValue().getKezdet()));
        });
        col_kezdet.setCellValueFactory(data->{
            SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd hh:mm");
            return new ReadOnlyStringWrapper(df.format(data.getValue().getVege()));
        });
        col_ido.setCellValueFactory(data -> data.getValue().idoProperty().asObject());
        col_link.setCellValueFactory(data -> data.getValue().linkProperty());
        col_letrehozta.setCellValueFactory(data -> data.getValue().letrehozoProperty());
        refresh(kerdoiv);
        this.getColumns().addAll(col_id, col_nev, col_kerdesek, col_kitoltesek, col_kezdet, col_vege, col_ido, col_link, col_letrehozta);
    }

    public void refresh(List<Kerdoiv> k) {
        this.setItems(FXCollections.observableList(k));
    }
}

package hu.alkfejl;

import hu.alkfejl.controller.ControllerImpl;
import hu.alkfejl.view.AddKerdoivDialog;
import hu.alkfejl.view.KerdoivekTablazat;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

enum TableSetter{KERDOIV, KERDES, VALASZ, VALASZADAS}

public class App extends Application {
    TableView table = new KerdoivekTablazat(controller.getKerdoiv());
    Text tableText = new Text("Kérdőívek listája");
    public static ControllerImpl controller = new ControllerImpl();
    private MenuBar menubar = new MenuBar();
    private void setTable(TableSetter t, String extraInfo){
        switch (t){
            case KERDOIV:
                tableText.setText("Kérdőívek listája");
                table.setItems(FXCollections.observableList(controller.getKerdoiv()));
                break;
        }
        return;
    }

    @Override
    public void start(Stage stage) {
        //region MenuBar setup
        Menu menu = new Menu("Kérdőív");
        MenuItem kerdoivAdd = new MenuItem("Kérdőív hozzáadása");
        MenuItem kerdoivList = new MenuItem("Kérdőívek listázása");
        menubar.getMenus().add(menu);
        menu.getItems().addAll(kerdoivAdd, kerdoivList);
        //endregion
        //region MenuBar action
        kerdoivAdd.setOnAction(e -> {
            new AddKerdoivDialog(controller);
        });
        kerdoivList.setOnAction(e -> {
            setTable(TableSetter.KERDOIV, null);
        });
        //endregion
        tableText.setTextAlignment(TextAlignment.CENTER);

        Scene scene = new Scene(new VBox(menubar, tableText, table), 1280, 720);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
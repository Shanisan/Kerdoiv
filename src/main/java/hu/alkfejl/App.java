package hu.alkfejl;

import hu.alkfejl.controller.ControllerImpl;
import hu.alkfejl.view.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class App extends Application {
    public static ControllerImpl controller = new ControllerImpl();
    public static TableViewController TVC = new TableViewController(controller);
    private static ButtonRow buttons = new ButtonRow();
    private static Scene scene;
    private static VBox root;
    private static Stage stage;

    public static void refreshTable(String title) {
        root.getChildren().clear();
        root.getChildren().addAll(buttons, TVC.getTable());
        setTitle(title);
    }

    private static void setTitle(String title){
        stage.setTitle(title);
    }

    @Override
    public void start(Stage stage) {
        this.stage=stage;
        root=new VBox();
        root.getChildren().addAll(buttons, TVC.getTable());
        stage.setTitle("Kérdőívek listája");
        scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
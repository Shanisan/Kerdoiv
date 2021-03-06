package hu.alkfejl;

import hu.alkfejl.controller.AES;
import hu.alkfejl.controller.ControllerImpl;
import hu.alkfejl.view.*;
import hu.alkfejl.view.dialogs.LoginWindow;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    public static ControllerImpl controller = new ControllerImpl();
    public static TableViewController TVC;
    private static ButtonRow buttons;
    private static Scene scene;
    private static VBox root;
    private static Stage stage;
    public static int adminID;

    public static void refreshTable(String title) {
        root.getChildren().clear();
        root.getChildren().addAll(buttons, TVC.getTable());
        setTitle(title);
    }

    public static void lockdown(){
        for (Node n:root.getChildren()) {
            n.setDisable(true);
        }
    }

    public static void release(){
        for (Node n:root.getChildren()) {
            n.setDisable(false);
        }
    }

    private static void setTitle(String title){
        stage.setTitle(title);
    }

    @Override
    public void start(Stage stage) {

        this.stage=stage;

        root=new VBox();
        stage.setTitle("Kérdőívek listája");
        scene = new Scene(root, 1280, 720);
        boolean autoLogIn = false;
        if(!autoLogIn){
            LoginWindow lw = new LoginWindow(scene.getWindow());
        }else{
            adminID=1;
        }
        TVC = new TableViewController(controller);
        buttons = new ButtonRow();
        root.getChildren().addAll(buttons, TVC.getTable());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
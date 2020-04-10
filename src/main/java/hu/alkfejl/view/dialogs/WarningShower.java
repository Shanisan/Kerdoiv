package hu.alkfejl.view.dialogs;

import javafx.scene.control.Alert;

public class WarningShower {
    public static void showWarning(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.setTitle("Hiba");
        alert.showAndWait();
    }
    public static void showNotification(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}

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
}

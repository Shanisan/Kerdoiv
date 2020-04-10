package hu.alkfejl.view.dialogs;

import hu.alkfejl.App;
import hu.alkfejl.controller.AES;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginWindow extends Stage {
    private Label username = new Label("Felhasználónév");
    private Label password = new Label("Jelszó");
    private TextField usTF = new TextField();
    private TextField pwTF = new TextField();
    private Button login = new Button("Bejelentkezés");
    private Button exit = new Button("Kilépés");
    private Button forgot = new Button("Elfelejtettem a jelszavam");
    private Button register = new Button("Regisztracio");

    private GridPane gp = new GridPane();

    int adminID;

    public LoginWindow(Window w) {
        HBox parent = new HBox();
        gp.add(username, 0, 0);
        gp.add(password, 0, 1);
        gp.add(register, 0, 2);
        gp.add(forgot, 0, 3);
        gp.add(usTF, 0, 0);
        gp.add(pwTF, 1, 0);
        gp.add(login, 2, 0);
        gp.add(exit, 3, 0);
        Scene scene = new Scene(parent, 500, 500);
        this.setScene(scene);
        setOnCloseRequest(e->{System.exit(0);});
        exit.setOnAction(e->{System.exit(0);});
        login.setOnAction(e->{
            if(usTF.getText().equals("")||pwTF.getText().equals("")){
                WarningShower.showWarning("Adjon meg egy felhasználónevet és egy jelszót!");
                }
            App.adminID=App.controller.searchUser(usTF.getText(), AES.encrypt(pwTF.getText()));
            if(App.adminID!=-1){
                close();
            }else{
                WarningShower.showWarning("A felhasználónév vagy a jelszó helytelen!");
            }

        });
        parent.getChildren().addAll(gp);
        showAndWait();
    }

    public int showLoginWindow(){
        this.show();
        return adminID;
    }
}

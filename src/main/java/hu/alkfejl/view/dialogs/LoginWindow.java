package hu.alkfejl.view.dialogs;

import hu.alkfejl.App;
import hu.alkfejl.controller.AES;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginWindow extends Stage {
    private Label username = new Label("Felhasználónév");
    private Label password = new Label("Jelszó");
    private TextField usTF = new TextField();
    private PasswordField pwTF = new PasswordField();
    private Button login = new Button("Bejelentkezés");
    private Button exit = new Button("Kilépés");
    private Button forgot = new Button("Elfelejtettem a jelszavam");
    private Button register = new Button("Regisztracio");

    private GridPane gp = new GridPane();

    int adminID;

    public LoginWindow(Window w) {
        gp.add(username, 0, 0);
        gp.add(password, 0, 1);
        gp.add(register, 0, 2);
        gp.add(forgot, 0, 3);
        gp.add(usTF, 1, 0);
        gp.add(pwTF, 1, 1);
        gp.add(login, 1, 2);
        gp.add(exit, 1, 3);
        Scene scene = new Scene(gp, 350, 150);
        gp.setAlignment(Pos.CENTER);
        this.setScene(scene);
        setOnCloseRequest(e->{System.exit(0);});
        exit.setOnAction(e->{System.exit(0);});
        usTF.requestFocus();
        login.setOnAction(e->{
            if(usTF.getText().equals("")||pwTF.getText().equals("")){
                WarningShower.showWarning("Adjon meg egy felhasználónevet és egy jelszót!");
                return;
                }
            App.adminID=App.controller.searchUser(usTF.getText(), AES.encrypt(pwTF.getText()));
            if(App.adminID!=-1){
                close();
            }else{
                WarningShower.showWarning("A felhasználónév vagy a jelszó helytelen!");
            }

        });
        register.setOnAction(e->{
            new RegisterWindow();
        });
        login.setDefaultButton(true);
        showAndWait();
    }

    public int showLoginWindow(){
        this.show();
        return adminID;
    }
}

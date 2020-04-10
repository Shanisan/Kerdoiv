package hu.alkfejl.view.dialogs;

import hu.alkfejl.App;
import hu.alkfejl.controller.AES;
import hu.alkfejl.controller.EmailSender;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.List;

public class RegisterWindow extends Stage {
    private Label username = new Label("Felhasználónév");
    private Label password = new Label("Jelszó");
    private Label password2 = new Label("Jelszó ismét");
    private Label email = new Label("E-mail cím");
    private TextField usTF = new TextField();
    private PasswordField pwTF = new PasswordField();
    private PasswordField pw2TF = new PasswordField();
    private TextField emTF = new TextField();
    private Button back = new Button("Vissza");
    private Button register = new Button("Regisztracio");

    private GridPane gp = new GridPane();

    public RegisterWindow(){
        gp.add(username, 0, 0);
        gp.add(password, 0, 1);
        gp.add(password2, 0, 2);
        gp.add(email, 0, 3);
        gp.add(back, 0, 4);
        gp.add(usTF, 1, 0);
        gp.add(pwTF, 1, 1);
        gp.add(pw2TF, 1, 2);
        gp.add(emTF, 1, 3);
        gp.add(register, 1, 4);
        back.setOnAction(e->{close();});
        usTF.requestFocus();
        register.setOnAction(e->{
            //region ellenorzesek
            if(usTF.getText().equals("")||pwTF.getText().equals("")||pw2TF.getText().equals("")||emTF.getText().equals("")){
                WarningShower.showWarning("Minden mező kitöltése kötelező!");
                return;
            }
            if(!emTF.getText().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")){
                WarningShower.showWarning("Nem megfelelő e-mail cím formátum");
                return;
            }
            if(!pw2TF.getText().matches(pwTF.getText())){
                WarningShower.showWarning("A két jelszó nem egyezik!");
                return;
            }
            List<String> admins = App.controller.getAdminList();
            if(admins.contains(usTF.getText())){
                WarningShower.showWarning("A felhasználónév már foglalt");
                return;
            }
            //emdregion
            if(App.controller.addAdmin(usTF.getText(), emTF.getText(), AES.encrypt(pw2TF.getText()))){
                register.disarm();
                back.disarm();
                String msg = "Gratulálunk, sikeresen regisztrált a kérdőívek adminjaként!\n" +
                        "Felhasználónév: "+usTF.getText()+"\n" +
                        "Jelszó: "+pw2TF.getText();
                EmailSender.sendEmail(emTF.getText(), "Sikeres regisztráció", msg);
                WarningShower.showNotification("Regisztráció sikeres");
                close();
            }else{
                WarningShower.showWarning("Hiba történt");
            }
        });

        Scene scene = new Scene(gp, 350, 200);
        setScene(scene);
        showAndWait();
    }
}

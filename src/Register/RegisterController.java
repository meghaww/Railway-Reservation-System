package Register;

import Login.DatabaseConnection;
import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterController {
    @FXML
    private Button close;
    @FXML
    private Label RegistrationLabel;
    @FXML
    private TextField FnameTF;
    @FXML
    private TextField LnameTF;
    @FXML
    private TextField Uname;
    @FXML
    private TextField PasswordTF;
    @FXML
    private PasswordField PasswordPF;
    @FXML
    private Label PasswordLabel;
    public void closeOnAction(ActionEvent event){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
    public void registerButtonOnAction(ActionEvent event){
        if(PasswordTF.getText().equals(PasswordPF.getText())){

            PasswordLabel.setText("Passwords Match!");
            registerUser();
        }else{
            RegistrationLabel.setText("");
            PasswordLabel.setText("Passwords Don't Match.");
        }
    }
    public void registerUser(){
        DatabaseConnection RegisterConnection = new DatabaseConnection();
        Connection RegisterConnectdb = RegisterConnection.getConnection();

        String firstname = FnameTF.getText();
        String lastname = LnameTF.getText();
        String username = Uname.getText();
        String password = PasswordTF.getText();

        String insertFields = "INSERT INTO user_accounts(firstname, lastname, username, password) VALUES ('";
        String insertValues = firstname + "','" + lastname + "','" + username + "','" + password + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = RegisterConnectdb.createStatement();
            statement.executeUpdate(insertToRegister);
            RegistrationLabel.setText("User Registered Successfully!");

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

}

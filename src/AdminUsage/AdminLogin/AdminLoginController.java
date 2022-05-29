package AdminUsage.AdminLogin;

import Login.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminLoginController {
    @FXML
    private TextField AdminUser;
    @FXML
    private PasswordField AdminPass;
    @FXML
    private Button close;
    @FXML
    private Button AdminLogin;
    @FXML
    private Label MsgLabel;
//closing the stage
    public void closeOnAction(ActionEvent event){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
//login button
    public void LoginonAction(ActionEvent event){
        if(!AdminUser.getText().isBlank() && !AdminPass.getText().isBlank()){
            MsgLabel.setText("Access Granted");
            validAdminLogin();
        } else{
            MsgLabel.setText("Please Enter Username and Password");
        }

    }
//database logic and opening the admin controls stage
    public void validAdminLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM admin_accounts WHERE username = '" + AdminUser.getText() + "' AND password ='" + AdminPass.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    AdminControls();
                } else {
                    MsgLabel.setText("Invalid Credentials! Try Again");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
//admin controls stage
        public void AdminControls(){
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/AdminUsage/AdminControls/AdminControls.fxml"));
                Stage registerStage = new Stage();
                registerStage.initStyle(StageStyle.UNDECORATED);
                registerStage.setScene(new Scene(root));
                registerStage.show();
            }catch(Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }
}

package Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {

    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;
    @FXML
    private Button close;
    @FXML
    private Button login;
    @FXML
    private Label MsgLabel;

    public void closeOnAction(ActionEvent event){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
    public void LoginonAction(ActionEvent event){
        if(user.getText().isBlank() == false && pass.getText().isBlank() == false){
            validLogin();

        } else{
            MsgLabel.setText("Please Enter Username and Password");
        }

    }
    public void validLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM user_accounts WHERE username = '" + user.getText() + "' AND password ='" + pass.getText() +  "'";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    RailwayReservation();
                }else{
                    MsgLabel.setText("Invalid Credentials! Try Again");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void RailwayReservation(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Reservation/Railway.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void RegisterForm(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../Register/Register.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void AdminFeature(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../AdminUsage/AdminLogin/AdminLogin.fxml"));
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

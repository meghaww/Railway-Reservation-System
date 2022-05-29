package AdminUsage.AdminControls;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminControlsController {
    @FXML
    private Button close;
    public void closeOnAction(ActionEvent event){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
    public void UserAdminOnAction(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/AdminUsage/AlterUserDet/AlterUserDet.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void TrainAdminOnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AdminUsage/TrainEdit/TrainEdit.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    public void BookedTicketAdminOnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AdminUsage/BookedTickets/BookedTickets.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}


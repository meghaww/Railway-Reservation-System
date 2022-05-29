package AdminUsage.TrainEdit;


import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import Login.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import Models.TrainEditModel;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddEditTrainsController implements Initializable{

    @FXML
    private TextField PNRTF;
    @FXML
    private TextField SourceTF;
    @FXML
    private TextField DestinationTF;
    @FXML
    private TextField TrainNameTF;
    @FXML
    private TextField DepartureTimeTF;
    @FXML
    private TextField SeatsAvailableTF;
    @FXML
    private Button close;
    @FXML
    private Label AlertLabel;

    private boolean update;
    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();
    String query = null;
    String PNR_ID;
    PreparedStatement preparedStatement;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void closeOnAction(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void save() {

        Connection connectDB = connectNow.getConnection();
        String PNR = PNRTF.getText();
        String source = SourceTF.getText();
        String destination = DestinationTF.getText();
        String train_name = TrainNameTF.getText();
        Time departure_time = Time.valueOf(DepartureTimeTF.getText());
        String seats_available = SeatsAvailableTF.getText();

        if (PNR.isEmpty() || source.isEmpty() || destination.isEmpty() || train_name.isEmpty() || departure_time.toLocalTime().equals(false) || seats_available.isEmpty()) {
            AlertLabel.setText("");
            AlertLabel.setText("Error: Text Fields cannot remain empty");
        } else {
            AlertLabel.setText("Train Added to Database successfully");
            getQuery();
            clean();
        }
    }
    @FXML
    private void clean() {
        PNRTF.setText(null);
        SourceTF.setText(null);
        DestinationTF.setText(null);
        TrainNameTF.setText(null);
        DepartureTimeTF.setText(null);
        SeatsAvailableTF.setText(null);
    }

    private void getQuery() {
        if (!update) {
            AddTrain();
        }else{
        }
    }

    public void AddTrain(){
        DatabaseConnection RegisterConnection = new DatabaseConnection();
        Connection RegisterConnectdb = RegisterConnection.getConnection();

        String PNR = PNRTF.getText();
        String source = SourceTF.getText();
        String destination = DestinationTF.getText();
        String train_name = TrainNameTF.getText();
        Time departure_time = Time.valueOf(DepartureTimeTF.getText());
        String seats_available = SeatsAvailableTF.getText();
        String insertFields = "INSERT INTO train_details(pnr_id, source, destination, train_name, departure_time, seats_available) VALUES ('";
        String insertValues = PNR + "','" + source + "','" + destination + "','" + train_name + "','" + departure_time + "','" + seats_available + "')";
        String insertToTrain = insertFields + insertValues;
        try {
            Statement statement = RegisterConnectdb.createStatement();
            statement.executeUpdate(insertToTrain);
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void setUpdate(boolean b) {
        this.update = b;
    }
}

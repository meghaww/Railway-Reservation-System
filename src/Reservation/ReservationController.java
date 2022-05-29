package Reservation;

import Login.DatabaseConnection;
import Models.ReservationModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationController implements Initializable{
    @FXML
    private Button close;
    @FXML
    private TableView<ReservationModel> TrainTableView;
    @FXML
    private TableColumn<ReservationModel, Integer> SerialNoColumn;
    @FXML
    private TableColumn<ReservationModel, String> PNRColumn;
    @FXML
    private TableColumn<ReservationModel, String> SourceColumn;
    @FXML
    private TableColumn<ReservationModel, String> DestinationColumn;
    @FXML
    private TableColumn<ReservationModel, String> TrainNameColumn;
    @FXML
    private TableColumn<ReservationModel, Time> DepartureTimeColumn;
    @FXML
    private TableColumn<ReservationModel, Integer> SeatsColumn;
    @FXML
    private TextField SearchTF;
    @FXML
    private TextField PNRConfirm;
    @FXML
    private TextField PassengerConfirm;
    @FXML
    private TextField FNameConfirm;
    @FXML
    private TextField LNameConfirm;
    @FXML
    private PasswordField PasswordConfirm;
    @FXML
    private Label AlertLabel;

    ObservableList<ReservationModel> ReservationModelObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resource){
        refreshtable();
    }
    private void refreshtable(){
        ReservationModelObservableList.clear();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        //main sql query for the database
        String TrainViewQuery = "SELECT Sno, pnr_id, source, destination, train_name, departure_time, seats_available FROM train_details";
        try{
            Statement resstatement = connectDB.createStatement();
            ResultSet TrainQueryResult = resstatement.executeQuery(TrainViewQuery);

            while(TrainQueryResult.next()){
                Integer SNO = TrainQueryResult.getInt("Sno");
                String PNR = TrainQueryResult.getString("pnr_id");
                String Source = TrainQueryResult.getString("source");
                String Destination = TrainQueryResult.getString("destination");
                String TrainName = TrainQueryResult.getString("train_name");
                Time DepartureTime = TrainQueryResult.getTime("departure_time");
                Integer Seats = TrainQueryResult.getInt("seats_available");
                //populates Observable list
                ReservationModelObservableList.add(new ReservationModel(SNO, PNR, Source, Destination, TrainName, DepartureTime,Seats));
            }
            SerialNoColumn.setCellValueFactory(new PropertyValueFactory<>("Sno"));
            PNRColumn.setCellValueFactory(new PropertyValueFactory<>("pnr_id"));
            SourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));
            DestinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
            TrainNameColumn.setCellValueFactory(new PropertyValueFactory<>("train_name"));
            DepartureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departure_time"));
            SeatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats_available"));
            TrainTableView.setItems(ReservationModelObservableList);

            FilteredList<ReservationModel> filteredData = new FilteredList<>(ReservationModelObservableList, b -> true);

            SearchTF.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(ReservationModel -> {

                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if(ReservationModel.getTrain_name().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    }else if(ReservationModel.getPnr_id().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    }else if(ReservationModel.getSource().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    }else if(ReservationModel.getDestination().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    }else
                        return false;
                });
            });

            SortedList<ReservationModel> sortedData = new SortedList<>(filteredData);
            //Bind sorted result to table view
            sortedData.comparatorProperty().bind(TrainTableView.comparatorProperty());
            // Apply filtered and sorted data to the Table view
            TrainTableView.setItems(sortedData);

        }catch (SQLException e){
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
    public void closeOnAction(ActionEvent event){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    public void reservationButtonOnAction(ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String password = PasswordConfirm.getText();
        String PNR_booked = PNRConfirm.getText();
        String firstname = FNameConfirm.getText();
        String lastname = LNameConfirm.getText();
        try {
            Statement statement1= connectDB.createStatement();
            Statement statement2= connectDB.createStatement();
            ResultSet UAccQuery = statement1.executeQuery("SELECT count(1) FROM user_accounts WHERE firstname = '" + firstname + "' AND lastname = '" + lastname + "' AND password = '" + password + "'");
            ResultSet TDetQuery = statement2.executeQuery("SELECT count(1) FROM train_details WHERE pnr_id = '" + PNR_booked + "'");
            while(UAccQuery.next()) {
                while (TDetQuery.next()) {
                    if (UAccQuery.getInt(1) == 1 && TDetQuery.getInt(1) == 1) {
                        AlertLabel.setText("Credentials match Database!");
                        bookingTablefill();
                    } else {
                        AlertLabel.setText("Credentials do not match Database! Try Again");
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void bookingTablefill(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
//        RANDOM BOOKING ID GENERATION
            // chose a Character random from this String
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz";

            // create StringBuffer size of AlphaNumericString
            StringBuilder sb = new StringBuilder(5);

            for (int i = 0; i < 5; i++) {

                // generate a random number between
                // 0 to AlphaNumericString variable length
                int index = (int) (AlphaNumericString.length() * Math.random());

                // add Character one by one in end of sb
                sb.append(AlphaNumericString.charAt(index));
            }

        String booking_id = sb.toString();
        String PNR_booked = PNRConfirm.getText();
        String no_of_tickets  = PassengerConfirm.getText();
        String first_name = FNameConfirm.getText();
        String last_name = LNameConfirm.getText();
        String booking_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());
        try{
            Statement statementSelect = connectDB.createStatement();
            Statement statementUpdate = connectDB.createStatement();
            ResultSet usernameSet = statementSelect.executeQuery("SELECT username FROM user_accounts WHERE firstname = '" + first_name + "' AND lastname = '" + last_name + "'");

            while(usernameSet.next()){
                String username = usernameSet.getString("username");
                String insertBooking = "INSERT INTO booked_tickets(booking_id,PNR_booked,first_name,last_name,username,no_of_tickets,booking_time) VALUES('";
                String insertValues = booking_id + "','" + PNR_booked + "','" + first_name + "','" + last_name + "','" +  username + "','" + no_of_tickets + "','" + booking_time + "')";
                String insertToBook = insertBooking + insertValues;
                statementUpdate.executeUpdate(insertToBook);
            }
            BillingConfirmation();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void BillingConfirmation(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Billing/BillingConfirmation.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void AdminFeature(ActionEvent event){
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
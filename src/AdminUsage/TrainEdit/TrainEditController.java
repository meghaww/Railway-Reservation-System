package AdminUsage.TrainEdit;

import Login.DatabaseConnection;
import Models.TrainEditModel;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrainEditController implements Initializable{
    @FXML
    private Button close;
    @FXML
    private TextField SearchTF;
    @FXML
    private TableView<TrainEditModel> TrainTableView;
    @FXML
    private TableColumn<TrainEditModel, Integer> SerialNoColumn;
    @FXML
    private TableColumn<TrainEditModel, String> PNRColumn;
    @FXML
    private TableColumn<TrainEditModel, String> SourceColumn;
    @FXML
    private TableColumn<TrainEditModel, String> DestinationColumn;
    @FXML
    private TableColumn<TrainEditModel, String> TrainNameColumn;
    @FXML
    private TableColumn<TrainEditModel, Time> DepartureTimeColumn;
    @FXML
    private TableColumn<TrainEditModel, Integer> SeatsColumn;
    @FXML
    private TableColumn<TrainEditModel, String> ActionsColumn;
    ObservableList<TrainEditModel> TrainEditModelObservableList = FXCollections.observableArrayList();

    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadData();
    }
    private void LoadData(){
        refreshTable();
        SerialNoColumn.setCellValueFactory(new PropertyValueFactory<>("Sno"));
        PNRColumn.setCellValueFactory(new PropertyValueFactory<>("pnr_id"));
        SourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        DestinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        TrainNameColumn.setCellValueFactory(new PropertyValueFactory<>("train_name"));
        DepartureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departure_time"));
        SeatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats_available"));
        TrainTableView.setItems(TrainEditModelObservableList);

        Callback<TableColumn<TrainEditModel, String>, TableCell<TrainEditModel, String>> cellFactory = (TableColumn<TrainEditModel, String> param) -> {
            // make cell containing buttons
            final TableCell<TrainEditModel, String> cell = new TableCell<TrainEditModel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    }else{
                        ImageView DeleteIcon = new ImageView("@../../res/delete.png" );
                        DeleteIcon.setFitHeight(35);
                        DeleteIcon.setFitWidth(35);
                        DeleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-fx-scale-x: 1;"
                                        + "-fx-scale-y: 1;"
                        );
                        DeleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            try {
                                TrainEditModel TrainEditModelObject = TrainTableView.getSelectionModel().getSelectedItem();
                                String TrainDeleteQuery = "DELETE FROM train_details WHERE (pnr_id  = '" + TrainEditModelObject.getPnr_id() + "');";
                                Statement TrainDelStatement = connectDB.createStatement();
                                TrainDelStatement.execute(TrainDeleteQuery);
                                refreshTable();
                            } catch (SQLException ex) {
                                Logger.getLogger(TrainEditController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        HBox managebtn = new HBox(DeleteIcon);
                        managebtn.setStyle("-fx-alignment:Center");
                        HBox.setMargin(DeleteIcon, new javafx.geometry.Insets(2, 2, 0, 2));
                        setGraphic(managebtn);
                        setText(null);
                        }
                    }
                };
            return cell;
            };
            ActionsColumn.setCellFactory(cellFactory);
            TrainTableView.setItems(TrainEditModelObservableList);
        }
    @FXML
    private void refreshTable(){
        TrainEditModelObservableList.clear();

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
                TrainEditModelObservableList.add(new TrainEditModel(SNO, PNR, Source, Destination, TrainName, DepartureTime,Seats));
            }
            FilteredList<TrainEditModel> filteredData = new FilteredList<>(TrainEditModelObservableList, b -> true);

            SearchTF.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(TrainEditModel -> {

                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if(TrainEditModel.getTrain_name().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }else if(TrainEditModel.getPnr_id().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }else if(TrainEditModel.getSource().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }else if(TrainEditModel.getDestination().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }else
                        return false;
                });
            });

            SortedList<TrainEditModel> sortedData = new SortedList<>(filteredData);
            //Bind sorted result to table view
            sortedData.comparatorProperty().bind(TrainTableView.comparatorProperty());
            // Apply filtered and sorted data to the Table view
            TrainTableView.setItems(sortedData);
        }catch (SQLException e){
            Logger.getLogger(TrainEditController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }
    public void closeOnAction(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void AddEdit(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../TrainEdit/AddEdit.fxml"));
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

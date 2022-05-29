package AdminUsage.AlterUserDet;

import Login.DatabaseConnection;
import Models.AlterUserDetModel;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlterUserDetController implements Initializable {
    @FXML
    private Button close;
    @FXML
    private TextField SearchTF;
    @FXML
    private TableView<AlterUserDetModel> AlterUserView;
    @FXML
    private TableColumn<AlterUserDetModel, Integer> AccountIDColumn;
    @FXML
    private TableColumn<AlterUserDetModel, String> FnameColumn;
    @FXML
    private TableColumn<AlterUserDetModel, String> LnameColumn;
    @FXML
    private TableColumn<AlterUserDetModel, String> UserColumn;
    @FXML
    private TableColumn<AlterUserDetModel, String> ActionsColumn;


    ObservableList<AlterUserDetModel> AlterUserDetModelObservableList = FXCollections.observableArrayList();

    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadData();
    }
    private void LoadData(){
        refreshTable();
        AccountIDColumn.setCellValueFactory(new PropertyValueFactory<>("account_id"));
        FnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        LnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        UserColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        AlterUserView.setItems(AlterUserDetModelObservableList);

        Callback<TableColumn<AlterUserDetModel, String>, TableCell<AlterUserDetModel, String>> cellFactory = (TableColumn<AlterUserDetModel, String> param) -> {
            // make cell containing buttons
            final TableCell<AlterUserDetModel, String> cell = new TableCell<AlterUserDetModel, String>() {
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
                                AlterUserDetModel AlterUserDetModelObject = AlterUserView.getSelectionModel().getSelectedItem();
                                String UserDeleteQuery = "DELETE FROM user_accounts WHERE (account_id  = '" + AlterUserDetModelObject.getAccount_id() + "');";
                                Statement UserDelStatement = connectDB.createStatement();
                                UserDelStatement.execute(UserDeleteQuery);
                                refreshTable();
                            } catch (SQLException ex) {
                                Logger.getLogger(AlterUserDetController.class.getName()).log(Level.SEVERE, null, ex);
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
        AlterUserView.setItems(AlterUserDetModelObservableList);
    }
    @FXML
    private void refreshTable(){
        AlterUserDetModelObservableList.clear();

        String AlterUserViewQuery = "SELECT account_id, firstname, lastname, username FROM user_accounts";
        try{
            Statement resstatement = connectDB.createStatement();
            ResultSet TrainQueryResult = resstatement.executeQuery(AlterUserViewQuery);

            while(TrainQueryResult.next()){
                Integer Account_id = TrainQueryResult.getInt("account_id");
                String FirstName = TrainQueryResult.getString("firstname");
                String LastName = TrainQueryResult.getString("lastname");
                String Username = TrainQueryResult.getString("username");

                //populates Observable list
                AlterUserDetModelObservableList.add(new AlterUserDetModel(Account_id, FirstName, LastName, Username));
            }
            FilteredList<AlterUserDetModel> filteredData = new FilteredList<>(AlterUserDetModelObservableList, b -> true);

            SearchTF.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(AlterUserDetModel -> {

                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if(AlterUserDetModel.getAccount_id().equals(searchKeyword)) {
                        return true;
                    }else if(AlterUserDetModel.getFirstname().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }else if(AlterUserDetModel.getLastname().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }else if(AlterUserDetModel.getUsername().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }else
                        return false;
                });
            });

            SortedList<AlterUserDetModel> sortedData = new SortedList<>(filteredData);
            //Bind sorted result to table view
            sortedData.comparatorProperty().bind(AlterUserView.comparatorProperty());
            // Apply filtered and sorted data to the Table view
            AlterUserView.setItems(sortedData);
        }catch (SQLException e){
            Logger.getLogger(AlterUserDetController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }
    public void closeOnAction(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
}


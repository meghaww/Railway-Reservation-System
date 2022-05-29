package AdminUsage.BookedTickets;


import Login.DatabaseConnection;
import Models.BookedTicketsModel;
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

import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookedTicketsController implements Initializable {
    @FXML
    private Button close;
    @FXML
    private TextField SearchTF;
    @FXML
    private TableView<BookedTicketsModel> TicketsTableView;
    @FXML
    private TableColumn<BookedTicketsModel, String> BookingIDColumn;
    @FXML
    private TableColumn<BookedTicketsModel, String> PNRColumn;
    @FXML
    private TableColumn<BookedTicketsModel, String> FnameColumn;
    @FXML
    private TableColumn<BookedTicketsModel, String> LnameColumn;
    @FXML
    private TableColumn<BookedTicketsModel, String> UserColumn;
    @FXML
    private TableColumn<BookedTicketsModel, Integer> NoOfTicketsColumn;
    @FXML
    public TableColumn<BookedTicketsModel, LocalDateTime> BookingTimeColumn;
    @FXML
    private TableColumn<BookedTicketsModel, String> ActionsColumn;

    ObservableList<BookedTicketsModel> bookedTicketsModelObservableList = FXCollections.observableArrayList();

    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadData();
    }
    private void LoadData(){
        refreshTable();
        BookingIDColumn.setCellValueFactory(new PropertyValueFactory<>("booking_id"));
        PNRColumn.setCellValueFactory(new PropertyValueFactory<>("PNR_booked"));
        FnameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        LnameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        UserColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        NoOfTicketsColumn.setCellValueFactory(new PropertyValueFactory<>("no_of_tickets"));
        BookingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("booking_time"));
        TicketsTableView.setItems(bookedTicketsModelObservableList);

        Callback<TableColumn<BookedTicketsModel, String>, TableCell<BookedTicketsModel, String>> cellFactory = (TableColumn<BookedTicketsModel, String> param) -> {
            // make cell containing buttons
            final TableCell<BookedTicketsModel, String> cell = new TableCell<BookedTicketsModel, String>() {
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
                                BookedTicketsModel bookedTicketsModel = TicketsTableView.getSelectionModel().getSelectedItem();
                                String TicketDeleteQuery = "DELETE FROM booked_tickets WHERE (booking_id = '" + bookedTicketsModel.getBooking_id() + "');";
                                Statement TicketDelStatement = connectDB.createStatement();
                                TicketDelStatement.execute(TicketDeleteQuery);
                                refreshTable();
                            } catch (SQLException ex) {
                                Logger.getLogger(BookedTicketsController.class.getName()).log(Level.SEVERE, null, ex);
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
        TicketsTableView.setItems(bookedTicketsModelObservableList);
    }
    @FXML
    private void refreshTable(){
        bookedTicketsModelObservableList.clear();

        String TicketViewQuery = "SELECT booking_id, PNR_booked, first_name, last_name, username, no_of_tickets, booking_time FROM booked_tickets";
        try{
            Statement resstatement = connectDB.createStatement();
            ResultSet TicketQueryResult = resstatement.executeQuery(TicketViewQuery);

            while(TicketQueryResult.next()){
                String BookingID = TicketQueryResult.getString("booking_id");
                String PNRBooked = TicketQueryResult.getString("PNR_booked");
                String first_name = TicketQueryResult.getString("first_name");
                String last_name = TicketQueryResult.getString("last_name");
                String Username = TicketQueryResult.getString("username");
                Integer noOfTickets = TicketQueryResult.getInt("no_of_tickets");
                Time BookingTime = TicketQueryResult.getTime("booking_time");
                //populates Observable list
                bookedTicketsModelObservableList.add(new BookedTicketsModel(BookingID, PNRBooked, first_name, last_name, Username, noOfTickets, BookingTime));
            }
            FilteredList<BookedTicketsModel> filteredData = new FilteredList<>(bookedTicketsModelObservableList, b -> true);

            SearchTF.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(BookedTicketsModel -> {

                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if(BookedTicketsModel.getBooking_id().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }else if(BookedTicketsModel.getPNR_booked().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }else if(BookedTicketsModel.getFirst_name().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }else if(BookedTicketsModel.getLast_name().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }else if(BookedTicketsModel.getUsername().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }else
                        return false;
                });
            });

            SortedList<BookedTicketsModel> sortedData = new SortedList<>(filteredData);
            //Bind sorted result to table view
            sortedData.comparatorProperty().bind(TicketsTableView.comparatorProperty());
            // Apply filtered and sorted data to the Table view
            TicketsTableView.setItems(sortedData);
        }catch (SQLException e){
            Logger.getLogger(BookedTicketsController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }
    public void closeOnAction(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
}

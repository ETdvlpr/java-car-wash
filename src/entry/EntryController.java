/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entry;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer.MarginType;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.run;

/**
 * FXML Controller class
 *
 * @author Dave
 */
public class EntryController implements Initializable {

    @FXML
    private Label lbl_price;
    @FXML
    private TextField txt_plate, txt_phone, txt_start, txt_end, txt_remark;
    @FXML
    private ComboBox<employee> select_employee;
    @FXML
    private ComboBox<String> select_car_type, select_car, select_wash_type;
    @FXML
    private CheckBox check_paid, check_office;
    @FXML
    private Button btn_save, btn_cancel, btn_print;
    @FXML
    private TableView<Activity> tbl_activity;
    @FXML
    private TableColumn<Activity, String> col_index, col_name, col_plate, col_start;

    private ArrayList<Integer> employee_ids;
    private float price;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            txt_start.setText(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
            txt_end.setText(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
            col_index.setCellValueFactory(
                    new PropertyValueFactory<Activity, String>("Index")
            );
            col_name.setCellValueFactory(
                    new PropertyValueFactory<Activity, String>("Name")
            );
            col_plate.setCellValueFactory(
                    new PropertyValueFactory<Activity, String>("Plate")
            );
            col_start.setCellValueFactory(
                    new PropertyValueFactory<Activity, String>("Start")
            );
            select_wash_type.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (select_wash_type.getValue().equals("FULL")) {
                        price = 135.0f;
                    } else {
                        price = 95.0f;
                    }
                    lbl_price.setText("Price : " + price);
                }
            });
            check_paid.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (check_paid.isSelected()) {
                        check_office.setSelected(false);
                    }
                }
            });
            check_office.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (check_office.isSelected()) {
                        check_paid.setSelected(false);
                    }
                }
            });
            btn_print.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    PrinterJob job = PrinterJob.createPrinterJob();
                    if(job.showPrintDialog(run.primaryStage)){
                        try {
                            job.getJobSettings().setPageLayout(job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, MarginType.HARDWARE_MINIMUM));
                            job.getJobSettings().setJobName("Daily Activity");
                            job.printPage(create_print_table());
                            job.endJob();
                        } catch (SQLException ex) {
                            Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            Connection conn = DriverManager.getConnection("jdbc:sqlite:ETCarWash.db");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID, name FROM employee;");
            
            ObservableList<employee> data = FXCollections.observableArrayList();
            while (rs.next()) {
                data.add(new employee(rs.getInt("ID"), rs.getString("name")));
            }
            select_employee.setItems(data);
            ObservableList<String> type_data = FXCollections.observableArrayList();
            type_data.add("FULL");
            type_data.add("HALF");
            select_wash_type.setItems(type_data);
            type_data = FXCollections.observableArrayList();
            type_data.add("Large");
            type_data.add("Middle");
            type_data.add("Small");
            select_car_type.setItems(type_data);
            update_table();
        } catch (SQLException ex) {
            Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleButtonSave(ActionEvent event) {
        String day = new SimpleDateFormat("yyyy-MM-dd ").format(Calendar.getInstance().getTime());
        String query = "INSERT INTO wash ( employee, Plate_No, Customer_phone, Car_Type, Car, Wash_Type, Price, Status, Remark, Start, End) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:ETCarWash.db");
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, select_employee.getValue().getID());
            ps.setString(2, txt_plate.getText().equals("") ? null : txt_plate.getText());
            ps.setString(3, txt_phone.getText().equals("") ? null : txt_phone.getText());
            ps.setString(4, select_car_type.getValue());
            ps.setString(5, select_car.getValue());
            ps.setString(6, select_wash_type.getValue());
            ps.setFloat(7, price);
            ps.setString(8, check_paid.isSelected() ? "PAID" : "OFFICE");
            ps.setString(9, txt_remark.getText().equals("") ? null : txt_remark.getText());
            ps.setString(10, day + txt_start.getText());
            ps.setString(11, day + txt_end.getText());

            ps.execute();
            ps.close();
            conn.close();
            update_table();
        } catch (SQLException ex) {
            Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void update_table() throws SQLException {
        String day = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        Connection conn = DriverManager.getConnection("jdbc:sqlite:ETCarWash.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT employee.name, Plate_No, Start FROM wash JOIN employee on wash.employee = employee.ID WHERE start >= '" + day + "';");

        ObservableList<Activity> data = FXCollections.observableArrayList();
        int i = 1;
        while (rs.next()) {
            data.add(new Activity(i++, rs.getString("name"), rs.getString("Plate_No"), rs.getString("Start").split(" ")[1]));
        }
        tbl_activity.setItems(data);
    }
    
    private TableView<print_activity> create_print_table() throws SQLException{
        
        TableView tableView = new TableView();

        TableColumn<String, print_activity> column1 = new TableColumn<>("No");
        column1.setCellValueFactory(new PropertyValueFactory<>("Index"));
        TableColumn<String, print_activity> column2 = new TableColumn<>("Attendant Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("attendant"));
        TableColumn<String, print_activity> column3 = new TableColumn<>("Plate No");
        column3.setCellValueFactory(new PropertyValueFactory<>("plate"));
        TableColumn<String, print_activity> column4 = new TableColumn<>("Car Type");
        column4.setCellValueFactory(new PropertyValueFactory<>("carType"));
        TableColumn<String, print_activity> column5 = new TableColumn<>("Wash type");
        column5.setCellValueFactory(new PropertyValueFactory<>("washType"));
        TableColumn<String, print_activity> column6 = new TableColumn<>("Phone No");
        column6.setCellValueFactory(new PropertyValueFactory<>("phone"));
        TableColumn<String, print_activity> column7 = new TableColumn<>("Amount in birr");
        column7.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<String, print_activity> column8 = new TableColumn<>("Status");
        column8.setCellValueFactory(new PropertyValueFactory<>("status"));
        TableColumn<String, print_activity> column9 = new TableColumn<>("C.A. Sign");
        TableColumn<String, print_activity> column10 = new TableColumn<>("Starting Time");
        column10.setCellValueFactory(new PropertyValueFactory<>("start"));
        TableColumn<String, print_activity> column11 = new TableColumn<>("Ending Time");
        column11.setCellValueFactory(new PropertyValueFactory<>("end"));
        TableColumn<String, print_activity> column12 = new TableColumn<>("Remark");
        column12.setCellValueFactory(new PropertyValueFactory<>("remark"));
        TableColumn<String, print_activity> column13 = new TableColumn<>("Expense");
        column13.setCellValueFactory(new PropertyValueFactory<>("expense"));
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);
        tableView.getColumns().add(column6);
        tableView.getColumns().add(column7);
        tableView.getColumns().add(column8);
        tableView.getColumns().add(column9);
        tableView.getColumns().add(column10);
        tableView.getColumns().add(column11);
        tableView.getColumns().add(column12);
        tableView.getColumns().add(column13);

        String day = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        Connection conn = DriverManager.getConnection("jdbc:sqlite:ETCarWash.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM wash JOIN employee on wash.employee = employee.ID WHERE start >= '" + day + "';");

        ObservableList<print_activity> data = FXCollections.observableArrayList();
        int i = 1;
        while (rs.next()) {
            data.add(new print_activity(i++, rs.getString("name"), rs.getString("Plate_No"), rs.getString("Car_Type"), rs.getString("Wash_Type"), rs.getString("Customer_phone"), rs.getString("Price"), rs.getString("Status"), rs.getString("Start").split(" ")[1], rs.getString("End").split(" ")[1], rs.getString("Remark")));
        }
        tableView.setItems(data);

        tableView.setStyle("-fx-font-size:0.8em;");
        return tableView;
    }
}

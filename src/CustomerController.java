

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.jdbc.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerController implements Initializable{
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	Customer1 custR = null;
	private ArrayList<Customer1> data;
	private ObservableList<Customer1> dataList = FXCollections.observableArrayList();

    @FXML
    private VBox EmployeesVb;

    @FXML
    private TableColumn<Customer1, Double> debtCol;

    @FXML
    private TableColumn<Customer1, Integer> idCol;
    @FXML
    private TableColumn<Customer1, Date> lastOrderDate;

    @FXML
    private TableColumn<Customer1, Date> lastPaymentDate;
    @FXML
    private Button insertBt;

    @FXML
    private TableColumn<Customer1, String> nameCol;

   

    @FXML
    private Button showBoutghtProductBt;

    @FXML
    private Button showConatactBt;

    @FXML
    private CheckBox showDeptRB;

    @FXML
    private TableView<Customer1> tableViewCust;

    @FXML
    void insertNewRow(ActionEvent event) {
    	try {
			Stage stage = new Stage();
//			Pane pane = (Pane) FXMLLoader.load(getClass().getResource("InsertCustFX.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("InsertCustFX.fxml"));
			Pane pane = loader.load();
			if (custR!=null) {
			InsertCustomerController controller = loader.getController();
			controller.setCoustomer(custR);
			}
			Scene scene = new Scene(pane, 600, 400);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Open Error");
			alert.setContentText("Error:" + e.getMessage());
			e.printStackTrace();
		}
    }

    @FXML
    void rowViewCust(MouseEvent event) {
    	custR=tableViewCust.getSelectionModel().getSelectedItem();
//    	insertNewRow(new ActionEvent());
    }



    @FXML
    void showBoutghtProduct(ActionEvent event) {
    	try {
    		if (custR!=null) {
			Stage stage = new Stage();
//			Pane pane = (Pane) FXMLLoader.load(getClass().getResource("ShowBughtProduct.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowBughtProduct.fxml"));
			Pane pane = loader.load();
			
			ShowBughtProductController controller = loader.getController();
			controller.setCustomer(custR);
			
			Scene scene = new Scene(pane, 600, 400);
			stage.setScene(scene);
			stage.show();
    		}

		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Open Error");
			alert.setContentText("Error:" + e.getMessage());
			e.printStackTrace();
		}
    }

    @FXML
    void showConatact(ActionEvent event) {
    	try {
    		if (custR!=null) {
			Stage stage = new Stage();
//			Pane pane = (Pane) FXMLLoader.load(getClass().getResource("showContantCust/FX.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("showContantCustFX.fxml"));
			Pane pane = loader.load();
			
			ShowCustomerContactController controller = loader.getController();
			controller.setCustomer(custR);
			
			Scene scene = new Scene(pane, 600, 400);
			stage.setScene(scene);
			stage.show();
    	}
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Open Error");
			alert.setContentText("Error:" + e.getMessage());
			e.printStackTrace();
		}
    }

    @FXML
    void showOnlyWithDept(ActionEvent event) {
    	if(showDeptRB.isSelected()) {
    	dataList = getData(true);
		showDate();
    	}
    	else {
    		dataList = getData(false);
    		showDate();
    	}

    }
    private ObservableList<Customer1> getData(boolean debtB) {
    	data= new ArrayList<>();
//	String SQL = "select C.customerID,last_payment_date,Email,last_Order,Cname,phone,C.debt\r\n"
//			+ "from Employees E, customers C\r\n"
//			+ "where E.eid=C.EmployeeID";
	
	String SQL ="select * from customers";
	
	
	System.out.println(SQL);
	try {
		Connection con = DBConnecter.connectDB();
		Statement state = (Statement) con.createStatement();
		ResultSet rs = state.executeQuery(SQL);
		while (rs.next()) {
			Customer1 cust = new Customer1(Integer.parseInt(rs.getString(1)), rs.getDate(2),
					rs.getString(3), rs.getDate(4), rs.getString(5), Integer.parseInt(rs.getString(6)), Double.parseDouble(rs.getString(7)));
			if(debtB) {
			if(cust.getDebt()!=0.0)
			data.add(cust);
			}
			else
				data.add(cust);
			
		}
		dataList = FXCollections.observableArrayList(data);
		rs.close();
//		state.close();
		con.close();
		System.out.println("Number of data is " + data.size());

	} catch (Exception e) { // TODO Auto-generated catch block
		e.printStackTrace();
	}
	return dataList;
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dataList = getData(false);
		showDate();
		
	}

	private void showDate() {
		data = new ArrayList<>();
		tableViewCust.setEditable(true);

		idCol.setCellValueFactory(new PropertyValueFactory<Customer1, Integer>("customerID"));
		lastOrderDate.setCellValueFactory(new PropertyValueFactory<Customer1, Date>("last_Order"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Customer1, String>("Cname"));
		debtCol.setCellValueFactory(new PropertyValueFactory<Customer1, Double>("debt"));
		lastPaymentDate.setCellValueFactory(new PropertyValueFactory<Customer1, Date>("last_payment_date"));

		tableViewCust.setItems(dataList);
	}


}

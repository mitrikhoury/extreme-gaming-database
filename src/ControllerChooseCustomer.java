import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerChooseCustomer implements Initializable {

	@FXML
	private TextField Customer;

	@FXML
	private TableColumn<Customer, String> EmailColumn;

	@FXML
	private TableColumn<Customer, Integer> IDColumn;

	@FXML
	private TableColumn<Customer, Integer> PhoneColumn;

	@FXML
	private Button addCustomer;

	@FXML
	private TableView<Customer> customerTable;

	@FXML
	private TableColumn<Customer, Double> debtColumn;

	@FXML
	private TextField email;

	@FXML
	private TableColumn<Customer, Date> last_OrderColumn;

	@FXML
	private TableColumn<Customer, Date> last_payment_dateColumn;

	@FXML
	private TextField name;

	@FXML
	private TableColumn<Customer, String> nameColumn;

	@FXML
	private TextField phone;

	@FXML
	private Button selectCustomer;

	@FXML
	private TextField location;

	private final ObservableList<Customer> datalist = FXCollections.observableArrayList();

	private ControllerNewSell controllerNewSell;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		IDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
		PhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
		debtColumn.setCellValueFactory(new PropertyValueFactory<>("debt"));
		last_OrderColumn.setCellValueFactory(new PropertyValueFactory<>("lastOrder"));
		last_payment_dateColumn.setCellValueFactory(new PropertyValueFactory<>("lastPayment"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		customerTableSetUp();
		FilteredList<Customer> filteredData = new FilteredList<>(datalist, b -> true);
		Customer.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(customer -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (customer.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(customer.getCustomerID()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(customer.getPhone()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}else if (customer.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				else {
					return false;
				}

			});
		});

		SortedList<Customer> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(customerTable.comparatorProperty());
		customerTable.setItems(sortedData);

	}

	public void customerTableSetUp() {
		try {
			datalist.clear();
			Connection con = DBConnecter.connectDB();
			String query = "SELECT * FROM customers";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int customerID = rs.getInt("customerID");
				String name = rs.getString("Cname");
				String email = rs.getString("Email");
				double debt = rs.getDouble("debt");
				int phone = rs.getInt("Phone");
				Date last_Order = rs.getDate("last_Order");
				Date last_payment_date = rs.getDate("last_payment_date");
				Customer customer = new Customer(customerID, name, email, debt, phone, last_Order, last_payment_date);
				datalist.add(customer);
			}
			customerTable.setItems(datalist);
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void addNewCustomer(ActionEvent event) {
		try {
			Connection con = DBConnecter.connectDB();
			String address = "(" +  phone.getText() + ",'" + location.getText() + "')";
			String query1 = "insert into address (Phone,location) values " + address;
			Statement stmt1 = con.createStatement();
			stmt1.executeUpdate(query1);
			
			String values = "('" + email.getText() + "','" + name.getText() + "'," + phone.getText() + ")";
			String query2 = "INSERT INTO   (Email,  Cname, Phone) VALUES " + values;
			Statement stmt2 = con.createStatement();
			stmt2.executeUpdate(query2);

			customerTableSetUp();
			FilteredList<Customer> filteredData = new FilteredList<>(datalist, b -> true);
			Customer.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(customer -> {

					if (newValue == null || newValue.isEmpty()) {
						return true;
					}

					String lowerCaseFilter = newValue.toLowerCase();

					if (customer.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if (String.valueOf(customer.getCustomerID()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if (String.valueOf(customer.getPhone()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else {
						return false;
					}

				});
			});

			SortedList<Customer> sortedData = new SortedList<>(filteredData);
			sortedData.comparatorProperty().bind(customerTable.comparatorProperty());
			customerTable.setItems(sortedData);
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void selectedCustomer(ActionEvent event) {
		Customer customer = customerTable.getSelectionModel().getSelectedItem();
		controllerNewSell.setCustomer(customer);
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Customer have been selected");
		alert.showAndWait();
	}

	public void setControllerNewSell(ControllerNewSell controllerNewSell) {
		this.controllerNewSell = controllerNewSell;
	}

}
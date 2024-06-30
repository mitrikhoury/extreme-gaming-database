import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller implements Initializable {

	@FXML
	private TableColumn<Report, Integer> AmountColumn;

	@FXML
	private TableColumn<Product, Integer> Barcode;

	@FXML
	private TableColumn<Product, Integer> IdCategories;

	@FXML
	private TableColumn<Product, String> PnameS;

	@FXML
	private TableView<Report> Report;

	@FXML
	private TableColumn<Report, String> ReportColumn;

	@FXML
	private Button dashboard;

	@FXML
	private Button dispose;

	@FXML
	private Button Storge;

	@FXML
	private TableColumn<Product, Date> expiredDate;

	@FXML
	private TableColumn<Product, Date> manufacturingDate;

	@FXML
	private ChoiceBox<String> mothChoice;

	@FXML
	private Button newSell;

	@FXML
	private TableView<Product> outOfStock;

	@FXML
	private TableView<Product> outOfStockSoon;

	@FXML
	private Button payingOff;

	@FXML
	private TableColumn<Product, String> pname;

	@FXML
	private TableColumn<Product, Double> pprice;

	@FXML
	private TableColumn<Product, Integer> weight;

	@FXML
	private ChoiceBox<String> yearChoice;

	@FXML
	private LineChart<?, ?> salesLineChart;

	@FXML
	private CategoryAxis xaxis;

	@FXML
	private NumberAxis yaxis;

	@FXML
	private BorderPane BPane;

	@FXML
	private Pane dashBPane;

	private int employeeID;

	private boolean isManager;

	private LoginController loginController;

	@FXML
	private Button Customer;

	@FXML
	private Button Employee;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pname.setCellValueFactory(new PropertyValueFactory<>("name"));
		outOfStockTable();
		Barcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
		PnameS.setCellValueFactory(new PropertyValueFactory<>("name"));
		pprice.setCellValueFactory(new PropertyValueFactory<>("price"));
		weight.setCellValueFactory(new PropertyValueFactory<>("amount"));
		expiredDate.setCellValueFactory(new PropertyValueFactory<>("expiredDate"));
		manufacturingDate.setCellValueFactory(new PropertyValueFactory<>("manufacturingDate"));
		IdCategories.setCellValueFactory(new PropertyValueFactory<>("IdCategories"));
		outOfStockSoonTable();
		AmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
		ReportColumn.setCellValueFactory(new PropertyValueFactory<>("reportType"));
		reportTable();
		chartLine();
		months();
		years();
	}

	public void outOfStockTable() {
		try {
			Connection con = DBConnecter.connectDB();
			String query = "SELECT pname FROM prouct WHERE amount<= 0 OR  insuranceExpirationDate <= CURDATE()";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String Pname = rs.getString("pname");
				Product product = new Product(Pname);
				outOfStock.getItems().add(product);
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void outOfStockSoonTable() {
		try {
			Connection con = DBConnecter.connectDB();
			String query = "SELECT * FROM prouct WHERE amount <= 5 and insuranceExpirationDate > CURDATE()";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int barcode = rs.getInt("Barcode");
				String name = rs.getString("pname");
				double price = rs.getDouble("pprice");
				int weight = rs.getInt("amount");
				Date expiredDate = rs.getDate("insuranceExpirationDate");
				Date manufacturingDate = rs.getDate("manufacturingDate");
				int IdCategories = rs.getInt("IdCategories");
				Product product = new Product(barcode, name, price, weight, expiredDate, manufacturingDate,
						IdCategories);
				outOfStockSoon.getItems().add(product);
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void reportTable() {// total products,total empolyees,total customers,total out of stock, total
								// sales
		try {
			Connection con = DBConnecter.connectDB();
			String query1 = "SELECT COUNT(*) AS total_employees FROM Employees";
			Statement stmt1 = con.createStatement();
			ResultSet rs1 = stmt1.executeQuery(query1);
			while (rs1.next()) {
				int totalEmp = rs1.getInt("total_employees");
				Report rep1 = new Report("Total employees", totalEmp);
				Report.getItems().add(rep1);
			}
			String query2 = "SELECT SUM(amount) AS total_amount FROM prouct";
			Statement stmt2 = con.createStatement();
			ResultSet rs2 = stmt2.executeQuery(query2);
			while (rs2.next()) {
				int totalProd = rs2.getInt("total_amount");
				Report rep2 = new Report("Total Product", totalProd);
				Report.getItems().add(rep2);
			}

			String query3 = "SELECT COUNT(*) AS total_customers FROM customers";
			Statement stmt3 = con.createStatement();
			ResultSet rs3 = stmt3.executeQuery(query3);
			while (rs3.next()) {
				int totalCusmt = rs3.getInt("total_customers");
				Report rep3 = new Report("Total Customers", totalCusmt);
				Report.getItems().add(rep3);
			}

			String query4 = "SELECT count(*) as total_outofstock FROM prouct WHERE amount<= 0 or  insuranceExpirationDate <= CURDATE()";
			Statement stmt4 = con.createStatement();
			ResultSet rs4 = stmt4.executeQuery(query4);
			while (rs4.next()) {
				int totaloutofs = rs4.getInt("total_outofstock");
				Report rep4 = new Report("Total out of stock", totaloutofs);
				Report.getItems().add(rep4);
			}

			String query5 = "SELECT count(oid) AS total_orders FROM Orders;";
			Statement stmt5 = con.createStatement();
			ResultSet rs5 = stmt5.executeQuery(query5);
			while (rs5.next()) {
				int totalOrders = rs5.getInt("total_orders");
				Report rep5 = new Report("Total Orders", totalOrders);
				Report.getItems().add(rep5);
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void chartLine() {
		XYChart.Series series = new XYChart.Series();
		int ser = 1;
		try {
			Connection con = DBConnecter.connectDB();
			String query = "select totalPrice from orders";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				series.getData().add(new XYChart.Data(String.valueOf(ser), rs.getInt("totalPrice")));
				ser++;
			}
			salesLineChart.getData().add(series);
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void months() {
		mothChoice.setValue("Month");
		mothChoice.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August",
				"September", "October", "November", "December");
		mothChoice.setOnAction(e -> {
			XYChart.Series series = new XYChart.Series();
			int ser = 1;
			try {
				String month = "'" + mothChoice.getValue() + "'";
				Connection con = DBConnecter.connectDB();
				String query = "select totalPrice from orders where MONTHNAME(ODate) = " + month;
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					series.getData().add(new XYChart.Data(String.valueOf(ser), rs.getInt("totalPrice")));
					ser++;
				}
				salesLineChart.getData().clear();
				salesLineChart.getData().add(series);
				con.close();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	public void years() {
		yearChoice.setValue("Year");
		yearChoice.getItems().addAll("2020", "2021", "2022", "2023");
		yearChoice.setOnAction(e -> {
			XYChart.Series series = new XYChart.Series();
			int ser = 1;
			try {
				String year = yearChoice.getValue();
				Connection con = DBConnecter.connectDB();
				String query = "select totalPrice from orders where year(ODate) = " + year;
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					series.getData().add(new XYChart.Data(String.valueOf(ser), rs.getInt("totalPrice")));
					ser++;
				}
				salesLineChart.getData().clear();
				salesLineChart.getData().add(series);
				con.close();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	@FXML
	void loadNewSell(ActionEvent event) {
		Parent Root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("NewSell.fxml"));
			AnchorPane anchorPane = loader.load();
			ControllerNewSell controller = loader.getController();

			// Pass the reference of LoginController to Controller
			controller.setController(this);
			controller.setEmployeeID(employeeID);

			// BPane.getChildren().clear();
			BPane.setCenter(anchorPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void loadDashboard(ActionEvent event) {
		BPane.setCenter(dashBPane);
	}

	@FXML
	void loadPayingOf(ActionEvent event) {
		Parent Root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PayingOff.fxml"));
			AnchorPane anchorPane = loader.load();
			BPane.setCenter(anchorPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void loadDispose(ActionEvent event) {
		Parent Root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Disposal.fxml"));
			AnchorPane anchorPane = loader.load();
			BPane.setCenter(anchorPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void loadStorge(ActionEvent event) {
		Parent Root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Storge.fxml"));
			AnchorPane anchorPane = loader.load();
			BPane.setCenter(anchorPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void loadEmployee(ActionEvent event) {
		Parent Root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployessFX.fxml"));
			VBox anchorPane = loader.load();
			BPane.setCenter(anchorPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void loadCustomer(ActionEvent event) {
		Parent Root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomersFX.fxml"));
			VBox anchorPane = loader.load();
			BPane.setCenter(anchorPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public void setIsManager(boolean isManager) {
		this.isManager = isManager;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

}

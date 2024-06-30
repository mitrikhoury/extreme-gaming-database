import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ControllerNewSell implements Initializable {

	@FXML
	private TableColumn<Product, Integer> BarcodeColumn;

	@FXML
	private TableColumn<Product, Integer> IdCategoriesColumn;

	@FXML
	private Label TotCost;

	@FXML
	private Button addProduct;

	@FXML
	private TableColumn<Product, Integer> amountColumn;

	@FXML
	private Button chooseCustomer;

	@FXML
	private Label date;

	@FXML
	private Label costAD;

	@FXML
	private TextField discount;
	
	 @FXML
	 private TextField warehouse;
	
	@FXML
	private TableColumn<Product, Date> expiredDateColumn;

	@FXML
	private TableColumn<Product, Date> manufacturingDateColumn;

	@FXML
	private TableColumn<Product, String> nameColumn;

	@FXML
	private TextField paid;

	@FXML
	private TableColumn<Product, Double> priceColuumn;

	@FXML
	private TableView<Product> productsOrdered;

	private final ObservableList<Product> datalist = FXCollections.observableArrayList();

	private Customer customer;
	
	private int employeeID;
	
	private Controller controller;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		BarcodeColumn.setCellValueFactory(new PropertyValueFactory<>("barcode"));
		IdCategoriesColumn.setCellValueFactory(new PropertyValueFactory<>("IdCategories"));
		expiredDateColumn.setCellValueFactory(new PropertyValueFactory<>("expiredDate"));
		manufacturingDateColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturingDate"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		priceColuumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

		LocalDate currentDate = LocalDate.now();
		date.setText(currentDate.toString());
		TotCost.setText("0");
		costAD.setText("0");
	}

	@FXML
	void addProductScene(ActionEvent event) {
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProductStage.fxml"));
			root = loader.load();

			// Get the controller of the AddProductStage.fxml
			ControllerAddProduct addProductController = loader.getController();

			// Pass the reference of ControllerNewSell to ControllerAddProduct
			addProductController.setControllerNewSell(this);

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void chooseCustomerScene(ActionEvent event) {
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ChooseCustomerStage.fxml"));
			root = loader.load();

			// Get the controller of the AddProductStage.fxml
			ControllerChooseCustomer SelectCustomerController = loader.getController();

			// Pass the reference of ControllerNewSell to ControllerAddProduct
			SelectCustomerController.setControllerNewSell(this);

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void calculateDiscount(KeyEvent event) {
		try {
			double discountPrecent = Integer.parseInt(discount.getText());
			double discount = Double.parseDouble(TotCost.getText()) * (discountPrecent / 100);
			double discountedPrice = Double.parseDouble(TotCost.getText()) - discount;
			costAD.setText(String.valueOf(discountedPrice));
			System.out.println(discount + " " + discountedPrice);
		} catch (NumberFormatException e) {

		}

	}

	@FXML
	void sellTo(ActionEvent event) {
		if (!(discount.getText().isEmpty()) && !(paid.getText().isEmpty()) && customer != null && !(warehouse.getText().isEmpty())) {
			try {
				double debt = Double.parseDouble(paid.getText()) - Double.parseDouble(costAD.getText());
				if (debt < 0)
					debt = debt * -1;
				else
					debt = 0;
				String updateInfoCustomer = "Set last_payment_date = CURDATE(), last_Order = CURDATE(), debt = " + debt;
				Connection con = DBConnecter.connectDB();
				String query = "update customers " + updateInfoCustomer + " where customerID = "
						+ customer.getCustomerID();
				Statement stmt = con.createStatement();
				stmt.executeUpdate(query);

				int maxOrderID = 0;

				String query1 = "select max(oid) as orderID from orders";
				Statement stmt1 = con.createStatement();
				ResultSet rs1 = stmt1.executeQuery(query1);
				while (rs1.next()) {
					maxOrderID = rs1.getInt("orderID");
					maxOrderID++;
				}

				int maxDelID = 0;

				String query2 = "select max(deliveryID) as deliveryID from Delivery";
				Statement stmt2 = con.createStatement();
				ResultSet rs2 = stmt2.executeQuery(query2);
				while (rs2.next()) {
					maxDelID = rs2.getInt("deliveryID");
					maxDelID++;
				}

				String location = "";
				String query3 = "select location from customers c, address a  where c.phone = a.phone and c.customerID = "
						+ customer.getCustomerID();
				Statement stmt3 = con.createStatement();
				ResultSet rs3 = stmt3.executeQuery(query3);
				while (rs3.next()) {
					location = rs3.getString("location");
				}

				String insetDeleveryInfo = "(" + maxDelID + ",'On the way','" + location + "')";
				String query4 = "INSERT INTO Delivery (deliveryID, delievery_status, delievery_location) VALUES "
						+ insetDeleveryInfo;
				Statement stmt4 = con.createStatement();
				stmt4.executeUpdate(query4);

				String insetOrderInfo = "(" + maxOrderID + "," + discount.getText() + ",CURDATE()," + TotCost.getText()
						+ "," + maxDelID + "," + customer.getCustomerID() + "," + employeeID+ ",'" + warehouse.getText() +"')";
				System.out.println(insetOrderInfo);
				String query5 = "INSERT INTO Orders (oid,orderDiscount, ODate, totalPrice,del_id,customerID,eid,ware_house) VALUES "
						+ insetOrderInfo;
				Statement stmt5 = con.createStatement();
				stmt5.executeUpdate(query5);

				for (int i = 0; i < datalist.size(); i++) {
					Product product = datalist.get(i);
					int barcode = product.getBarcode();
					int amount = product.getAmount();
					String productOrderInfo = "(" + barcode + "," + maxOrderID + "," + amount + ")";
					String query6 = "INSERT INTO OPcontains (barcoce, OrderId,amount) VALUES " + productOrderInfo;
					Statement stmt6 = con.createStatement();
					stmt6.executeUpdate(query6);
					datalist.remove(i);
				}
				TotCost.setText("0");
				costAD.setText("0");
				paid.clear();
				discount.clear();

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setContentText("Order has been processed");
				alert.showAndWait();

				con.close();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void addToTable(Product product) {
		datalist.add(product);
		productsOrdered.setItems(datalist);
		double cost = (product.getPrice() * product.getAmount()) + Double.parseDouble(TotCost.getText());
		TotCost.setText(String.valueOf(cost));
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
}

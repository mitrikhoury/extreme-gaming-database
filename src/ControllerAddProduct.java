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
import javafx.scene.input.MouseEvent;

public class ControllerAddProduct implements Initializable {

	@FXML
	private TableColumn<Product, Integer> BarcodeColumn;

	@FXML
	private TableColumn<Categories, Integer> IDcoulumn;

	@FXML
	private TableColumn<Product, Integer> IdCategoriesColumn;

	@FXML
	private Button addButton;

	@FXML
	private TextField amount;

	@FXML
	private TableColumn<Product, Integer> amountColumn;

	@FXML
	private TextField category;

	@FXML
	private TableColumn<Product, Date> expiredDateColumn;

	@FXML
	private TableColumn<Product, Date> manufacturingDateColumn;

	@FXML
	private TableColumn<Categories, String> cnameColumn;

	@FXML
	private TableColumn<Product, String> nameColumn;

	@FXML
	private TableColumn<Product, Double> priceColuumn;

	@FXML
	private TextField product;

	@FXML
	private TableView<Product> productTable;

	@FXML
	private TableView<Categories> categoriesTable;

	private final ObservableList<Categories> datalistCate = FXCollections.observableArrayList();

	private final ObservableList<Product> datalistProd = FXCollections.observableArrayList();

	private ControllerNewSell controllerNewSell;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		IDcoulumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
		cnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		catTable();

		BarcodeColumn.setCellValueFactory(new PropertyValueFactory<>("barcode"));
		IdCategoriesColumn.setCellValueFactory(new PropertyValueFactory<>("IdCategories"));
		expiredDateColumn.setCellValueFactory(new PropertyValueFactory<>("expiredDate"));
		manufacturingDateColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturingDate"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		priceColuumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

		FilteredList<Categories> filteredDataCate = new FilteredList<>(datalistCate, b -> true);
		category.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredDataCate.setPredicate(categories -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (categories.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(categories.getID()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else {
					return false;
				}

			});
		});

		SortedList<Categories> sortedDataCateg = new SortedList<>(filteredDataCate);
		sortedDataCateg.comparatorProperty().bind(categoriesTable.comparatorProperty());
		categoriesTable.setItems(sortedDataCateg);

	}

	public void catTable() {
		try {
			Connection con = DBConnecter.connectDB();
			String query = "select * from categories";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("cname");
				Categories categ = new Categories(id, name);
				datalistCate.add(categ);
			}
			con.close();
			categoriesTable.setItems(datalistCate);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void productCategory(MouseEvent event) {
		try {
			datalistProd.clear();
			Categories categ = categoriesTable.getSelectionModel().getSelectedItem();
			Connection con = DBConnecter.connectDB();
			String query = "select * from prouct where IdCategories = " + categ.getID();
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
				Product product = new Product(barcode, name, price, weight, expiredDate,
						manufacturingDate, IdCategories);
				datalistProd.add(product);
			}
			con.close();
			productTable.setItems(datalistProd);

			FilteredList<Product> filteredDataProd = new FilteredList<>(datalistProd, b -> true);
			product.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredDataProd.setPredicate(product -> {

					if (newValue == null || newValue.isEmpty()) {
						return true;
					}

					String lowerCaseFilter = newValue.toLowerCase();

					if (product.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if (String.valueOf(product.getPrice()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else {
						return false;
					}

				});
			});

			SortedList<Product> sortedDataProd = new SortedList<>(filteredDataProd);
			sortedDataProd.comparatorProperty().bind(productTable.comparatorProperty());
			productTable.setItems(sortedDataProd);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void add(ActionEvent event) {
		try {
			if (!(amount.getText().isEmpty()) && categoriesTable.getSelectionModel().getSelectedItem() != null) {
				Product product = productTable.getSelectionModel().getSelectedItem();
				int newAmount = product.getAmount() - Integer.parseInt(amount.getText());
				if (newAmount < 0) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Ordered amount is more than the store has");
					alert.showAndWait();
				} else {
					Connection con = DBConnecter.connectDB();
					String query = "UPDATE prouct SET amount = " + newAmount + " WHERE barcode = "
							+ product.getBarcode();
					Statement stmt = con.createStatement();
					stmt.executeUpdate(query);
					con.close();
					product.setAmount(Integer.parseInt(amount.getText()));
					controllerNewSell.addToTable(product);
					amount.clear();
					updateProdTable();
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateProdTable() {
		try {
			datalistProd.clear();
			Categories categ = categoriesTable.getSelectionModel().getSelectedItem();
			Connection con = DBConnecter.connectDB();
			String query = "select * from prouct where IdCategories = " + categ.getID();
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
				Product product = new Product(barcode, name, price, weight, expiredDate,
						manufacturingDate, IdCategories);
				datalistProd.add(product);
			}
			con.close();
			productTable.setItems(datalistProd);

			FilteredList<Product> filteredDataProd = new FilteredList<>(datalistProd, b -> true);
			product.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredDataProd.setPredicate(product -> {

					if (newValue == null || newValue.isEmpty()) {
						return true;
					}

					String lowerCaseFilter = newValue.toLowerCase();

					if (product.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if (String.valueOf(product.getPrice()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else {
						return false;
					}

				});
			});

			SortedList<Product> sortedDataProd = new SortedList<>(filteredDataProd);
			sortedDataProd.comparatorProperty().bind(productTable.comparatorProperty());
			productTable.setItems(sortedDataProd);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setControllerNewSell(ControllerNewSell controllerNewSell) {
		this.controllerNewSell = controllerNewSell;
	}

}

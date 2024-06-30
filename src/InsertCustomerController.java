

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class InsertCustomerController implements Initializable {
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	Customer1 customer = null;
	ArrayList<Customer1> data = null;
	private ObservableList<Customer1> dataList = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Customer1, String> EmailCol;

	@FXML
	private TextArea Email_contantTA;

	
	@FXML
	private ImageView addimage;

	@FXML
	private TextField debtTF;

	@FXML
	private ImageView deleteImage;

	@FXML
	private TableColumn<Customer1, Double> depCol;

	@FXML
	private TableColumn<Customer1, Integer> idCol;

	@FXML
	private TableColumn<Customer1, Date> lastOrderDateCol;

	@FXML
	private DatePicker lastOrderdateTF;

	@FXML
	private DatePicker lastPaymentDAteTf;

	@FXML
	private TableColumn<Customer1, Date> lastPaymetDateCol;

	@FXML
	private TextField nameTF;

	

	@FXML
	private TableColumn<Customer1, String> namecol;

	@FXML
	private TableColumn<Customer1, Integer> phoneCol;

	@FXML
	private TextField phoneTF;

	@FXML
    private TextField custID;
	@FXML
	private TableView<Customer1> tableViewCust;

	@FXML
	void addEmployee(MouseEvent event) {
		if(custID.getText()!=""&&lastPaymentDAteTf.getValue()!=null&&Email_contantTA.getText()!=""
				&&lastOrderdateTF.getValue()!=null&&nameTF.getText()!=""&&phoneTF.getText()!=""&&debtTF.getText()!="") {
		int customerID = Integer.parseInt(custID.getText().trim());
		Date last_payment_dateDate = Date.valueOf(lastPaymentDAteTf.getValue());
		String Email = Email_contantTA.getText().trim();
		Date last_Order = Date.valueOf(lastOrderdateTF.getValue());
		String Cname = nameTF.getText().trim();
		int Phone = Integer.parseInt(phoneTF.getText().trim());
		double debt = Double.parseDouble(debtTF.getText().trim());
		String sqlP = "insert into address (Phone) value (?)";
		
		String sql = "insert into customers (customerID ,last_payment_date,Email,last_Order,Cname,Phone,debt)\r\n"
				+ "values (?,?,?,?,?,?,?)";
		System.out.println(sql);
		
		try {
			conn = DBConnecter.connectDB();
			pst = conn.prepareStatement(sqlP);
			pst.setInt(1, Phone);
			pst.execute();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			conn = DBConnecter.connectDB();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, customerID);
			pst.setDate(2, last_payment_dateDate);
			pst.setString(3, Email);
			pst.setDate(4, last_Order);
			pst.setString(5, Cname);
			pst.setInt(6, Phone);
			pst.setDouble(7, debt);

			pst.execute();
			custID.clear();
			lastPaymentDAteTf.setValue(null);
			Email_contantTA.clear();
			lastOrderdateTF.setValue(null);
			phoneTF.clear();
			debtTF.clear();
			nameTF.clear();
			Customer1 custom = new Customer1(customerID, last_payment_dateDate, Email, last_Order,Cname, Phone, debt);
			dataList.add(custom);
//			JOptionPane.showMessageDialog(null, "Succes Insertion");
			conn.close();
			showDate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		}
	}

	@FXML
	void deleteRow(MouseEvent event) {
		if (custID!=null&&custID.getText() != "") {

			String sql = "delete from customers where customerID=? ";
			System.out.println(sql);
			try {
				conn = DBConnecter.connectDB();
				pst = conn.prepareStatement(sql);
				pst.setInt(1, Integer.parseInt(custID.getText().trim()));
				pst.execute();
//				JOptionPane.showMessageDialog(null, "Customer " + custID.getText().trim() + " Deleted Successfully");
//     			clickshowdata(event);
				for (Customer1 item : dataList) {
					if (item.getCustomerID() == Integer.parseInt(custID.getText().trim())) {
						dataList.remove(item);
						break;
					}
				}

				custID.clear();
				DBConnecter.connectDB().close();
				showDate();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		} else if (customer != null) {
			int customerID = customer.getCustomerID();

			String sql = "delete from customers where customerID = ?";
			try {
				conn = DBConnecter.connectDB();
				pst = conn.prepareStatement(sql);
				pst.setInt(1, customerID);
				pst.execute();
//				JOptionPane.showMessageDialog(null, "Customer " + customerID + " Deleted Successfully");
//     			clickshowdata(event);
//     			deleteNum.clear();
				dataList.remove(customer);
				customer = null;

				custID.clear();
				conn.close();
				showDate();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}

		}
	}

	@FXML
	void rowViewCust(MouseEvent event) {
		Customer1 selectedModel = tableViewCust.getSelectionModel().getSelectedItem();
		if (selectedModel != null)
			setCoustomer(selectedModel);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	private void showDate() {
		data = new ArrayList<>();
		data.add(customer);
		if (customer != null)
			dataList = FXCollections.observableArrayList(data);

		tableViewCust.setEditable(true);

		idCol.setCellValueFactory(new PropertyValueFactory<Customer1, Integer>("customerID"));
		lastOrderDateCol.setCellValueFactory(new PropertyValueFactory<Customer1, Date>("last_Order"));
		namecol.setCellValueFactory(new PropertyValueFactory<Customer1, String>("Cname"));
		depCol.setCellValueFactory(new PropertyValueFactory<Customer1, Double>("debt"));
		EmailCol.setCellValueFactory(new PropertyValueFactory<Customer1, String>("Email"));
		lastPaymetDateCol.setCellValueFactory(new PropertyValueFactory<Customer1, Date>("last_payment_date"));
		phoneCol.setCellValueFactory(new PropertyValueFactory<Customer1, Integer>("Phone"));
		
		tableViewCust.getItems().clear();
		tableViewCust.setItems(dataList);
	}

	public void setCoustomer(Customer1 cust) {
		this.customer = cust;
		showDate();
	}
}

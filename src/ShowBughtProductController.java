

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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ShowBughtProductController implements Initializable{
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	Customer1 customer = null;
	ArrayList<HiredCustomer> data = null;
	private ObservableList<HiredCustomer> dataList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<HiredCustomer,Integer > AmountCol;
    @FXML
    private TableColumn<HiredCustomer, String> custNameCol;
    @FXML
    private TableColumn<HiredCustomer, Date> ExpiryDateTF;
    @FXML
    private TableView<HiredCustomer> tableView;


    @FXML
    private Label nameBughtLabel;

    @FXML
    private TableColumn<HiredCustomer, String> productNamecol;

    @FXML
    private TableColumn<HiredCustomer, Date> productionDateCol;

    @FXML
    private TableColumn<HiredCustomer, Date> saleDateCol;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		helper();
		
	}
	private void helper() {
		if(customer!=null) {
			dataList=getData();
			showDate();
		}
		else {
			
		}
	}
    public void setCustomer(Customer1 cust) {
    	this.customer = cust;
    	helper();
    	
    }
    private void showDate() {
		
		custNameCol.setCellValueFactory(new PropertyValueFactory<HiredCustomer, String>("Cname"));
	     AmountCol.setCellValueFactory(new PropertyValueFactory<HiredCustomer, Integer>("Amount"));

	     ExpiryDateTF.setCellValueFactory(new PropertyValueFactory<HiredCustomer, Date>("insuranceExpirationDate"));



	     productNamecol.setCellValueFactory(new PropertyValueFactory<HiredCustomer, String>("productName"));

	    productionDateCol.setCellValueFactory(new PropertyValueFactory<HiredCustomer, Date>("productuionDate"));

	    saleDateCol.setCellValueFactory(new PropertyValueFactory<HiredCustomer, Date>("soldDate"));
	    tableView.getItems().clear();
	    
	    tableView.setItems(dataList);
    
    }
    private ObservableList<HiredCustomer> getData() {
    	data= new ArrayList<>();
    	String SQL ="select C.Cname , P.Amount , P.manufacturingDate,P.insuranceExpirationDate,O.ODate,P.pname\r\n"
    			+ "	from prouct P,customers C ,Orders O ,OPcontains OP\r\n"
    			+ "	where C.customerID=O.customerID and  OP.barcoce =P.Barcode and OP.OrderId=O.oid";
    	System.out.println(SQL);
    	try {
    		Connection con = DBConnecter.connectDB();
    		Statement state = (Statement) con.createStatement();
    		ResultSet rs = state.executeQuery(SQL);
    		while (rs.next()) {
    			String name = rs.getString(1);
    			HiredCustomer cust = new HiredCustomer(name,Integer.parseInt(rs.getString(2)), rs.getDate(3),
    					rs.getDate(4), rs.getDate(5), rs.getString(6));
//    			if (customer.getCname().equals(name))
    				data.add(cust);
    			
    		}
    		dataList = FXCollections.observableArrayList(data);
    		rs.close();
//    		state.close();
    		con.close();
    		System.out.println("Number of data is " + data.size());

    	} catch (Exception e) { // TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return dataList;
        }
}

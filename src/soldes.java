

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class soldes implements Initializable{

    @FXML
    private TableColumn<soldclass, Integer> Amount;

    @FXML
    private TableColumn<soldclass, String> BougthBy;

    @FXML
    private TableColumn<soldclass, Date> ExpiredDate;

    @FXML
    private TableColumn<soldclass, Date> SaleDate;

    @FXML
    private TableColumn<soldclass, String> SoldBy;

   

    @FXML
    private Label labelCategores;

    @FXML
    private TableColumn<soldclass, Date> manufactirangDate;

    
    @FXML
    private TableView<soldclass> soldTable;
    @FXML
    private TableColumn<soldclass, String> productName;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Amount.setCellValueFactory(new PropertyValueFactory<soldclass, Integer>("amount"));
		BougthBy.setCellValueFactory(new PropertyValueFactory<soldclass, String>("BoughtBy"));
		SaleDate.setCellValueFactory(new PropertyValueFactory<soldclass, Date>("SaleDate"));
		ExpiredDate.setCellValueFactory(new PropertyValueFactory<soldclass, Date>("insuranceExpirationDate"));
		SoldBy.setCellValueFactory(new PropertyValueFactory<soldclass, String>("SoldBy"));
		productName.setCellValueFactory(new PropertyValueFactory<soldclass, String>("Pname"));
		manufactirangDate.setCellValueFactory(new PropertyValueFactory<soldclass, Date>("manufacturingDate"));
		
		Tablesold();
		
	}
	
	public void Tablesold () {
    	try {
    		int catId = Storge.pro.getID();  // get the of the selected item
    		 java.sql.Connection con = DBConnecter.connectDB();
			System.out.println("conection Start");
			String quere ="select p.pname ,p.manufacturingDate , p.insuranceExpirationDate , o.ODate , e.ename , c.Cname ,p.amount from prouct p , customers c , Orders o , Employees e , OPcontains op where p.IdCategories="+catId+" and e.eid = o.eid and c.customerID = o.customerID and o.oid = op.OrderId and op.barcoce = p.Barcode";
					
			java.sql.Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(quere);
			while(rs.next()) {
				String pname = rs.getString("pname");
				 Date man = rs.getDate("manufacturingDate");
				Date expired = rs.getDate("insuranceExpirationDate");
				Date odate = rs.getDate("ODate");
				String ename = rs.getString("ename");
				String cname = rs.getString("Cname");
				int amount= rs.getInt("amount");
					       
				//System.out.println("employee " + ename);
				
				soldTable.getItems().add(new soldclass(expired, man, odate, ename, cname, amount, pname));	
				System.out.printf(expired +" "+man + " "+ odate +" " + ename + " " + cname+ " "+ amount+ "pname :" +pname  );
			}
			
			rs.close();
			stm.close();
			
			String nameCat =Storge.pro.getName();
			labelCategores.setText(nameCat + " Sold");
			con.close();
			System.out.println("conection end");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//payingTable.getSelectionModel().getSelectedItem();
    }
	
	

}
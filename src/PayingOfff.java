

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PayingOfff implements Initializable{

	private ArrayList<Custemers> data;
    private ObservableList<Custemers> dataList;
	
    @FXML
    private Button AprovePayMentBT;

   

    @FXML
    private TableView<Custemers> payingTable;
    @FXML
    private TableColumn<Custemers, Integer> CustemerId;
    

    @FXML
    private TableColumn<Custemers, String> CustemerName;

    @FXML
    private TableColumn<Custemers, Double> Dept;

  
    @FXML
    private Label LabelSelectCustemer;

   

    @FXML
    private Button SelectCustemer;

    @FXML
    private TextField TfAmount;

    @FXML
    void AprovePay(ActionEvent event) {
    	Custemers x	= payingTable.getSelectionModel().getSelectedItem();
    	int Id = x.getCustemerId();
    	String name = x.getCustemername();
    	double dept = x.getDept();
    	try {
			java.sql.Connection con = DBConnecter.connectDB();
			System.out.println("conection Start");
			String quere =" SET SQL_SAFE_UPDATES = 0";
			java.sql.Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(quere);
			
			rs.close();
			stm.close();
			int tfamount = Integer.parseInt(TfAmount.getText());
            double result = dept - tfamount;
            System.out.println(result);
			String quere1 =" update  customers set debt ="+result+"where customerID ="+Id;
			java.sql.Statement stm1 = con.createStatement();
			stm1.executeUpdate(quere1);
			
			
			payingTable.getItems().clear();
			TableCustemerloud () ;
			LabelSelectCustemer.setText("");
			TfAmount.clear();
			stm1.close();
			String quere3 =" SET SQL_SAFE_UPDATES = 1";
			java.sql.Statement stm3 = con.createStatement();
			ResultSet rs3 = stm3.executeQuery(quere3);
			
			
			rs3.close();
			stm3.close();
			
			con.close();
			System.out.println("conection end");
			

			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
    	
   }
    


    @FXML
    void selectCustemerrr(ActionEvent event) {
    	Custemers x	= payingTable.getSelectionModel().getSelectedItem();
    	int id = x.getCustemerId();
    	LabelSelectCustemer.setText(String.valueOf(id));
    }
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		CustemerId.setCellValueFactory(new PropertyValueFactory<Custemers, Integer>("CustemerId"));
		CustemerName.setCellValueFactory(new PropertyValueFactory<Custemers, String>("Custemername"));
		Dept.setCellValueFactory(new PropertyValueFactory<Custemers, Double>("dept"));
		TableCustemerloud();
	
	}

    
    public void TableCustemerloud () {
    	try {
    		java.sql.Connection con = DBConnecter.connectDB();
			System.out.println("conection Start");
			String quere ="select customerID, Cname , debt from customers";
			java.sql.Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(quere);
			while(rs.next()) {
		         int id = rs.getInt("customerID");
				double dept=rs.getDouble("debt");
				String custamerN = rs.getString("Cname");
				payingTable.getItems().add(new Custemers(id, custamerN, dept));	
				System.out.printf("id: " +id , " name : "+ custamerN  + "dept :" + dept );
			}
			
			rs.close();
			stm.close();

			con.close();
			System.out.println("conection end");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//payingTable.getSelectionModel().getSelectedItem();
    }

}
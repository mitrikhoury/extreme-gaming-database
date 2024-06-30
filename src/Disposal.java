
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Disposal implements Initializable{

    @FXML
    private TableColumn<Product, Integer> Amount;

    @FXML
    private Button DisposalBt;

    @FXML
    private TableView<Product> DisposalTable;

    @FXML
    private TableColumn<Product, Date> ExpiredDate;

    @FXML
    private TableColumn<Product, Date> PriductionDate;

    @FXML
    private TableColumn<Product, Integer> ProductId;

    @FXML
    private TableColumn<Product, String> ProductName;

    @FXML
    private TextField TFAmount;

    @FXML
    private CheckBox expiredCheckBox;

    @FXML
    private Label massegeLabel;

    @FXML
    void ExpiredAction(ActionEvent event) {
     if(expiredCheckBox.isSelected()) {
    	 DisposalTable.getItems().clear();
    	 try {
    		 java.sql.Connection con = DBConnecter.connectDB();
    		 String quere =" SELECT * FROM prouct WHERE  insuranceExpirationDate <= CURDATE()";
 			java.sql.Statement stm = con.createStatement();
 			ResultSet rs = stm.executeQuery(quere);
 			while(rs.next()) {
		         int pid = rs.getInt("Barcode");
			     Date mandate = rs.getDate("manufacturingDate");
			     Date expired = rs.getDate("insuranceExpirationDate");
			     int amount = rs.getInt("amount");
				String pname = rs.getString("pname");
				DisposalTable.getItems().add(new Product(pid, pname, amount, expired, mandate));	
				System.out.printf(pid + " " +  pname + " " + mandate + " " + expired + " " + amount);
			}
 			rs.close();
 			stm.close();
 			
 			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
     }else {
    	 DisposalTable.getItems().clear();
    	 TableDisposalloud();
     }
    }
   
    @FXML
    void disposalBtAction(ActionEvent event) {
    	Product p	= DisposalTable.getSelectionModel().getSelectedItem();
    	if(!TFAmount.equals("")) {
    		int anount =p.getAmount();
    		int Id = p.getBarcode();
    		String Id2 = String.valueOf(Id);
    		System.out.println("iiiiid"+Id +"\n");
    		System.out.println("amount" + anount);
    		try {
    			 java.sql.Connection con = DBConnecter.connectDB();
    			System.out.println("conection Start");
    			String quere =" SET SQL_SAFE_UPDATES = 0";
    			java.sql.Statement stm = con.createStatement();
    			ResultSet rs = stm.executeQuery(quere);
    			
    			rs.close();
    			stm.close();
    			int tfamount = Integer.parseInt(TFAmount.getText());
                int result = anount - tfamount;
                String res=String.valueOf(result);
                System.out.println("tfamount"+tfamount);
                System.out.println(result);
             //   update  prouct set amount = 120 where Barcode = 1005;
    			String quere11 =" update  prouct set amount =? where Barcode =? ";
    			PreparedStatement statementUpdate = (PreparedStatement) DBConnecter.connectDB().prepareStatement(quere11);
    			statementUpdate.setString(1, res);
    			statementUpdate.setString(2, Id2);
    			statementUpdate.executeUpdate();
    			statementUpdate.close();
    			massegeLabel.setText("Disposed Successfully");
    			TFAmount.clear();
    			
    			String quere3 =" SET SQL_SAFE_UPDATES = 1";
    			java.sql.Statement stm3 = con.createStatement();
    			ResultSet rs3 = stm3.executeQuery(quere3);
    			
    			
    			rs3.close();
    			stm3.close();
    			
    			con.close();
    			 if(expiredCheckBox.isSelected()) {
    		    	 DisposalTable.getItems().clear();
    		    	 try {
    		    		 java.sql.Connection con1 = DBConnecter.connectDB();
    		    		 String quere1 =" SELECT * FROM prouct WHERE  insuranceExpirationDate <= CURDATE()";
    		 			java.sql.Statement stm1 = con1.createStatement();
    		 			ResultSet rs1 = stm1.executeQuery(quere1);
    		 			while(rs1.next()) {
    				         int pid = rs1.getInt("Barcode");
    					     Date mandate = rs1.getDate("manufacturingDate");
    					     Date expired = rs1.getDate("insuranceExpirationDate");
    					     int amount = rs1.getInt("amount");
    						String pname = rs1.getString("pname");
    						DisposalTable.getItems().add(new Product(pid, pname, amount, expired, mandate));	
    						System.out.printf(pid + " " +  pname + " " + mandate + " " + expired + " " + amount);
    					}
    		 			rs.close();
    		 			stm.close();
    		 			
    		 			con.close();
    				} catch (Exception e) {
    					// TODO: handle exception
    				}
    		     }else {
    		    	 DisposalTable.getItems().clear();
    		    	 TableDisposalloud();
    		     }
    			

    			
    		} catch (ClassNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	
    		
    	}else {
    		System.out.println("fill the amount textfiled");
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		ProductName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		Amount.setCellValueFactory(new PropertyValueFactory<Product, Integer>("amount"));
		ProductId.setCellValueFactory(new PropertyValueFactory<Product , Integer>("barcode"));
		PriductionDate.setCellValueFactory(new PropertyValueFactory<Product, Date>("manufacturingDate"));
		ExpiredDate.setCellValueFactory(new PropertyValueFactory<Product, Date>("expiredDate"));
		TableDisposalloud();
	}
	
	
	 public void TableDisposalloud () {
	    	try {
	    		java.sql.Connection con = DBConnecter.connectDB();
				System.out.println("conection Start");
				String quere ="select Barcode , pname, manufacturingDate , insuranceExpirationDate , amount from prouct";
				java.sql.Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(quere);
				while(rs.next()) {
			         int pid = rs.getInt("Barcode");
				     Date mandate = rs.getDate("manufacturingDate");
				     Date expired = rs.getDate("insuranceExpirationDate");
				     int amount = rs.getInt("amount");
					String pname = rs.getString("pname");
					DisposalTable.getItems().add(new Product(pid, pname, amount, expired, mandate));	
					System.out.printf(pid + " " +  pname + " " + mandate + " " + expired + " " + amount);
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
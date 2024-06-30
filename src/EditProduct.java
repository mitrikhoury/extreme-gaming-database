

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditProduct implements Initializable{

	private BatchStock bat2 ;
    

	public void setBat(BatchStock bat) {
		this.bat2 = bat;
	}
	
    @FXML
    private TextField Amount;

    @FXML
    private TextField EXpiryDate;

    @FXML
    private TextField ProductionDate;

    @FXML
    private TextField TFName;

    @FXML
    private TextField TFPrice;

    @FXML
    private Button Update;
    
    @FXML
    private Label labelUpdate;


    @FXML
    void ActionUpdate(ActionEvent event) {
    	int Id = BatchStock.product.getBarcode();
    	String id =String.valueOf(Id);
    	if(!ProductionDate.equals("")&& !TFPrice.equals("")  && !Amount.equals("") && !TFName.equals("") && !EXpiryDate.equals("") ) {
    		int x1 = BatchStock.catId; //id category
    		try {
				
    			 java.sql.Connection con = DBConnecter.connectDB();
    			System.out.println("conection Start");
    			String quere =" SET SQL_SAFE_UPDATES = 0";  // to update 
    			java.sql.Statement stm = con.createStatement();
    			ResultSet rs = stm.executeQuery(quere);
    			
    			rs.close();
    			stm.close();
    			
    		    String name = TFName.getText();
    		    String quere11 =" update  prouct set pname =? where Barcode =? ";
    			PreparedStatement statementUpdate = (PreparedStatement) DBConnecter.connectDB().prepareStatement(quere11);
    			statementUpdate.setString(1, name);
    			statementUpdate.setString(2, id);
    			statementUpdate.executeUpdate();
    			statementUpdate.close();          // edit the name
    			
    			
    			String price =TFPrice.getText();
    			 String quere2 =" update  prouct set pprice =? where Barcode =? ";
    			 PreparedStatement statementUpdate2 = (PreparedStatement) DBConnecter.connectDB().prepareStatement(quere2);
    			 statementUpdate2.setString(1, price);
    			 statementUpdate2.setString(2, id);
    			 statementUpdate2.executeUpdate();
    			 statementUpdate2.close();  //edit the price
    			 
    			 String amount =Amount.getText();
    			 String quere3 =" update  prouct set amount =? where Barcode =? ";
    			 PreparedStatement statementUpdate3 = (PreparedStatement) DBConnecter.connectDB().prepareStatement(quere3);
    			 statementUpdate3.setString(1, amount);
    			 statementUpdate3.setString(2, id);
    			 statementUpdate3.executeUpdate();
    			 statementUpdate3.close();  //edit the Amount
    			 
    			 String production =ProductionDate.getText();
    			 String quere4 =" update  prouct set manufacturingDate =? where Barcode =? ";
    			 PreparedStatement statementUpdate4 = (PreparedStatement) DBConnecter.connectDB().prepareStatement(quere4);
    			 statementUpdate4.setString(1, production);
    			 statementUpdate4.setString(2, id);
    			 statementUpdate4.executeUpdate();
    			 statementUpdate4.close();  //edit the production date
    			 
    			 String expiry =EXpiryDate.getText();
    			 String quere5 =" update  prouct set insuranceExpirationDate =? where Barcode =? ";
    			 PreparedStatement statementUpdate5 = (PreparedStatement) DBConnecter.connectDB().prepareStatement(quere5);
    			 statementUpdate5.setString(1, expiry);
    			 statementUpdate5.setString(2, id);
    			 statementUpdate5.executeUpdate();
    			 statementUpdate5.close();  //edit the expired date
    			 
    			 
    			 String quere6 =" SET SQL_SAFE_UPDATES = 1";
     			java.sql.Statement stm3 = con.createStatement();
     			ResultSet rs3 = stm3.executeQuery(quere6);  // close the update
     			rs3.close();
     			stm3.close();
     			
     			con.close();
     			System.out.println("connetion end");
     			bat2.getBatcTable().getItems().clear();
     			bat2.Tablebatch();
     			labelUpdate.setText("Updated Successfully");
     			EXpiryDate.clear();
     			ProductionDate.clear();
     			Amount.clear();
     			TFPrice.clear();
     			TFName.clear();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
    		
    	}else {
    		System.out.println("invalid input");
    	}
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
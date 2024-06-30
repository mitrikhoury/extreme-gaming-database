

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.jar.Attributes.Name;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ShowCustomerContactController implements Initializable{
	Customer1 cust = null;
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	ArrayList<Customer1> data = null;
	private ObservableList<Customer1> dataList = FXCollections.observableArrayList();
    
	  @FXML
	    private Label nameLbl;
    @FXML
    private TextArea ContantTA;

    @FXML
    private ImageView addImgBT;

    @FXML
    private TextField phoneTF;

    @FXML
    private TextArea showTXt;

    @FXML
    void addContant(MouseEvent event) {
    	if (cust!=null&&ContantTA.getText()!=""&&phoneTF.getText()!="") {
        if(phoneTF.getText()!=""&&ContantTA.getText()!=""&&cust.getCustomerID()!=0) {
//        	String sql ="UPDATE Employees SET contact =? WHERE eid =?";
        	String sql ="update customers set Phone=? and Email=? where customerID=?;";
        	try {
    			conn = DBConnecter.connectDB();
    			pst = conn.prepareStatement(sql);
    			pst.setInt(1, Integer.parseInt(phoneTF.getText().trim()));
    			pst.setString(2, ContantTA.getText().trim());
    			pst.setInt(3, cust.getCustomerID());
    			pst.execute();
    			ContantTA.clear();
    			conn.close();
    			showTXt.appendText(cust.getPhone()+"\n"+cust.getEmail());
    		} catch (ClassNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	}
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		helper();
		
	}
	private void helper() {
		if(cust!=null&&(cust.getPhone()!=0||cust.getEmail()!=null)) {
			showTXt.appendText(cust.getPhone()+"\n"+cust.getEmail());
			nameLbl.setText(cust.getCname());
		}
		else {
			showTXt.setText("");
		}
	}
	public void setCustomer(Customer1 customer) {
		this.cust=customer;
		helper();
		
	}

}



import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AddProduct implements Initializable {
     
     private BatchStock bat ;
     

	public void setBat(BatchStock bat) {
		this.bat = bat;
	}

	@FXML
    private Button AddNewProduct;

//    @FXML
//    private TableColumn<Product, Integer> Amount;

//    @FXML
//    private TableView<Product> EditTable;

//    @FXML
//    private TableColumn<Product, Date> ExpiryDate;

    @FXML
    private TextField ExpiryTf;

//    @FXML
//    private TableColumn<Product, Integer> ProductId;
//
//    @FXML
//    private TableColumn<Product, String> ProductName;

    @FXML
    private TextField TFName;

    @FXML
    private TextField TfAmount;

    @FXML
    private TextField TfId;

    @FXML
    private TextField TfPrice;

    @FXML
    private TextField TfProduction;

    @FXML
    private ImageView pensel;

    @FXML
    private Label labelInsert;

    
//    @FXML
//    private TableColumn<Product, Double> price;
//
//    @FXML
//    private TableColumn<Product, Date> productionDate;
   // INSERT INTO product (pid, pname, pprice, amount, activationState, expiredDate, manufacturingDate, IdCategories) VALUES (1030, 'cd gta', 350.99, 100, true, '2019-08-30', '2023-06-01', 2);
    @FXML
    void AddProduct(ActionEvent event) {
          //.executeUpdate(SQL);
    	if(!TfProduction.equals("")&& !TfPrice.equals("") && !TfId.equals("") && !TfAmount.equals("") && !TFName.equals("") && !ExpiryTf.equals("") ) {
    		int x=BatchStock.catId;
    		System.out.println(x + "eeeeeeeeeeeeeeeeeeeeeeee");
    		try {
    			java.sql.Connection con1 = DBConnecter.connectDB();
	    		 System.out.println("mitriiiii");
	    		 String quere1 =" INSERT INTO prouct (Barcode, pname, pprice, amount, insuranceExpirationDate, manufacturingDate, IdCategories) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    		 PreparedStatement statementUpdate = (PreparedStatement) DBConnecter.connectDB().prepareStatement(quere1);
	    		 statementUpdate.setString(1, TfId.getText());
	    		 statementUpdate.setString(2, TFName.getText());
	    		 statementUpdate.setString(3, TfPrice.getText());
	    		 statementUpdate.setString(4, TfAmount.getText());
	    		 statementUpdate.setString(5, ExpiryTf.getText());
	    		 statementUpdate.setString(6, TfProduction.getText());
	    		 statementUpdate.setString(7, String.valueOf(x));
	    		 statementUpdate.executeUpdate();
	    			statementUpdate.close();
	    			
	    			labelInsert.setText("Insert Successfully");
	    			
	    			String quere2 ="select sum(amount) from prouct p where  IdCategories ="+bat.catId;
	    			java.sql.Statement stm2 = con1.createStatement();
	    			ResultSet rs2 = stm2.executeQuery(quere2);
	    			int totall = 0 ;
	    			while(rs2.next()) {
	    				 totall = rs2.getInt("sum(amount)");
	    				
	    			}
	    			
	    			String totalSum =String.valueOf(totall);
	    		bat.getLabelCount().setText(totalSum);
	    			
	    			rs2.close();
	    			stm2.close();
	    			
	 			con1.close();
	 			//new Product(id, name, amount, expired, man, price)
	 			bat.getBatcTable().getItems().add(new Product(Integer.parseInt(TfId.getText()),TFName.getText() , Integer.parseInt(TfAmount.getText()),Date.valueOf(ExpiryTf.getText()) , Date.valueOf(TfProduction.getText()), Double.parseDouble(TfPrice.getText())) );
	 			TfId.clear();
    			TFName.clear();
    			TfPrice.clear();
    			TfAmount.clear();
    			ExpiryTf.clear();
    			TfProduction.clear();
    			
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
    	}
    	
    	
    }

    @FXML
    void actionEdit(MouseEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
//		Product x =BatchStock.product;
//		System.out.println(x.getAmount());
//		System.out.println(x.getPid());
//		System.out.println(x.getPname());
//		System.out.println(x.getManufacturingDate()+"mannn");
//		System.out.println(x.getPprice());
//		
//		System.out.println(x.getPname());
		
//		productionDate.setCellValueFactory(new PropertyValueFactory<Product , Date>("manufacturingDate"));
//		ExpiryDate.setCellValueFactory(new PropertyValueFactory<Product, Date>("expiredDate"));
//		Amount.setCellValueFactory(new PropertyValueFactory<Product, Integer>("amount"));
//		ProductId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("Pid"));
//		price.setCellValueFactory(new PropertyValueFactory<Product, Double>("pprice"));
//		ProductName.setCellValueFactory(new PropertyValueFactory<Product, String>("pname"));
//		EditTable.getItems().add(BatchStock.product);
	}

}
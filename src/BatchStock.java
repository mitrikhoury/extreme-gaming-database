

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BatchStock implements Initializable{

    @FXML
    private TableColumn<Product, Integer> Amount;

    @FXML
    private TableColumn<Product, Integer> productId;

    @FXML
    private TableColumn<Product, Double> Price;

    @FXML
    private TableColumn<Product, String> ProductName;

    
    
    @FXML
    private TableColumn<Product, Date> ExpiryDate;

    @FXML
    private TableView<Product> batcTable;

    
    
    
    public void setBatcTable(TableView<Product> batcTable) {
		this.batcTable = batcTable;
	}


	public  TableView<Product> getBatcTable() {
		return batcTable;
	}


	
	 @FXML
	    void actionEdit(MouseEvent event) throws IOException {
		 product = batcTable.getSelectionModel().getSelectedItem();
		 if( product != null) {
			 FXMLLoader fxprodEdit = new FXMLLoader(getClass().getResource("Editp.fxml"));
			 
			 Parent root = fxprodEdit.load();
		    	EditProduct e = fxprodEdit.getController();
		    	e.setBat(this);
				Scene s7 = null;
				Stage st = new Stage();
				
					s7 = new Scene(root);
				
				
				st.setScene(s7);
				st.show();
		 }else {
			 System.out.println("plz select item");
		 }
	    }

	@FXML
    private DatePicker celender;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TableColumn<Product, Date> productionDate;
    
    
    @FXML
    private Label labelCount;

    public Label getLabelCount() {
		return labelCount;
	}


	public void setLabelCount(Label labelCount) {
		this.labelCount = labelCount;
	}

	@FXML
    private Label labelProduct;
   
   public static Product product;
    
    @FXML
    void avtionAdd(MouseEvent event) throws IOException {
    	// product = batcTable.getSelectionModel().getSelectedItem();
        
    	// if(product != null) {
    	FXMLLoader fxproduct = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
    	Parent root = fxproduct.load();
    	AddProduct c = fxproduct.getController();
    	c.setBat(this);
		Scene s4 = null;
		Stage st = new Stage();
		
			s4 = new Scene(root);
		
		
		st.setScene(s4);
		st.show();
//    	 }else {
//    		System.out.println("plz select item"); 
//    	 }
    }
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		productionDate.setCellValueFactory(new PropertyValueFactory<Product, Date>("manufacturingDate"));
		ExpiryDate.setCellValueFactory(new PropertyValueFactory<Product, Date>("expiredDate"));
		Amount.setCellValueFactory(new PropertyValueFactory<Product, Integer>("amount"));
		productId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("barcode"));
		Price.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
		ProductName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		labelProduct.setText(Storge.pro.getName());
		Tablebatch ();
	}
    
	
//	select p.pname , p.manufacturingDate , p.expiredDate , p.amount from product p
//	  where IdCategories =1;
	public static int catId ;
	public void Tablebatch () {
    	try {
    		 catId = Storge.pro.getID();  // get the of the selected item
    		 java.sql.Connection con = DBConnecter.connectDB();
			System.out.println("conection Start");
			String quere ="select p.Barcode ,p.pname , p.pprice , p.manufacturingDate , p.insuranceExpirationDate , p.amount from prouct p  where IdCategories ="+catId;
			java.sql.Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(quere);
			while(rs.next()) {
				int id = rs.getInt("Barcode");
				String name =rs.getString("pname");
				double price =rs.getDouble("pprice");
		         Date man = rs.getDate("manufacturingDate");
				Date expired = rs.getDate("insuranceExpirationDate");
				int amount= rs.getInt("amount");
				System.out.println(id+" "+name+" "+man+ ""+ price + " " +  expired + " " + amount  +"1");
				batcTable.getItems().add(new Product(id, name, amount, expired, man, price));	
				System.out.println(id+" "+name+" "+man+ ""+ price + " " +  expired + " " + amount  );
			}
			
			rs.close();
			stm.close();
			//select sum(amount) from product p where  IdCategories =1;
			String quere2 ="select sum(amount) from prouct p where  IdCategories ="+catId;
			java.sql.Statement stm2 = con.createStatement();
			ResultSet rs2 = stm2.executeQuery(quere2);
			int totall = 0 ;
			while(rs2.next()) {
				 totall = rs2.getInt("sum(amount)");
				
			}
			
			String totalSum =String.valueOf(totall);
			labelCount.setText(totalSum);
			
			rs2.close();
			stm2.close();
			con.close();
			System.out.println("conection end");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//payingTable.getSelectionModel().getSelectedItem();
    }
	
	
}
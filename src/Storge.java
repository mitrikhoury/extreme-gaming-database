

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Storge implements Initializable{
	 

	    @FXML
	    private TableColumn<Categories, Integer> CategoriesId;

	    @FXML
	    private TableColumn<Categories, String> CategoriesName;

	    @FXML
	    private TableView<Categories> StorgeTable;

	    @FXML
	    private Button showSold;

	    @FXML
	    private Button showStack;

	    

    @FXML
    void ShoeSoldAction(ActionEvent event) {
    	 pro = StorgeTable.getSelectionModel().getSelectedItem();
    	 if(pro != null) {
    		 FXMLLoader fxmlsold = new FXMLLoader(getClass().getResource("sold.fxml"));
    			Scene s5 = null;
    			Stage st = new Stage();
    			try {
    				s5 = new Scene(fxmlsold.load());
    			} catch (IOException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
    			
    			st.setScene(s5);
    			st.show();
    	 }else {
     		System.out.println("plz select item"); 
     	 }
    }
   public static Categories pro;
   @FXML
   void showStackAction(ActionEvent event) {
    	 pro = StorgeTable.getSelectionModel().getSelectedItem();
    	 if(pro != null) {
    	FXMLLoader fxmlBatch = new FXMLLoader(getClass().getResource("batch.fxml"));
		Scene s4 = null;
		Stage st = new Stage();
		try {
			s4 = new Scene(fxmlBatch.load());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		st.setScene(s4);
		st.show();
    	 }else {
    		System.out.println("plz select item"); 
    	 }
     }
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		CategoriesName.setCellValueFactory(new PropertyValueFactory<Categories, String>("name"));
		CategoriesId.setCellValueFactory(new PropertyValueFactory<Categories, Integer>("ID"));
		
		TableStorge();
		
	}
	 public void TableStorge () {
	    	try {
	    		 java.sql.Connection con = DBConnecter.connectDB();
				System.out.println("conection Start");
				String quere ="select * from  categories";
				java.sql.Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(quere);
				while(rs.next()) {
			         int id = rs.getInt("id");
					String name = rs.getString("cname");
					
					StorgeTable.getItems().add(new Categories(id, name));
					System.out.println(id + name);
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
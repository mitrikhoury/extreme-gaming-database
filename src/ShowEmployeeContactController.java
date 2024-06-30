

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ShowEmployeeContactController implements Initializable{
	Employees employee = null;
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	ArrayList<Employees> data = null;
	private ObservableList<Employees> dataList = FXCollections.observableArrayList();
    @FXML
    private TextArea ContantTA;

    @FXML
    private ImageView addImgBT;
    @FXML
    private TextArea showTXt;
    @FXML
    private Label nameLabel;

    @FXML
    void addContant(MouseEvent event) {
    	if (employee!=null&&ContantTA.getText()!="") {
    	String contactTxt= ContantTA.getText().trim();
    	String sql ="UPDATE Employees SET contact =? WHERE eid =?";
    	try {
			conn = DBConnecter.connectDB();
			pst = conn.prepareStatement(sql);
			pst.setString(1, contactTxt);
			pst.setInt(2, employee.getEid());
			pst.execute();
			ContantTA.clear();
			conn.close();
			showTXt.appendText(employee.getContact());
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		coolHle();
		
	}
	private void coolHle () {
		if(employee!=null) {
			showTXt.appendText(employee.getContact());
			nameLabel.setText(employee.getEname());
		}
		else {
			showTXt.setText("");
		}
	}
	public void setEmployee(Employees employee) {
		this.employee=employee;
		coolHle();
	}
	
}

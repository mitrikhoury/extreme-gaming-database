

import java.io.IOException;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeController implements Initializable {
	Connection conn = null;
	ArrayList<Employees> data;
	 ObservableList<Employees> dataList;
	PreparedStatement pst = null;
	Employees empl = null;
	@FXML
	private VBox EmployeesVb;

	@FXML
	private TableColumn<Employees, Date> hirDaycol;

	@FXML
	private TableColumn<Employees, Integer> idCol;

	@FXML
	private Button insertBt;

	@FXML
	private CheckBox isActiveRB;

	@FXML
	private TableColumn<Employees, Boolean> isActivecol;

	@FXML
	private CheckBox isManagerRB;

	@FXML
	private TableColumn<Employees, String> jobTitileCol;

	@FXML
	private TableColumn<Employees, String> namecol;

	@FXML
	private TableColumn<Employees, Double> salaryCol;

	@FXML
	private Button showConatactInfoBt;
	@FXML
	private TableColumn<Employees, String> VecationCol;

	@FXML
	private TableColumn<Employees, Integer> depCol;

	@FXML
	private TableView<Employees> tableViewCust;
	@FXML
	private TableColumn<Employees, Integer> managerid;

	@FXML
	void insertNewRow(ActionEvent event) {
		try {
			
			
				Stage stagee = new Stage();
//				Pane pane = (Pane) FXMLLoader.load(getClass().getResource("InsertEmployee.fxml"));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("InsertEmployeeFXXX.fxml"));
				Pane pane = loader.load();

				if (empl != null) {
				InsertEmployeeController controller = loader.getController(); 
				controller.setEmployee(empl);
				}
				Scene scene = new Scene(pane, 600, 400);
				stagee.setScene(scene);
				stagee.show();
			
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Open Error");
			alert.setContentText("Error:" + e.getMessage());
			e.printStackTrace();
		}
	}

	@FXML
	void rowViewCust(MouseEvent event) {
		empl = tableViewCust.getSelectionModel().getSelectedItem();
		int id = empl.getEid();
		String SQL = "select * from Employees E where eid="+id;
		System.out.println(SQL);
		try {
			Connection con = DBConnecter.connectDB();
			Statement state = (Statement) con.createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				boolean act = false;
				if (rs.getString(5).trim().equals("1"))
					act = true;
				empl= new Employees(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),
						rs.getString(4), act, rs.getString(6), rs.getDate(7), Integer.parseInt(rs.getString(8)),
						Double.parseDouble(rs.getString(9)), Integer.parseInt(rs.getString(10)), rs.getDate(11),rs.getString(12));
			}
//			dataList = FXCollections.observableArrayList(data);
			rs.close();
//			state.close();
			con.close();
			System.out.println("Number of data is " + data.size());

		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
//		insertNewRow(new ActionEvent());
	}

	@FXML
	void showAcitveOnly(ActionEvent event) {
		if (isActiveRB.isSelected() && !isManagerRB.isSelected()) {
			getData(false, true);
			showDate();
		} else if (isActiveRB.isSelected() && isManagerRB.isSelected()) {
			getData(false, false);
			showDate();
		} else if (!isActiveRB.isSelected() && isManagerRB.isSelected()) {
			getData(true, false);
			showDate();
		} else {
			getData(true, true);
			showDate();
		}
	}

	@FXML
	void showConatactInfo(ActionEvent event) {

		try {
			if (empl != null) {
				Stage stage = new Stage();
//			Pane pane = (Pane) FXMLLoader.load(getClass().getResource("showContantFX.fxml"));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("showContantFX.fxml"));
				Pane pane = loader.load();

				ShowEmployeeContactController controller = loader.getController();
				controller.setEmployee(empl);

				Scene scene = new Scene(pane, 674, 447);
				stage.setScene(scene);
				stage.show();
			}
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Open Error");
			alert.setContentText("Error:" + e.getMessage());
			e.printStackTrace();
		}

	}

	@FXML
	void showManagerOnly(ActionEvent event) {
		if (isActiveRB.isSelected() && !isManagerRB.isSelected()) {
			getData(false, true);
			showDate();
		} else if (isActiveRB.isSelected() && isManagerRB.isSelected()) {
			getData(false, false);
			showDate();
		} else if (!isActiveRB.isSelected() && isManagerRB.isSelected()) {
			getData(true, false);
			showDate();
		} else {
			getData(true, true);
			showDate();
		}

	}

	private ObservableList<Employees> getData(boolean Active, boolean mangaer) {
		data = new ArrayList<>();
		String SQL = "select * from Employees";
		System.out.println(SQL);
		try {
			Connection con = DBConnecter.connectDB();
			Statement state = (Statement) con.createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				boolean act = false;
				if (rs.getString(5).trim().equals("1"))
					act = true;
				Employees employee = new Employees(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),
						rs.getString(4), act, rs.getString(6), rs.getDate(7), Integer.parseInt(rs.getString(8)),
						Double.parseDouble(rs.getString(9)), Integer.parseInt(rs.getString(10)), rs.getDate(11),rs.getString(12));
				if (!Active && mangaer) {
					if (act)
						data.add(employee); // dataList.add(Employees);
				} else if (Active && !mangaer) {
					if (employee.getManagerID() == employee.getEid())
						data.add(employee); // dataList.add(Employees);

				} else if (!Active && !mangaer) {
					if (employee.getManagerID() == employee.getEid() && act)
						data.add(employee); // dataList.add(Employees);

				} else
					data.add(employee); // dataList.add(Employees);

			}
			dataList = FXCollections.observableArrayList(data);
			rs.close();
//			state.close();
			DBConnecter.connectDB().close();
			System.out.println("Number of data is " + data.size());

		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataList;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dataList = getData(true, true);
		showDate();

	}

	private void showDate() {
		data = new ArrayList<>();
		tableViewCust.setEditable(true);

		idCol.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("eid"));
		depCol.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("did"));
		namecol.setCellValueFactory(new PropertyValueFactory<Employees, String>("ename"));
		jobTitileCol.setCellValueFactory(new PropertyValueFactory<Employees, String>("job_title"));
		salaryCol.setCellValueFactory(new PropertyValueFactory<Employees, Double>("salary"));
		VecationCol.setCellValueFactory(new PropertyValueFactory<Employees, String>("vacation_days"));
		hirDaycol.setCellValueFactory(new PropertyValueFactory<Employees, Date>("hire_date"));
		isActivecol.setCellValueFactory(new PropertyValueFactory<Employees, Boolean>("E_active"));
		managerid.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("managerID"));
		tableViewCust.getItems().clear();
		tableViewCust.setItems(dataList);
	}

}

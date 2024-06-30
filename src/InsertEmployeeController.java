

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class InsertEmployeeController implements Initializable {
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	Employees employee = null;
	ArrayList<Employees> data = null;
	private ObservableList<Employees> dataList = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Employees, String> EmailCol;
	@FXML
	private ComboBox<String> vacationCombo;
	@FXML
	private TextField eidTF;

	@FXML
	private ImageView addimage;

	@FXML
	private DatePicker birthdatyCalender;

	@FXML
	private TextField contantTA;

	@FXML
	private TableColumn<Employees, Integer> depCol;

	@FXML
	private TextField depTF;
	@FXML
	private TableColumn<Employees, String> vacationDayCol;

	@FXML
	private TableColumn<Employees, Integer> idCol;

	@FXML
	private TableColumn<Employees, Boolean> isActivecol;

	@FXML
	private TextField jobTitileTF;
	@FXML
	private TextField managerTF;

	@FXML
	private TableColumn<Employees, String> jobtitileCol;

	@FXML
	private TextField nameTF;

	@FXML
	private TableColumn<Employees, String> namecol;

	@FXML
	private TextField passwdTF;

	@FXML
	private TableColumn<Employees, Double> salaryCol;
	@FXML
	private ImageView deleteImage;
	@FXML
	private TextField salaryTF;

	@FXML
	private TableView<Employees> tableViewCust;

	@FXML
	void deleteRow(MouseEvent event) {
		if (eidTF.getText() != "") {

			String sql = "delete from Employees where eid = ?";
			System.out.println(sql);
			try {
				conn = DBConnecter.connectDB();
				pst = conn.prepareStatement(sql);
				pst.setInt(1, Integer.parseInt(eidTF.getText().trim()));
				pst.execute();
//				JOptionPane.showMessageDialog(null, "Customer " + eidTF.getText().trim() + " Deleted Successfully");
//     			clickshowdata(event);
				for (Employees item : dataList) {
					if (item.getEid() == Integer.parseInt(eidTF.getText().trim())) {
						dataList.remove(item);
						break;
					}
				}

				eidTF.clear();
				conn.close();
				showDate();
			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, e);
			}
		} else if (employee != null) {
			int eid = employee.getEid();

			String sql = "delete from Employees where eid = ?";
			try {
				conn =DBConnecter.connectDB();
				pst = conn.prepareStatement(sql);
				pst.setInt(1, eid);
				pst.execute();
//				JOptionPane.showMessageDialog(null, "Customer " + eid + " Deleted Successfully");
//     			clickshowdata(event);
//     			deleteNum.clear();

				dataList.remove(employee);
				employee = null;
				eidTF.clear();
				conn.close();
				showDate();
			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, e);
				e.printStackTrace();
			}

		}
	}

	
	@FXML
	void addEmployee(MouseEvent event) {
		if (eidTF.getText() != "" && vacationCombo.getValue() != null && nameTF.getText() != ""
				&& contantTA.getText() != "" && jobTitileTF.getText() != "" && birthdatyCalender.getValue() != null
				&& managerTF.getText() != "" && salaryTF.getText() != "" && depTF.getText() != ""
				&& passwdTF.getText() != "") {
			int eid = Integer.parseInt(eidTF.getText().trim());
			String vacation_days = vacationCombo.getValue();
//					getSelectionModel().getSelectedItem();
			String ename = nameTF.getText().trim();
			String contact = contantTA.getText().trim();
			boolean E_active = true;

			String job_title = jobTitileTF.getText().trim();

			Date birthday = Date.valueOf(birthdatyCalender.getValue());
			int managerID = Integer.parseInt(managerTF.getText().trim());
			double salary = Double.parseDouble(salaryTF.getText().trim());
			int did = Integer.parseInt(depTF.getText().trim());
			Date hire_date = java.sql.Date.valueOf(LocalDate.now());
			
			String passwd = passwdTF.getText().trim();
//		ArrayList<Employees> list = new ArrayList<>(); 
			Employees em = new Employees(eid, vacation_days, ename, contact, E_active, job_title, birthday, managerID,
					salary, did, hire_date, passwd);
//		dataList.add(em);
			String sql = "insert into Employees(eid, vacation_days,ename,contact,E_active,job_title,birthday,managerID,salary,did,hire_date,passwd) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
			System.out.println(sql);
			try {
				conn = DBConnecter.connectDB();
				pst = conn.prepareStatement(sql);
				pst.setInt(1, eid);
				pst.setString(2, vacation_days);
				pst.setString(3, ename);
				pst.setString(4, contact);
				pst.setBoolean(5, E_active);
				pst.setString(6, job_title);
				pst.setDate(7, birthday);
				pst.setInt(8, managerID);
				pst.setDouble(9, salary);
				pst.setInt(10, did);
				pst.setDate(11, hire_date);
				pst.setString(12, passwd);

				pst.execute();
				eidTF.clear();
				vacationCombo.setSelectionModel(null);
				nameTF.clear();
//				contantTA.clear();
				jobTitileTF.clear();
				birthdatyCalender.setValue(null);
				managerTF.clear();
				salaryTF.clear();
				depTF.clear();
				passwdTF.clear();

//			JOptionPane.showMessageDialog(null, "Succes Insertion");
				conn.close();

				dataList.add(em);
				showDate();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}

	@FXML
	void rowViewCust(MouseEvent event) {
		Employees emp = tableViewCust.getSelectionModel().getSelectedItem();
		if (emp != null) {
			setEmployee(emp);
		}
	}

	private void showDate() {
//		data = new ArrayList<>();
//		data.add(employee);
//		if (employee != null)
//			dataList = FXCollections.observableArrayList(data);
		if (employee != null)
			dataList.add(employee);
		tableViewCust.setEditable(true);

		idCol.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("eid"));
		EmailCol.setCellValueFactory(new PropertyValueFactory<Employees, String>("contact"));
		namecol.setCellValueFactory(new PropertyValueFactory<Employees, String>("ename"));
		depCol.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("did"));

		isActivecol.setCellValueFactory(new PropertyValueFactory<Employees, Boolean>("E_active"));
		jobtitileCol.setCellValueFactory(new PropertyValueFactory<Employees, String>("job_title"));
		salaryCol.setCellValueFactory(new PropertyValueFactory<Employees, Double>("salary"));

		vacationDayCol.setCellValueFactory(new PropertyValueFactory<Employees, String>("vacation_days"));

		tableViewCust.getItems().clear();
		tableViewCust.setItems(dataList);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		vacationCombo.getItems().addAll("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");

	}

	public void setEmployee(Employees employee) {
		this.employee = employee;
		showDate();

	}

}

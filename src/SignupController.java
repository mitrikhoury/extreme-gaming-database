import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignupController implements Initializable {

	@FXML
	private Button addNewUser;

	@FXML
	private TextField name;

	@FXML
	private Button cancel;

	@FXML
	private DatePicker dateOB;

	@FXML
	private ChoiceBox<String> dept;

	@FXML
	private TextField email;

	@FXML
	private ChoiceBox<String> jobTitle;

	@FXML
	private PasswordField passwd;

	@FXML
	private TextField salary;

	@FXML
	private ChoiceBox<String> vactionD;

	private MangerConfirmationController mangerConfirmationController;

	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		vactionD.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
		jobTitle.getItems().addAll("Employee", "Manager");
		setupDept();
	}

	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static boolean isValidDate(String dateStr) {
		dateFormat.setLenient(false); // Disable lenient parsing

		try {
			Date date = Date.valueOf(dateStr); // Try to parse the date
			String formattedDate = dateFormat.format(date);// Format the parsed date
			return dateStr.equals(formattedDate); // Compare the original and formatted dates
		} catch (IllegalArgumentException e) {
			return false; // Invalid date format
		}
	}

	@FXML
	void addUser(ActionEvent event) {
		if (vactionD.getValue() == null || !isValidEmail(email.getText()) || jobTitle.getValue() == null
				|| !isValidDate(dateOB.getValue().toString()) || salary.getText().isEmpty()
				|| Integer.parseInt(salary.getText()) < 0 || dept.getValue() == null || passwd.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Invalid credentials ");
			alert.showAndWait();
		} else {
			try {
				Connection con = DBConnecter.connectDB();
				String departmentInfo = dept.getValue();
				String[] department = departmentInfo.split(",");
				String query1 = "select did from department where dlocation = '" + department[0] + "' and dname = '"
						+ department[1] + "'";
				Statement stmt1 = con.createStatement();
				ResultSet rs = stmt1.executeQuery(query1);
				int did = 0;
				while (rs.next()) {
					did = rs.getInt("did");
				}
				LocalDate currentDate = LocalDate.now();
				String values = vactionD.getValue() + "','" + name.getText() + "','" + email.getText() + "',true,'" + jobTitle.getValue()
						+ "',CURDATE()" + "," + mangerConfirmationController.getManagerID() + ","
						+ Double.parseDouble(salary.getText()) + "," + did + ",'" + currentDate.toString() + "','"
						+ passwd.getText() + "')";
				String query2 = "INSERT INTO Employees (vacation_days, ename,contact, E_active, job_title, birthday,managerID, salary, did, hire_date, passwd) VALUES ('"
						+ values;
				Statement stmt2 = con.createStatement();
				stmt2.executeUpdate(query2);
				con.close();
				vactionD.setValue(null);
				email.clear();
				jobTitle.setValue(null);
				dateOB.setValue(null);
				salary.clear();
				dept.setValue(null);
				passwd.clear();
				name.clear();
				String query3 = "select max(eid) as neweid from Employees";
				Statement stmt3 = con.createStatement();
				ResultSet rs2 = stmt3.executeQuery(query3);
				int newEid = 0;
				while (rs.next()) {
					newEid = rs.getInt("neweid");
				}
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setContentText("Employee has been added the new employee id is " + newEid);
				alert.showAndWait();
				Parent signUpRoot = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
				Stage currentStage = (Stage) addNewUser.getScene().getWindow();
				Scene signUpScene = new Scene(signUpRoot);
				currentStage.setScene(signUpScene);
				currentStage.show();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@FXML
	void cancel(ActionEvent event) throws ParseException {
		try {
			Parent signUpRoot = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
			Stage currentStage = (Stage) addNewUser.getScene().getWindow();
			Scene signUpScene = new Scene(signUpRoot);
			currentStage.setScene(signUpScene);
			currentStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setupDept() {
		try {

			Connection con = DBConnecter.connectDB();
			String query = "select dlocation,dname from department";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String dlocation = rs.getString("dlocation");
				String dname = rs.getString("dname");
				String department = dlocation + "," + dname;
				dept.getItems().add(department);
			}
			stmt.close();
			rs.close();
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setMangerConfirmationController(MangerConfirmationController mangerConfirmationController) {
		this.mangerConfirmationController = mangerConfirmationController;
	}

}

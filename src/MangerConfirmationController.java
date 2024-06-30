import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MangerConfirmationController {

	@FXML
	private Button cancel;

	@FXML
	private Button confirm;

	@FXML
	private TextField id;

	@FXML
	private PasswordField passwd;
	
	private LoginController loginController;
	
	private int managerID;
	
	@FXML
	void isValidManager(ActionEvent event) {
		try {
			Connection con = DBConnecter.connectDB();
			boolean idCheck = false;
			for(int i=0 ; i<id.getText().length(); i++) {
				if(Character.isLetter(id.getText().charAt(i)))
					idCheck = true;
			}
			if (id.getText().isEmpty() || passwd.getText().isEmpty() && idCheck) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Please enter your login credentials ");
				alert.showAndWait();
			} else {
				String query = "select managerID,passwd from employees where eid = " + id.getText();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				if (!(rs.isBeforeFirst())) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("User Not Found");
					alert.showAndWait();
				} else {
					String password = "";
					while (rs.next()) {
						password = rs.getString("passwd");
						managerID = rs.getInt("managerID");
					}
					
					if (password.equals(passwd.getText()) && managerID == Integer.parseInt(id.getText())) {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpScene.fxml"));
						Parent signUpRoot = loader.load();
						SignupController signupController = loader.getController();
						signupController.setMangerConfirmationController(this);
						Stage currentStage = (Stage) loginController.getLogin().getScene().getWindow();
						Scene signUpScene = new Scene(signUpRoot);
						currentStage.setScene(signUpScene);
						currentStage.show();
						Stage currentStage2 = (Stage) confirm.getScene().getWindow();
						currentStage2.close();
					} else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("Password is Incorrect or you are not a manager");
						alert.showAndWait();
					}

				}
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void cancel(ActionEvent event) {
		try {
			Parent signUpRoot = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
			Stage currentStage = (Stage) confirm.getScene().getWindow();
			Scene signUpScene = new Scene(signUpRoot);
			currentStage.setScene(signUpScene);
			currentStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}
	
	public int getManagerID() {
		return this.managerID;
	}
	
}

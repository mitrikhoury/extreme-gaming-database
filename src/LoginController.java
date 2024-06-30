import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private TextField id;

	@FXML
	private Button login;

	@FXML
	private PasswordField passwd;

	@FXML
	private Button singup;

	private int managerID;

	private boolean isManager = false;

	@FXML
	void loadSignUp(ActionEvent event) {
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerConformation.fxml"));
			root = loader.load();
			MangerConfirmationController MangerConfirmationController = loader.getController();
			MangerConfirmationController.setLoginController(this);
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void login(ActionEvent event) {
		try {
			if (id.getText().isEmpty() || passwd.getText().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Please enter your login credentials ");
				alert.showAndWait();
			} else {
				Connection con = DBConnecter.connectDB();
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

					if (password.equals(passwd.getText())) {
						if (Integer.parseInt(id.getText()) == managerID) {
							isManager = true;
						}
						FXMLLoader loader = new FXMLLoader(getClass().getResource("HeadScene.fxml"));


						// Wrap the BorderPane inside an AnchorPane
						AnchorPane anchorPane = loader.load();
						
						BorderPane borderPane = new BorderPane(anchorPane);
						
						// Get the controller of the HeadScene.fxml
						Controller controller = loader.getController();

						// Pass the reference of LoginController to Controller
						controller.setLoginController(this);
						controller.setEmployeeID(Integer.parseInt(id.getText()));
						Stage currentStage = (Stage) login.getScene().getWindow();
						Scene signUpScene = new Scene(borderPane);
						currentStage.setScene(signUpScene);
						currentStage.show();
					} else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("Password is Incorrect");
						alert.showAndWait();
					}

				}
				stmt.close();
				rs.close();
				con.close();
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Button getLogin() {
		return this.login;
	}

	public boolean getIsManager() {
		return this.isManager;
	}

}
package employee.employeefx;

import employee.employeefx.controller.EmployeeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Employee");
		try {

			// Loading..
			FXMLLoader loader = new FXMLLoader(getClass().getResource("employee_view.fxml"));
			Parent root = loader.load();
			EmployeeController controller = loader.getController();
			controller.init();
			controller.updateEmployees();
			stage.setScene(new Scene(root));

		} catch (IOException e) {
			e.printStackTrace();
		}
		stage.show();
	}
	
	public static void main(String[] args) {

		launch(EmployeeApplication.class);
		
	}

}
package employee.employeefx.controller;

import employee.employeefx.database.EmployeeDatabase;
import employee.employeefx.model.Address;
import employee.employeefx.model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;

import java.util.Map;
import java.util.Optional;

public class EmployeeController {

	@FXML
	private TableView<Employee> employee_table;
	@FXML
	private TableColumn<Employee, String> tc_fname, tc_lname, tc_contact, tc_street, tc_city, tc_state;
	@FXML
	private TableColumn<Employee, Double> tc_salary;
	@FXML
	private TableColumn<Employee, Integer> tc_zipcode;
	@FXML
	private TextField f_id, f_fname, f_lname, f_contact, f_salary, f_street, f_city, f_state, f_zipcode;
	private EmployeeDatabase db;
	private Employee oldEmp;
	
	public void init() {
		db = new EmployeeDatabase();
		tc_fname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		tc_lname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		tc_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
		tc_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
		tc_street.setCellValueFactory(new PropertyValueFactory<>("street"));
		tc_city.setCellValueFactory(new PropertyValueFactory<>("city"));
		tc_state.setCellValueFactory(new PropertyValueFactory<>("state"));
		tc_zipcode.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
		employee_table.getSelectionModel().selectedItemProperty().addListener((obs, old, current) -> {
			if (current != null) {
				Employee emp = employee_table.getSelectionModel().getSelectedItem();
				if (emp != null) {
					oldEmp = emp;
					f_id.setText(String.valueOf(emp.getId()));
					f_fname.setText(emp.getFirstName());
					f_lname.setText(emp.getLastName());
					f_contact.setText(emp.getContact());
					f_salary.setText(String.valueOf(emp.getSalary()));
					f_street.setText(emp.getAddress().getStreet());
					f_city.setText(emp.getAddress().getCity());
					f_state.setText(emp.getAddress().getState());
					f_zipcode.setText(String.valueOf(emp.getAddress().getZipCode()));
				}
			}
		});
		oldEmp = null;
	}
	
	public void updateEmployees() {
		Map<Integer, Employee> employees = db.readEmployees();
		ObservableList<Employee> employeeList = FXCollections.observableArrayList(employees.values());
		employee_table.setItems(employeeList);
	}
	
	@FXML
	private void insertEmployee() {
		String firstName = f_fname.getText().trim();
		String lastName = f_lname.getText().trim();
		String contact = f_contact.getText().trim();
		String salaryStr = f_salary.getText().trim();
		String street = f_street.getText().trim();
		String city = f_city.getText().trim();
		String state = f_state.getText().trim();
		String zipcodeStr = f_zipcode.getText().trim();
		TextField[] fields = {f_fname, f_lname, f_contact, f_salary, f_street, f_city, f_state, f_zipcode};
		for (TextField field : fields) {
			if (field.getText().trim().isBlank()) {
				showAlert("Empty Field", "No any field should be empty!");
				return;
			}
		}
		double salary;
		try {
			salary = Double.parseDouble(salaryStr);
		} catch (NumberFormatException e) {
			showAlert("Incorrect data", "Salary should have valid data.");
			return;
		}
		int zipcode;
		try {
			zipcode = Integer.parseInt(zipcodeStr);
		} catch (NumberFormatException e) {
			showAlert("Incorrect data", "Zip Code should have valid data.");
			return;
		}
		Address address = new Address(0, street, city, state, zipcode);
		Employee employee = new Employee(0, firstName, lastName, contact, salary, address);
		db.insert(employee);
		for (TextField field : fields) {
			field.clear();
		}
		updateEmployees();
	}

	@FXML
	private void updateEmployee() {
		if (oldEmp == null){
			showAlert("Update", "Select the row from table.");
			return;
		}
		String firstName = f_fname.getText().trim();
		String lastName = f_lname.getText().trim();
		String contact = f_contact.getText().trim();
		String salaryStr = f_salary.getText().trim();
		String street = f_street.getText().trim();
		String city = f_city.getText().trim();
		String state = f_state.getText().trim();
		String zipcodeStr = f_zipcode.getText().trim();
		TextField[] fields = {f_id, f_fname, f_lname, f_contact, f_salary, f_street, f_city, f_state, f_zipcode};
		for (TextField field : fields) {
			if (field.getText().trim().isBlank()) {
				showAlert("Empty Field", "No any field should be empty!");
				return;
			}
		}
		double salary;
		try {
			salary = Double.parseDouble(salaryStr);
		} catch (NumberFormatException e) {
			showAlert("Incorrect data", "Salary should have valid data.");
			return;
		}
		int zipcode;
		try {
			zipcode = Integer.parseInt(zipcodeStr);
		} catch (NumberFormatException e) {
			showAlert("Incorrect data", "Zip Code should have valid data.");
			return;
		}
		Address address = new Address(oldEmp.getAddress().getId(), street, city, state, zipcode);
		Employee employee = new Employee(oldEmp.getId(), firstName, lastName, contact, salary, address);
		db.update(oldEmp.getId(), employee);
		oldEmp = null;
		for (TextField field : fields) {
			field.clear();
		}
		updateEmployees();
	}

	@FXML
	private void deleteEmployee() {
		if (oldEmp == null){
			showAlert("Delete", "Select the row from table.");
			return;
		}
		Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you confirmed to delete it?");
		Optional<ButtonType> result = confirmAlert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			db.delete(oldEmp.getId());
			oldEmp = null;
			updateEmployees();
			TextField[] fields = {f_id, f_fname, f_lname, f_contact, f_salary, f_street, f_city, f_state, f_zipcode};
			for (TextField field : fields) {
				field.clear();
			}
			showAlert("Done", "Employee is deleted!");
		}
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

}

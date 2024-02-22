package employee.employeefx.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import employee.employeefx.model.*;

public class EmployeeDatabase {

	// Attributes.
	private Database database;

	public EmployeeDatabase() {
		this.database = Database.getInstance();
	}

	public void insert(Employee emp) {
		database.execute("insert into employees (first_name, last_name, contact, salary) values ('" + emp.getFirstName()
				+ "', '" + emp.getLastName() + "', '" + emp.getContact() + "', " + emp.getSalary() + ")");
		ResultSet result = database.executeQuery("select last_insert_id() as emp_id");
		try {
			if (result.next()) {
				Address address = emp.getAddress();
				database.execute("insert into address (street, city, state, zipcode, emp_id) values ('"
						+ address.getStreet() + "', '" + address.getCity() + "', '" + address.getState() + "', "
						+ address.getZipCode() + ", " + result.getInt("emp_id") + ")");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Map<Integer, Employee> readEmployees() {
		Map<Integer, Employee> employees = new HashMap<>();
		ResultSet result = database.executeQuery("select * from employees join address on employees.emp_id = address.emp_id");
		try {
			while (result.next()) {
				int id = result.getInt("emp_id");
				employees.put(id, new Employee(id, result.getString("first_name"), result.getString("last_name"),
						result.getString("contact"), result.getDouble("salary"),
						new Address(result.getInt("address_id"), result.getString("street"), result.getString("city"),
								result.getString("state"), result.getInt("zipcode"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	public void update(int empId, Employee employee) {
		Address address = employee.getAddress();
		database.execute("update employees set first_name = '" + employee.getFirstName() + "', last_name = '"
				+ employee.getLastName() + "', contact = '" + employee.getContact() + "', salary = "
				+ employee.getSalary() + " where emp_id = " + empId);
		database.execute("update address set street = '" + address.getStreet() + "', city = '"
				+ address.getCity() + "', state = '" + address.getState() + "', zipcode = " + address.getZipCode()
				+ " where emp_id = " + empId);
	}

	public void delete(int empId) {
		database.execute("delete from address where emp_id = " + empId);
		database.execute("delete from employees where emp_id = " + empId);
	}
}

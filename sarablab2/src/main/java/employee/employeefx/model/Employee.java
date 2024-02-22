package employee.employeefx.model;

public class Employee {

	private int id;
	private String firstName;
	private String lastName;
	private String contact;
	private double salary;
	private Address address;
	
	public Employee(int id, String firstName, String lastName, String contact, double salary, Address address) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contact = contact;
		this.salary = salary;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getContact() {
		return contact;
	}

	public double getSalary() {
		return salary;
	}

	public Address getAddress() {
		return address;
	}

	public String getStreet() {
		return address.getStreet();
	}

	public String getCity() {
		return address.getCity();
	}

	public String getState() {
		return address.getState();
	}

	public int getZipCode() {
		return address.getZipCode();
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
}

import java.sql.Date;

public class Customer {

	private int customerID;
	private String name;
	private String email;
	private double debt;
	private int phone;
	private Date lastOrder;
	private Date lastPayment;

	public Customer(int customerID, String name, String email, double debt, int phone, Date lastOrder,
			Date lastPayment) {
		this.customerID = customerID;
		this.name = name;
		this.email = email;
		this.debt = debt;
		this.phone = phone;
		this.lastOrder = lastOrder;
		this.lastPayment = lastPayment;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getDebt() {
		return debt;
	}

	public void setDebt(double debt) {
		this.debt = debt;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public Date getLastOrder() {
		return lastOrder;
	}

	public void setLastOrder(Date lastOrder) {
		this.lastOrder = lastOrder;
	}

	public Date getLastPayment() {
		return lastPayment;
	}

	public void setLastPayment(Date lastPayment) {
		this.lastPayment = lastPayment;
	}

}



import java.sql.Date;

public class Customer1 {
	private int customerID;
	private Date last_payment_date;
	private String Email;
	private Date last_Order;
	private String Cname;
	private int Phone;
	private double debt;
	
	/**
	 * @param customerID
	 * @param last_payment_date
	 * @param email
	 * @param last_Order
	 * @param cname
	 * @param phone
	 * @param employeeID
	 * @param debt
	 */
	public Customer1(int customerID, Date last_payment_date, String email, Date last_Order, String Cname,int phone, double debt) {
		super();
		this.customerID = customerID;
		this.last_payment_date = last_payment_date;
		Email = email;
		this.last_Order = last_Order;
		this.Cname=Cname;
		Phone = phone;
		this.debt = debt;
	}
	/**
	 * @return the customerID
	 */
	public int getCustomerID() {
		return customerID;
	}
	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	/**
	 * @return the last_payment_date
	 */
	public Date getLast_payment_date() {
		return last_payment_date;
	}
	/**
	 * @param last_payment_date the last_payment_date to set
	 */
	public void setLast_payment_date(Date last_payment_date) {
		this.last_payment_date = last_payment_date;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return Email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		Email = email;
	}
	/**
	 * @return the last_Order
	 */
	public Date getLast_Order() {
		return last_Order;
	}
	/**
	 * @param last_Order the last_Order to set
	 */
	public void setLast_Order(Date last_Order) {
		this.last_Order = last_Order;
	}
	
	/**
	 * @return the phone
	 */
	public int getPhone() {
		return Phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(int phone) {
		Phone = phone;
	}
	/**
	 * @return the employeeID
	 */

	/**
	 * @return the debt
	 */
	public double getDebt() {
		return debt;
	}
	/**
	 * @param debt the debt to set
	 */
	public void setDebt(double debt) {
		this.debt = debt;
	}
	/**
	 * @return the cname
	 */
	public String getCname() {
		return Cname;
	}
	/**
	 * @param cname the cname to set
	 */
	public void setCname(String cname) {
		Cname = cname;
	}
	
	
}

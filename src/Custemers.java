

import java.sql.Date;

public class Custemers {

	private int CustemerId ;
	private String Custemername;
	private Date LastPayment;
	private String Email;
	private Date LastOrder;
	private int phone;
	private int EmpId;
	private double dept;
	
	
	
	public Custemers(int custemerId, String custemername, double dept) {
		super();
		CustemerId = custemerId;
		Custemername = custemername;
		this.dept = dept;
	}
	public Custemers(int custemerId, String custemername, Date lastPayment, String email, Date lastOrder, int phone,
			int empId, double dept) {
		super();
		CustemerId = custemerId;
		Custemername = custemername;
		LastPayment = lastPayment;
		Email = email;
		LastOrder = lastOrder;
		this.phone = phone;
		EmpId = empId;
		this.dept = dept;
	}
	public int getCustemerId() {
		return CustemerId;
	}
	public void setCustemerId(int custemerId) {
		CustemerId = custemerId;
	}
	public String getCustemername() {
		return Custemername;
	}
	public void setCustemername(String custemername) {
		Custemername = custemername;
	}
	public Date getLastPayment() {
		return LastPayment;
	}
	public void setLastPayment(Date lastPayment) {
		LastPayment = lastPayment;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public Date getLastOrder() {
		return LastOrder;
	}
	public void setLastOrder(Date lastOrder) {
		LastOrder = lastOrder;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public int getEmpId() {
		return EmpId;
	}
	public void setEmpId(int empId) {
		EmpId = empId;
	}
	public double getDept() {
		return dept;
	}
	public void setDept(double dept) {
		this.dept = dept;
	}
	
	
	
	
}

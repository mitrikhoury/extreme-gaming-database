

import java.sql.Date;

public class Employees {
	
	private int eid;
	private String 	vacation_days;
	private String ename;
	private String contact;
	private boolean E_active ;
	
	private String job_title;
	private Date birthday;
	private int managerID;
	private  double salary;
	private int did;
	private Date hire_date;
	private String passwd;
	
	
	
	/**
	 * @param eid
	 * @param vacation_days
	 * @param ename
	 * @param contact
	 * @param e_active
	 * @param job_title
	 * @param birthday
	 * @param managerID
	 * @param salary
	 * @param did
	 * @param hire_date
	 * @param passwd
	 */
	public Employees(int eid, String vacation_days, String ename, String contact, boolean e_active, String job_title,
			Date birthday, int managerID, double salary, int did, Date hire_date, String passwd) {
		super();
		this.eid = eid;
		this.vacation_days = vacation_days;
		this.ename = ename;
		this.contact = contact;
		E_active = e_active;
		this.job_title = job_title;
		this.birthday = birthday;
		this.managerID = managerID;
		this.salary = salary;
		this.did = did;
		this.hire_date = hire_date;
		this.passwd = passwd;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * @param eid
	 * @param vacation_days
	 * @param ename
	 * @param contact
	 * @param e_active
	 * @param job_title
	 * @param birthday
	 * @param managerID
	 * @param salary
	 * @param did
	 * @param hire_date
	 */
	public Employees(int eid, String vacation_days, String ename, String contact, boolean e_active, String job_title,
			Date birthday, int managerID, double salary, int did, Date hire_date) {
		super();
		this.eid = eid;
		this.vacation_days = vacation_days;
		this.ename = ename;
		this.contact = contact;
		this.E_active = e_active;
		this.job_title = job_title;
		this.birthday = birthday;
		this.managerID = managerID;
		this.salary = salary;
		this.did = did;
		this.hire_date = hire_date;
	}
	
//	/**
//	 * @param vacation_days
//	 * @param ename
//	 * @param contact
//	 * @param e_active
//	 * @param job_title
//	 * @param birthday
//	 * @param managerID
//	 * @param salary
//	 * @param did
//	 * @param hire_date
//	 */
//	public Employees(String vacation_days, String ename, String contact, boolean e_active, String job_title,
//			Date birthday, int managerID, double salary, int did, Date hire_date) {
//		super();
//		this.vacation_days = vacation_days;
//		this.ename = ename;
//		this.contact = contact;
//		E_active = e_active;
//		this.job_title = job_title;
//		this.birthday = birthday;
//		this.managerID = managerID;
//		this.salary = salary;
//		this.did = did;
//		this.hire_date = hire_date;
//	}

	/**
	 * @return the eid
	 */
	public int getEid() {
		return eid;
	}
	/**
	 * @param eid the eid to set
	 */
	public void setEid(int eid) {
		this.eid = eid;
	}
	/**
	 * @return the vacation_days
	 */
	public String getVacation_days() {
		return vacation_days;
	}
	/**
	 * @param vacation_days the vacation_days to set
	 */
	public void setVacation_days(String vacation_days) {
		this.vacation_days = vacation_days;
	}
	/**
	 * @return the ename
	 */
	public String getEname() {
		return ename;
	}
	/**
	 * @param ename the ename to set
	 */
	public void setEname(String ename) {
		this.ename = ename;
	}
	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	/**
	 * @return the e_active
	 */
	public boolean isE_active() {
		return E_active;
	}
	/**
	 * @param e_active the e_active to set
	 */
	public void setE_active(boolean e_active) {
		E_active = e_active;
	}
	/**
	 * @return the job_title
	 */
	public String getJob_title() {
		return job_title;
	}
	/**
	 * @param job_title the job_title to set
	 */
	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}
	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the managerID
	 */
	public int getManagerID() {
		return managerID;
	}
	/**
	 * @param managerID the managerID to set
	 */
	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}
	/**
	 * @return the salary
	 */
	public double getSalary() {
		return salary;
	}
	/**
	 * @param salary the salary to set
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}
	/**
	 * @return the did
	 */
	public int getDid() {
		return did;
	}
	/**
	 * @param did the did to set
	 */
	public void setDid(int did) {
		this.did = did;
	}
	/**
	 * @return the hire_date
	 */
	public Date getHire_date() {
		return hire_date;
	}
	/**
	 * @param hire_date the hire_date to set
	 */
	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}
	
	
	
	
}
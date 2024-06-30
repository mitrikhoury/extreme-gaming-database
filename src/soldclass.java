

import java.sql.Date;

public class soldclass {

	 private Date insuranceExpirationDate;
	 private Date manufacturingDate; 
	 private Date SaleDate;
	 private String SoldBy;
	 private String BoughtBy;
	 private int amount;
	 private String Pname;

	 

	public soldclass(Date insuranceExpirationDate, Date manufacturingDate, Date saleDate, String soldBy, String boughtBy,
			int amount, String pname) {
		super();
		this.insuranceExpirationDate = insuranceExpirationDate;
		this.manufacturingDate = manufacturingDate;
		SaleDate = saleDate;
		SoldBy = soldBy;
		BoughtBy = boughtBy;
		this.amount = amount;
		setPname(pname);
	}
	
	public Date getInsuranceExpirationDate() {
		return insuranceExpirationDate;
	}

	public void setInsuranceExpirationDate(Date insuranceExpirationDate) {
		this.insuranceExpirationDate = insuranceExpirationDate;
	}

	public Date getManufacturingDate() {
		return manufacturingDate;
	}
	public void setManufacturingDate(Date manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}
	public Date getSaleDate() {
		return SaleDate;
	}
	public void setSaleDate(Date saleDate) {
		SaleDate = saleDate;
	}
	public String getSoldBy() {
		return SoldBy;
	}
	public void setSoldBy(String soldBy) {
		SoldBy = soldBy;
	}
	public String getBoughtBy() {
		return BoughtBy;
	}
	public void setBoughtBy(String boughtBy) {
		BoughtBy = boughtBy;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPname() {
		return Pname;
	}
	public void setPname(String pname) {
		Pname = pname;
	}
	 
	 
	
}

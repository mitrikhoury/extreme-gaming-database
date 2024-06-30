

import java.sql.Date;

public class HiredCustomer {
	private String Cname;
	private int Amount;
	private Date productuionDate;
	private Date insuranceExpirationDate;
	private Date soldDate;
	private String productName;
	/**
	 * @param cname
	 * @param amount
	 * @param productuionDate
	 * @param expiryDate
	 * @param soldDate
	 * @param solderName
	 * @param productName
	 */
	public HiredCustomer(String cname, int amount, Date productuionDate, Date insuranceExpirationDate, Date soldDate,
			 String productName) {
		super();
		Cname = cname;
		Amount = amount;
		this.productuionDate = productuionDate;
		this.insuranceExpirationDate = insuranceExpirationDate;
		this.soldDate = soldDate;
		
		this.productName = productName;
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
	/**
	 * @return the amount
	 */
	public int getAmount() {
		return Amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		Amount = amount;
	}
	/**
	 * @return the productuionDate
	 */
	public Date getProductuionDate() {
		return productuionDate;
	}
	/**
	 * @param productuionDate the productuionDate to set
	 */
	public void setProductuionDate(Date productuionDate) {
		this.productuionDate = productuionDate;
	}
	/**
	 * @return the expiryDate
	 */
	public Date getInsuranceExpirationDate() {
		return insuranceExpirationDate;
	}
	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setInsuranceExpirationDate(Date insuranceExpirationDate) {
		this.insuranceExpirationDate = insuranceExpirationDate;
	}
	/**
	 * @return the soldDate
	 */
	public Date getSoldDate() {
		return soldDate;
	}
	/**
	 * @param soldDate the soldDate to set
	 */
	public void setSoldDate(Date soldDate) {
		this.soldDate = soldDate;
	}
	
	
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}







}
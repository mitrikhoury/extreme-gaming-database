import java.sql.Date;

public class Product {

	private int barcode;
	private String name;
	private double price;
	private int amount;
	private Date expiredDate;
	private Date manufacturingDate;
	private int IdCategories;

	public Product(int barcode, String name, double price, int weight, Date expiredDate, Date manufacturingDate,
			int IdCategories) {
		this.barcode = barcode;
		this.name = name;
		this.price = price;
		this.amount = weight;
		this.expiredDate = expiredDate;
		this.manufacturingDate = manufacturingDate;
		this.IdCategories = IdCategories;
	}

	public Product(String pname, int amount, Date expiredDate, Date manufacturingDate) {
		super();
		this.name = pname;
		this.amount = amount;
		this.expiredDate = expiredDate;
		this.manufacturingDate = manufacturingDate;
	}

	public Product(int pid, String pname, int amount, Date expiredDate, Date manufacturingDate) {
		super();
		this.barcode = pid;
		this.name = pname;
		this.amount = amount;
		this.expiredDate = expiredDate;
		this.manufacturingDate = manufacturingDate;
	}

	public Product(int pid, String pname, int amount, Date expiredDate, Date manufacturingDate, double price) {
		super();
		this.barcode = pid;
		this.name = pname;
		this.amount = amount;
		this.price = price;
		this.expiredDate = expiredDate;
		this.manufacturingDate = manufacturingDate;
	}

	public Product(String name) {
		this.name = name;
	}

	public int getBarcode() {
		return barcode;
	}

	public void setBarcode(int barcode) {
		this.barcode = barcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Date getManufacturingDate() {
		return manufacturingDate;
	}

	public void setManufacturingDate(Date manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}

	public int getIdCategories() {
		return IdCategories;
	}

	public void setIdCategories(int idCategories) {
		IdCategories = idCategories;
	}

}

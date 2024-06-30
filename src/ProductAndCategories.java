

public class ProductAndCategories {

	private int Pid;
    private String pname;
    private double pprice;
    private String cname;
    
	public ProductAndCategories(int pid, String pname, double pprice, String cname) {
		super();
		Pid = pid;
		this.pname = pname;
		this.pprice = pprice;
		this.cname = cname;
	}
	
	public int getPid() {
		return Pid;
	}
	public void setPid(int pid) {
		Pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public double getPprice() {
		return pprice;
	}
	public void setPprice(double pprice) {
		this.pprice = pprice;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
    
    
}

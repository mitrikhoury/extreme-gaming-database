
public class Report {

	private String reportType;
	private int amount;

	public Report(String reportType, int amount) {
		this.reportType = reportType;
		this.amount = amount;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}

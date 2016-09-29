package ifaApi;

public class StatusMessage {

	private String status;
	private int percentage;
	
	public static StatusMessage message = new StatusMessage("noch leer", 0);

	private StatusMessage(String status, int percentage) {
		this.status = status;
		this.percentage = percentage;
	}
	

	public String getStatus() {
		return status;
	}

	public void setName(String status) {
		this.status = status;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
}
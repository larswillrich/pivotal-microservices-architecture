package ifaWrap;

public interface Listener {

	public void setBestellStatus(String message);
	public void setProgressBarStatus(int percentage);
	void sendUpdate();
}

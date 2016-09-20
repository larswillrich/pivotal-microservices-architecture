package ifaMock;

public class Bestellung {

	public enum STATUS {EINGEGANGEN, VORBEREITUNG, FERTIGUNG, PRUEFUNG, AUSLIEFERUNG, ZUSTELLUNG}
	
	private STATUS currentStatus;
	
	public Bestellung() {
		this.currentStatus = STATUS.values()[0];
	}
	
	public Bestellung(STATUS s){
		this.currentStatus = s;
	}
	
	public int numberSteps(){
		return STATUS.values().length;
	}
	
	public STATUS increment(){
		currentStatus = next();
		return currentStatus;
	}
	
	public STATUS next(){
		return STATUS.values()[(currentStatus.ordinal() + 1) % currentStatus.values().length];
	}
	
	public STATUS now(){
		return currentStatus;
	}
}

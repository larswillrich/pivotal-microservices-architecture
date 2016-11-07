package ifaMock;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
public class Bestellung {

	public enum STATUS {
		EINGEGANGEN, VORBEREITUNG, FERTIGUNG, PRUEFUNG, AUSLIEFERUNG, ZUSTELLUNG, EMPFANGEN
	}

	private STATUS currentStatus;
	private String id;
	private int percentage;

	public static Map<String, Bestellung> bestellungen = new HashMap<>();

	public Bestellung() {
		this.currentStatus = STATUS.values()[0];
	}

	public Bestellung(STATUS s) {
		this.currentStatus = s;
	}

	public static String getRandomID() {
		int maxLetters = 6;
		String id = "";

		int min = 65;
		int max = 90;

		for (int i = 0; i < maxLetters; i++) {
			id += (char) ((Math.random() * (max - min)) + min);
		}

		return id;
	}

	{
		String randomID = "";
		do {
			randomID = Bestellung.getRandomID();
		} while (Bestellung.bestellungen.get(randomID) != null);

		this.id = randomID;
	}

	@JsonIgnoreProperties
	public int numberSteps() {
		return STATUS.values().length;
	}

	public String getID() {
		return this.id;
	}

	@JsonIgnoreProperties
	public STATUS increment() {
		currentStatus = next();
		return currentStatus;
	}

	@JsonIgnoreProperties
	public STATUS next() {
		STATUS status = STATUS.values()[(currentStatus.ordinal() + 1) % STATUS.values().length];

		if (status.compareTo(STATUS.EMPFANGEN) == 0)
			this.percentage = 100;
		else
			this.percentage = 100 / numberSteps() + (status.ordinal() * 100) / numberSteps();

		return status;
	}

	public STATUS getCurrentStatus() {
		return currentStatus;
	}

	public int getPercentage() {
		return this.percentage;
	}
}

package ifaApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
			this.percentage = 100 / numberSteps() + (getCurrentStatus().ordinal() * 100) / numberSteps();

		return status;
	}

	public STATUS getCurrentStatus() {
		return currentStatus;
	}

	public int getPercentage() {
		return this.percentage;
	}

	public static String getAllOrdersAsJSON() {
		
		List sortedOrderList = new ArrayList(Bestellung.bestellungen.values());
		
		Collections.sort(sortedOrderList, new Comparator<Bestellung>() {

	        public int compare(Bestellung o1, Bestellung o2) {
	            return o1.getID().compareTo(o2.getID());
	        }
	    });
		
		// send order
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(sortedOrderList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}

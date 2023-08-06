package it.unibs.pajc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Zone implements Serializable{

	private String zoneName; 
	private List<Integer> zoneNumbers;
	private int payout;
	private int betValue;
	
	public Zone(String zoneName, List<Integer> zoneNumbers) {
		super();
		this.zoneName = zoneName;
		this.zoneNumbers = zoneNumbers;
	}
	

	public Zone(String zoneName, List<Integer> zoneNumbers, int payout) {
		super();
		this.zoneName = zoneName;
		this.zoneNumbers = zoneNumbers;
		this.payout = payout;
	}


	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public List<Integer> getZoneNumbers() {
		return zoneNumbers;
	}

	public void setZoneNumbers(List<Integer> zoneNumbers) {
		this.zoneNumbers = new ArrayList<>(zoneNumbers);
	}


	public int getPayout() {
		return payout;
	}

	public void setPayout(int payout) {
		this.payout = payout;
	}


	public int getBetValue() {
		return betValue;
	}


	public void setBetValue(int betValue) {
		this.betValue = betValue;
	}

	

}

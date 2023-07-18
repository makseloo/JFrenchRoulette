package it.unibs.pajc;

public class Fiche {
	private int value;
	private boolean isSelected;
	
	public Fiche(int value, boolean isSelected) {
		this.value = value;
		this.isSelected = isSelected;
	}

	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
}

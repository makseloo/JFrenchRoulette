package it.unibs.pajc.server;

import java.io.Serializable;

public class TimerMessage implements Serializable{
	
	private int seconds;
	public TimerMessage(int seconds) {
		super();
		this.seconds = seconds;
	}
	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
}

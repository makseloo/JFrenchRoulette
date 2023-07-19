package it.unibs.pajc;
//sto file va nel model?
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

	public class Colors {
		//debug
		private static final Random random = new Random();
		
		
		private static Color standardBlack = new Color(34,34,34);
		private static Color standardRed = new Color(204,0,0);
		private static Color standardGreen = new Color(0,128,0);
		private static Color standardGray = new Color(80,80,80);
		
		
		public static Color getBlack() {
			return standardBlack;
		}
		public static Color getRed() {
			return standardRed;
		}
		public static Color getGreen() {
			return standardGreen;
		}
		public static Color getGray() {
			return standardGray;
		}
		
		//debug
		public static Color getRandomColor() {
			int r = random.nextInt(256); // Random value between 0 and 255 for red
	        int g = random.nextInt(256); // Random value between 0 and 255 for green
	        int b = random.nextInt(256); // Random value between 0 and 255 for blue
	        return new Color(r, g, b);
		}
	}
		

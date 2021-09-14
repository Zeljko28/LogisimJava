package logisim;

import java.awt.Color;
import java.awt.Graphics2D;

public class connector {
	
	private int x, y;
	private int value;
	
	private double strength;
	private double inputStrength;
	private boolean isInput;
	private boolean isWire;
	
	public connector(int m_x, int m_y, boolean m_isInput, boolean m_isWire, double m_strength, double m_inputStrength) {
		x = m_x;
		y = m_y;
		isInput = m_isInput;
		isWire = m_isWire;
		strength = m_strength;
		inputStrength = m_inputStrength;
	}
	
	public boolean isInput() { return isInput; }
	public boolean isWire() { return isWire; }
	
	public void setX(int m_x) { x = m_x; }
	public void setY(int m_y) { y = m_y; }
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	
	public void setValue(int m_value) { value = m_value; }
	public int getValue() { return this.value; }
	
	public void setStrength(double m_strength) { strength = m_strength; }
	public double getStrength() { return this.strength; }
	
	public void setInputStrength(double m_inputStrength) { inputStrength = m_inputStrength; }
	public double getInputStrength() { return this.inputStrength; }
	
	public String[] getElements() {
		String [] arr = {"" + this.x + " ", "" + this.y + " ", "" + this.isInput + " ", "" + this.isWire + " ", "" + this.strength + " ", "" + this.inputStrength + " "};
		return arr;
	}
	
	
	public boolean equal(connector other) {
		
		if((this.getX() < (other.getX() + 20)) && (this.getX() > (other.getX() - 20))) {
			if((this.getY() < (other.getY() + 20)) && (this.getY() > (other.getY() - 20))) {		
				return true;
			}
			
			else {
				return false;
			}
		}
		
		else {
			return false;
		}
	}
	
	
	public void draw(Graphics2D g2) {
		
		g2.setColor(Color.YELLOW);
		g2.drawRect(x, y, 20, 20);
		
	}
	
}

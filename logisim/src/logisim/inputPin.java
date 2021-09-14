package logisim;

import java.awt.Color;
import java.awt.Graphics2D;

public class inputPin {
	
	
	int pin;
	
	boolean clicked = false;
	
	public int x, y;
	
	private connector inputConnector;
	
	
	public inputPin(int m_pin, int m_x, int m_y) {
		
		pin = m_pin;
		x = m_x;
		y = m_y;
		
		inputConnector = new connector(m_x + 10, m_y - 10, true, false, 2, 2);
		
		inputConnector.setValue(m_pin);
	}
	
	public String[] getElements() {
		String [] arr = {"" + this.pin + " ", "" + this.x + " ", "" + this.y + " "};
		return arr;
	}
	
	
	public connector getConnector() { return inputConnector; }
	
	public void draw(Graphics2D g2) {
		
		if(pin == 1) {
			g2.setColor(Color.BLACK);
			g2.drawRect(x - 10, y - 10, 20, 20);
			
			g2.setColor(Color.GREEN);
			g2.fillRect(x - 10, y - 10, 20, 20);
			
			g2.setColor(Color.BLACK);
			g2.drawLine(x + 10, y, x + 15, y);
			g2.drawRect(x + 15, y - 3, 6,  6);
			g2.drawString("1", x - 3, y + 5);
		}
		
		if(pin == 0) {
			g2.setColor(Color.BLACK);
			g2.drawRect(x - 10, y - 10, 20, 20);
			
			g2.setColor(new Color(0, 102, 0));
			g2.fillRect(x - 10, y - 10, 20, 20);
			
			g2.setColor(Color.BLACK);
			g2.drawLine(x + 10, y, x + 15, y);
			g2.drawRect(x + 15, y - 3, 6,  6);
			g2.drawString("0", x - 3, y + 5);
		}
	
	}
}

package logisim;

import java.awt.Color;
import java.awt.Graphics2D;

public class outputPin {

	
	private int value;
	
	private int x, y;
	
	private connector outputConnector;
	
	public outputPin(int m_x, int m_y) {
		x = m_x;
		y = m_y;
		outputConnector = new connector(m_x - 20, m_y, false, false, 0, 0);
		outputConnector.setValue(2);
		
		value = outputConnector.getValue();
	}
	
	public String[] getElements() {
		String [] arr = {"" + this.x + " ", "" + this.y + " "};
		return arr;
	}
	
	public connector getConnector() { return outputConnector; }
	
	
	public void draw(Graphics2D g2) {
		String out = "X";
		g2.setColor(Color.BLACK);
		g2.drawOval(x, y, 20, 20);
		
		value = outputConnector.getValue();
		
		if( value == 1 ) { 
			g2.setColor(Color.GREEN);
			out = "1";
		}
		
		else if( value == 0 ) { 
			g2.setColor(new Color(0, 102, 0));
			out = "0";
		}
		
		else {
			g2.setColor(Color.BLUE);
		}
		g2.fillOval(x, y, 20, 20);
		
		g2.setColor(Color.BLACK);
		g2.drawString(out, x + 7, y + 15);
		g2.drawLine(x, y + 10, x - 5, y + 10);
		g2.drawRect(x - 11, y + 7, 6,  6);
	}
}

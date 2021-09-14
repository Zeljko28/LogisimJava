package logisim;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class wire {
	
	private int startX, startY, endX, endY;
	private connector startConnector;
	private connector endConnector;
	private int initial = 0;
	
	public wire(int sX, int sY, int eX, int eY) {
		startX = sX;
		startY = sY;
		endX = eX;
		endY = eY;
		
		startConnector = new connector(sX, sY, false, true, 1.1, 1);
		endConnector = new connector(eX, eY, false, true, 1.1, 1);
		
		startConnector.setValue(2);
		
		endConnector.setValue(startConnector.getValue());
	} 
	
	
	public connector getStartConnector() { return startConnector; }
	public connector getEndConnector() { return endConnector; }
	
	public String[] getElements() {
		String [] arr = {"" + this.startX + " ", "" + this.startY + " ", "" + this.endX + " ", "" + this.endY + " "};
		return arr;
	}
	
	public void updateWires() {
		if(initial == 0) {
		// ovde je problem.. 
			if(endX < startX) {
				startConnector.setValue(endConnector.getValue());
				endConnector.setInputStrength(1.1);
			}
			else {
				endConnector.setValue(startConnector.getValue());
				startConnector.setInputStrength(1.1);
			}
			initial++;
		}
		
		else {
			if(startConnector.getInputStrength() > endConnector.getInputStrength()) {
				endConnector.setValue(startConnector.getValue());
			}
			else {
				startConnector.setValue(endConnector.getValue());
			}
		}
		
		
		
	}
	
	public void draw(Graphics2D g2) {
		if(startConnector.getValue() == 1) { g2.setColor(Color.GREEN); }
		else { g2.setColor(new Color(0, 102, 0)); }
	    g2.drawLine(startX, startY, endX, startY);
	    g2.drawLine(endX, startY, endX, endY);
	}
	
	
	
	
	
	
}

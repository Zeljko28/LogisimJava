package logisim;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;

public class circuit {
		int numOfInputs;
		
		connector inputs[];
		connector outConnector;
		int output;
		
		boolean clicked = false;
		
		private int x, y;
		
		String operation;
		
		boolean neg = false;
		
		public circuit (int m_numOfInputs, String m_operation, int m_x, int m_y) {
			numOfInputs = m_numOfInputs;
			inputs = new connector[m_numOfInputs];
			operation = m_operation;		
			
			x = m_x;
			y = m_y;
			
			if(operation.equals("NOT")) {
				outConnector = new connector(x + 55, y - 10, false, false, 0, 0);
				inputs[0] = new connector(x - 55, y - 10, false, false, 0, 0);
				outConnector.setValue(2);
				inputs[0].setValue(2);
				
			}
			
			else if(operation.equals("AND")) {
				outConnector = new connector(x + 55, y - 10, false, false, 0, 0);
				inputs[0] = new connector(x - 55, y - 25, false, false, 0, 0);
				inputs[1] = new connector(x - 55, y + 5, false, false, 0, 0);
				outConnector.setValue(2);
				inputs[0].setValue(2);
				inputs[1].setValue(2);
			}
			
			else if(operation.equals("OR")) {
				outConnector = new connector(x + 55, y - 10, false, false, 0, 0);
				inputs[0] = new connector(x - 40, y - 25, false, false, 0, 0);
				inputs[1] = new connector(x - 40, y + 5, false, false, 0, 0);
				outConnector.setValue(2);
				inputs[0].setValue(2);
				inputs[1].setValue(2);
			}
			
			else if(operation.equals("XOR")) {
				outConnector = new connector(x + 55, y - 10, false, false, 0, 0);
				inputs[0] = new connector(x - 40, y - 25, false, false, 0, 0);
				inputs[1] = new connector(x - 40, y + 5, false, false, 0, 0);
				outConnector.setValue(2);
				inputs[0].setValue(2);
				inputs[1].setValue(2);
			}
			
			
			
			/*for (int i = 0; i < m_numOfInputs; i++) {
				inputs[i] = new connector(x, y, false, false);
			}*/
		}
		
		
		
		public connector getOutConnector() { return outConnector; }
		public connector getInputConnector(int index) { return inputs[index]; }
		
		public int getX() { return this.x; }
		public int getY() { return this.y; }
		public int getNumOfInputs() {return this.numOfInputs; }
		
		public String [] getElements(){
			String [] arr = {"" + this.numOfInputs + " ", this.operation + " " ,"" + this.x + " ", "" + this.y + " "};
			return arr;
		}
		
		
		public String getOperation() { return this.operation; }
		
		public void draw(Graphics2D g2) {
			
			if (operation.equals("AND")) {	
				g2.setColor(Color.BLACK);
				g2.drawLine(x - 20 , y - 20, x - 20, y + 20);
				g2.drawLine(x - 20 , y - 20, x + 20, y - 20);
				g2.drawLine(x - 20 , y + 20, x + 20, y + 20);
				g2.draw(new Arc2D.Double(x, y - 20, 40, 40, 90, -180, Arc2D.OPEN));
				g2.drawLine(x - 20, y - 15, x - 40, y - 15);
				g2.drawRect(x - 46, y - 18, 6,  6);
				g2.drawLine(x - 20, y + 15, x - 40, y + 15);
				g2.drawRect(x - 46, y + 12, 6,  6);
				g2.drawLine(x + 40, y, x + 60, y);
				g2.drawRect(x + 60, y - 3, 6,  6);
			}
			
			else if(operation.equals("OR")) {
				g2.setColor(Color.BLACK);
				g2.draw(new Arc2D.Double(x - 100, y - 50, 100, 100, 30, -60, Arc2D.OPEN));
				g2.draw(new Arc2D.Double(x - 50, y - 25, 100, 100, 95, -65, Arc2D.OPEN));
				g2.draw(new Arc2D.Double(x - 50, y - 75, 100, 100, 265, 65, Arc2D.OPEN));
				g2.drawLine(x - 5, y - 15, x - 25, y - 15);
				g2.drawRect(x - 31, y - 18, 6,  6);
				g2.drawLine(x - 5, y + 15, x - 25, y + 15);
				g2.drawRect(x - 31, y + 12, 6,  6);
				g2.drawLine(x + 45, y, x + 60, y);
				g2.drawRect(x + 60, y - 3, 6,  6);
			}
			
			else if(operation.equals("XOR")) {
				g2.setColor(Color.BLACK);
				g2.draw(new Arc2D.Double(x - 100, y - 50, 100, 100, 30, -60, Arc2D.OPEN));
				g2.draw(new Arc2D.Double(x - 110, y - 50, 100, 100, 30, -60, Arc2D.OPEN));
				g2.draw(new Arc2D.Double(x - 50, y - 25, 100, 100, 95, -65, Arc2D.OPEN));
				g2.draw(new Arc2D.Double(x - 50, y - 75, 100, 100, 265, 65, Arc2D.OPEN));
				g2.drawLine(x - 15, y - 15, x - 25, y - 15);
				g2.drawRect(x - 31, y - 18, 6,  6);
				g2.drawLine(x - 15, y + 15, x - 25, y + 15);
				g2.drawRect(x - 31, y + 12, 6,  6);
				g2.drawLine(x + 45, y, x + 60, y);
				g2.drawRect(x + 60, y - 3, 6,  6);
			}
			
			else if (operation.equals("NOT")){	
				g2.setColor(Color.BLACK);
				g2.drawLine(x - 20, y - 20, x - 20, y + 20);
				g2.drawLine(x - 20, y - 20, x + 40, y);
				g2.drawLine(x - 20, y + 20, x + 40, y);
				g2.drawOval(x + 40, y - 3, 6, 6);
				g2.drawLine(x + 46, y, x + 60, y);
				g2.drawRect(x + 60, y - 3, 6,  6);
				g2.drawLine(x - 40, y, x - 20, y);
				g2.drawRect(x - 46, y - 3, 6,  6);
			}
		}
		
		public int not(connector conn) {
			if(conn.getValue() == 0) {
				return 1;
			}
			if(conn.getValue() == 1) {
				return 0;
			}
			return 2;
		}
		
		
		public int and(connector [] arr) {
			for(int i = 0; i < arr.length; i++) {
				if(arr[i].getValue() == 0 || arr[i].getValue() == 2) {
					return 0;
				}
			}
			return 1;
		}
		
		
		public int or(connector [] arr) {
			for(int i = 0; i < arr.length; i++) {
				if(arr[i].getValue() == 1) {
					return 1;
				}
			}
			return 0;
		}
		
		public int xor(connector [] arr) {
			if((arr[0].getValue() == arr[1].getValue()) && ((arr[0].getValue() != 2) && (arr[1].getValue()) != 2)) {
				return 0;
			}
			else if(arr[0].getValue() != arr[1].getValue()) {
				return 1;
			}
			return 2;
		}
		
		
		
		public void findOutput() {
			
			int output = 0;
			
			if(operation.equals("AND")) {
				output = and(inputs);
				outConnector.setStrength(2);
				for(int i = 0; i < inputs.length; i++) {
					inputs[i].setStrength(1);
				}
			}
			
			else if(operation.equals("OR")) {
				output = or(inputs);
				outConnector.setStrength(2);
				for(int i = 0; i < inputs.length; i++) {
					inputs[i].setStrength(1);
				}
			}
			
			else if(operation.equals("XOR")) {
				output = xor(inputs);
				outConnector.setStrength(2);
				for(int i = 0; i < inputs.length; i++) {
					inputs[i].setStrength(1);
				}
			}
			
			else if(operation.equals("NOT")) {
				output = not(inputs[0]);
				outConnector.setStrength(2);
				for(int i = 0; i < inputs.length; i++) {
					inputs[i].setStrength(1);
				}
			}
			
			this.outConnector.setValue(output);
			
		}
}


package logisim;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class drawArea extends JPanel{
	
	private int oldX, oldY, currentX, currentY;
	private int count = 0;
	public boolean newClicked = false; 
	
	public String str = "";
	
	// FLAGS
	
	public boolean clickedOnSurface = false;
	public boolean wireBtnClicked = false;
	public boolean inputPinClicked = false;
	public boolean outputPinClicked = false;
	private boolean circuitBtnClicked = false;
	public int inputPin;
	
	// INPUT PINS
	
	private inputPin[] inputPins = new inputPin[100];
	private int inputPinsLength = 0;                 // uvedeno da se ne bi prolazilo kroz ceo niz posto su na pocetku sve vrednosti null
	private int inputPinsIndex = 0;					 // index prvog slobodnog clana u nizu
	
	
	// OUTPUT PINS
	
	private outputPin[] outputPins = new outputPin[100];
	private int outputPinsLength = 0;
	private int outputPinsIndex = 0;
	
	// CONNECTORS
	
	private connector[] connectors = new connector[200];
	private int connectorsLength = 0;
	private int connectorsIndex = 0;
	
	// WIRES
	
	private wire[] wires = new wire[100];
	private int wiresLength = 0;
	private int wiresIndex = 0;
	
	// CIRCUITS
	
	private circuit[] circuits = new circuit[100];
	private int circuitsLength = 0;
	private int circuitsIndex = 0;
	private String op = "";
	
	// GETTERS AND SETTERS
	
	public String getOp() { return op; }
	public void setOp(String oper) { op = oper; }
	
	public boolean getCircuitBtnValue() { return circuitBtnClicked; }
	public void setCircuitBtnValue(boolean cBtn) { circuitBtnClicked = cBtn; }

	public boolean getWireBtnValue() { return wireBtnClicked; }
	public void setWireBtnValue(boolean wBtn) { wireBtnClicked = wBtn; }
	
	public boolean getOutputBtnValue() { return outputPinClicked; }
	public void setOutputBtnValue(boolean oBtn) { outputPinClicked = oBtn; }
	
	public boolean getInputBtnValue() { return inputPinClicked; }
	public void setInputBtnValue(boolean iBtn) { inputPinClicked = iBtn; }
	
	public int getInputPin() { return inputPin; }
	public void setInputPin(int iPin) { inputPin = iPin; }
	
	public boolean getClickedOnSurface() { return clickedOnSurface; }
	public void setClickedOnSurface(boolean clicked) { clickedOnSurface = clicked; }
	
	
	// FUNCTIONS
	
	private void deleteArr(Object [] arr, int len) {
		for(int i = 0; i < len; i++) {
			arr[i] = null;
		}
	}
	
	
	public void reset() {
		
		deleteArr(inputPins, inputPinsLength);
		deleteArr(outputPins, outputPinsLength);
		deleteArr(connectors, connectorsLength);
		deleteArr(circuits, circuitsLength);
		deleteArr(wires, wiresLength);
		setOp("");
		setClickedOnSurface(false);
		setCircuitBtnValue(false);
		setWireBtnValue(false);
		setOutputBtnValue(false);
		setInputBtnValue(false);
		setInputPin(-1);
		
		count = 0;
		inputPinsLength = 0;
		inputPinsIndex = 0;
		outputPinsLength = 0;
		outputPinsIndex = 0;
		connectorsLength = 0;
		connectorsIndex = 0;
		wiresLength = 0;
		wiresIndex = 0;
		circuitsLength = 0;
		circuitsIndex = 0;
		
		oldX = 0;
		oldY = 0; 
		
		
	}
	
	public void write(FileWriter wr, Object [] arr, int num ,int len) throws IOException {
		
		boolean flag = false;
		
		for(int i = 0; i < len; i++) {
			if(num == 1) {
				if(!flag) {
					wr.write("// CIRCUITS");
					wr.write("\n");
					flag = true;
				}
				String [] array = ((circuit) arr[i]).getElements();
				for(int j = 0; j < array.length; j++){
					wr.write("" + array[j]);
				}
				wr.write("\n");
			}
			else if(num == 2){
				if(!flag) {
					wr.write("// CONNECTORS");
					wr.write("\n");
					flag = true;
				}
				String [] array = ((connector) arr[i]).getElements();
				for(int j = 0; j < array.length; j++){
					wr.write("" + array[j]);
				}
				wr.write("\n");
			}
			else if(num == 3){
				if(!flag) {
					wr.write("// INPUTPINS");
					wr.write("\n");
					flag = true;
				}
				String [] array = ((inputPin) arr[i]).getElements();
				for(int j = 0; j < array.length; j++){
					wr.write("" + array[j]);
				}
				wr.write("\n");
			}
			else if(num == 4){
				if(!flag) {
					wr.write("// OUTPUTPINS");
					wr.write("\n");
					flag = true;
				}
				String [] array = ((outputPin) arr[i]).getElements();
				for(int j = 0; j < array.length; j++){
					wr.write("" + array[j]);
				}
				wr.write("\n");
			}
			else if(num == 5){
				if(!flag) {
					wr.write("// WIRES");
					wr.write("\n");
					flag = true;
				}
				String [] array = ((wire) arr[i]).getElements();
				for(int j = 0; j < array.length; j++){
					wr.write("" + array[j]);
				}
				wr.write("\n");
			}
		}
		wr.write("\n");
	}
	
	public File save(String path) throws IOException {
		
		File file = new File(path);
		FileWriter writer = new FileWriter(file);
		
		write(writer, circuits, 1, circuitsLength);
		write(writer, connectors, 2, connectorsLength);
		write(writer, inputPins, 3, inputPinsLength);
		write(writer, outputPins, 4, outputPinsLength);
		write(writer, wires, 5, wiresLength);
	    writer.close();
		
		
		return file;
	}
	
	public void addToArray(String [] arr, String element) {
		
		if(element == "CIRCUITS") {
			if(arr[1].trim().equals("NOT")) {
				
				circuits[circuitsIndex] = new circuit(Integer.parseInt(arr[0]), arr[1].trim(), Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
				connectors[connectorsIndex] = circuits[circuitsIndex].getOutConnector();
				connectorsLength++;
				connectorsIndex++;
				connectors[connectorsIndex] = circuits[circuitsIndex].getInputConnector(0);
				connectorsLength++;
				connectorsIndex++;
			}
			
			else {
				
				circuits[circuitsIndex] = new circuit(Integer.parseInt(arr[0]), arr[1].trim(), Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
				connectors[connectorsIndex] = circuits[circuitsIndex].getOutConnector();
				connectorsLength++;
				connectorsIndex++;
				connectors[connectorsIndex] = circuits[circuitsIndex].getInputConnector(0);
				connectorsLength++;
				connectorsIndex++;
				connectors[connectorsIndex] = circuits[circuitsIndex].getInputConnector(1);
				connectorsLength++;
				connectorsIndex++;
				//System.out.println(Integer.parseInt(arr[0]) + " + " + arr[1].trim() + " + " +  Integer.parseInt(arr[2]) + " + " +  Integer.parseInt(arr[3]));
			}
			
			circuitsLength++;
			circuitsIndex++;
		}
		
		else if(element == "INPUTPINS") {
			inputPins[inputPinsIndex] = new inputPin(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
			connectors[connectorsIndex] = inputPins[inputPinsIndex].getConnector();
			
			connectorsLength++;
			connectorsIndex++;
			
			inputPinsLength++;
			inputPinsIndex++;
		}
		
		else if(element == "OUTPUTPINS") {
			outputPins[outputPinsIndex] = new outputPin(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
			connectors[connectorsIndex] = outputPins[outputPinsIndex].getConnector();
			
			connectorsLength++;
			connectorsIndex++;
			
			outputPinsLength++;
			outputPinsIndex++;
		}
		
		else if(element == "WIRES") {
			wires[wiresIndex] = new wire(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
			connectors[connectorsIndex] = wires[wiresIndex].getStartConnector();
			connectorsLength++;
			connectorsIndex++;
			connectors[connectorsIndex] = wires[wiresIndex].getEndConnector();
			connectorsLength++;
			connectorsIndex++;
			
			
			wiresLength++;
			wiresIndex++;
		}
		
	}
	
	public drawArea() {
		
		setBounds(187, 62, 583, 357);
		setBackground(Color.WHITE);
		setVisible(true);
		
		addMouseListener(new MouseAdapter () {
			public void mouseClicked(MouseEvent e) {
				
				clickedOnSurface = true;
				
				if( (clickedOnSurface == true) && (circuitBtnClicked == true)) {
					
					if(op == "NOT") {
						circuits[circuitsIndex] = new circuit(1, op, e.getX(), e.getY());
						connectors[connectorsIndex] = circuits[circuitsIndex].getOutConnector();
						connectorsLength++;
						connectorsIndex++;
						connectors[connectorsIndex] = circuits[circuitsIndex].getInputConnector(0);
						connectorsLength++;
						connectorsIndex++;
					}
					
					else {
						circuits[circuitsIndex] = new circuit(2, op, e.getX(), e.getY());
						connectors[connectorsIndex] = circuits[circuitsIndex].getOutConnector();
						connectorsLength++;
						connectorsIndex++;
						connectors[connectorsIndex] = circuits[circuitsIndex].getInputConnector(0);
						connectorsLength++;
						connectorsIndex++;
						connectors[connectorsIndex] = circuits[circuitsIndex].getInputConnector(1);
						connectorsLength++;
						connectorsIndex++;
					}
					
					circuitsLength++;
					circuitsIndex++;
					clickedOnSurface = false;		
					
				}
				
				if( (clickedOnSurface == true) && (inputPinClicked == true) ) {
					
					inputPins[inputPinsIndex] = new inputPin(inputPin, e.getX(), e.getY());
					connectors[connectorsIndex] = inputPins[inputPinsIndex].getConnector();
					
					connectorsLength++;
					connectorsIndex++;
					
					inputPinsLength++;
					inputPinsIndex++;
					clickedOnSurface = false;
				}
				
				if( (clickedOnSurface == true) && (outputPinClicked == true) ) {
					
					outputPins[outputPinsIndex] = new outputPin(e.getX(), e.getY());
					connectors[connectorsIndex] = outputPins[outputPinsIndex].getConnector();
					
					connectorsLength++;
					connectorsIndex++;
					
					outputPinsLength++;
					outputPinsIndex++;
					clickedOnSurface = false;
				}
				
				if((wireBtnClicked == true) && (clickedOnSurface == true)) {
					
					count++;
					boolean flag = false;
					clickedOnSurface = false;
					
					if((count % 2 != 0)) {
					
						for(int g = 0; g < connectorsLength; g++) {
							
							if((e.getX() < (connectors[g].getX() + 20)) && (e.getX() > (connectors[g].getX()))){
								if((e.getY() < (connectors[g].getY() + 20)) && (e.getY() > (connectors[g].getY()))) {
									
									oldX = connectors[g].getX() + 5;
									oldY = connectors[g].getY() + 10;
									clickedOnSurface = false;
									flag = true;
									break;
								}
							}
						}
						
						if(!flag) {
							count--;
						}
					}
					
					if((count % 2 == 0)) {
						
						flag = false;
						clickedOnSurface = false;
						
						for(int g = 0; g < connectorsLength; g++) {
							if((e.getX() < (connectors[g].getX() + 20)) && (e.getX() > (connectors[g].getX() - 20))){
								if((e.getY() < (connectors[g].getY() + 20)) && (e.getY() > (connectors[g].getY() - 20))) {
									currentX = connectors[g].getX() + 12;
									currentY = connectors[g].getY() + 7;
									
									if(oldX != 0 && oldY != 0) {
										wires[wiresIndex] = new wire(oldX, oldY, currentX, currentY);
										connectors[connectorsIndex] = wires[wiresIndex].getStartConnector();
										connectorsLength++;
										connectorsIndex++;
										connectors[connectorsIndex] = wires[wiresIndex].getEndConnector();
										connectorsLength++;
										connectorsIndex++;
										
										
										wiresLength++;
										wiresIndex++;
										count = 0;
										
										clickedOnSurface = false;
										flag = true;
										break;
									}
								}
							}
						}
						
						
						if(!flag) {
							count--;
						}	
					}	
				}
			}
		});
		
		
		addMouseMotionListener(new MouseMotionAdapter () {
			public void mouseDragged(MouseEvent e) {
				
				currentX = e.getX();
				currentY = e.getY();
				
				//g2.drawLine(oldX, oldY, currentX, currentY);
				repaint();
				
			}
		});
		
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.BLUE);

		
		if(g != null) {
			
			
			for(int i = 0; i < connectorsLength; i++) {
				for( int j = 0; j < connectorsLength; j++) {
					
					if(connectors[i].equal(connectors[j])) {
						if((connectors[i].getStrength() > connectors[j].getStrength())) {
							connectors[j].setValue(connectors[i].getValue());
						}
					}
				}
			}
			
			
			for (int i = 0; i < wiresLength; i++) {
				if(wires[i] != null) {
					wires[i].updateWires();
					wires[i].draw(g2);
					
					repaint();
				}
			}
			
			
			for(int i = 0; i < circuitsLength; i++) {
				if(circuits[i] != null) {		
					circuits[i].findOutput();
					circuits[i].draw(g2);
					
					
					repaint();
				}
			}
			
			for(int i = 0; i < inputPinsLength; i++) {
				if(inputPins[i] != null) {
					inputPins[i].draw(g2);
					
					repaint();
				}
			}
			
			for(int i = 0; i < outputPinsLength; i++) {
				if(outputPins[i] != null) {
					outputPins[i].draw(g2);
					
					repaint();
				}
			}
			
			if(newClicked) {
				g2.setColor(Color.BLACK);
				g2.drawString("Bla", 300, 20);
			}
			
			
			/*for(int i = 0; i < connectorsLength; i++) {
				if(connectors[i] != null) {
					connectors[i].draw(g2);
					repaint();
				}
			}*/
			
			repaint();
		}
		

	}
	
}

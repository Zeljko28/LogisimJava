package logisim;

import java.awt.EventQueue;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import java.awt.Point;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;


public class logisim {

	private JFrame frame;
	private drawArea area;
	
	private boolean wireBtnClicked = false;
	private boolean clickedOnSurface = false;
	private boolean circuitClicked = false;
	private int wireBtnCount = 0;
	private int circuitORBtnCount = 0;
	private int circuitANDBtnCount = 0;
	private int circuitXORBtnCount = 0;
	private int circuitNOTBtnCount = 0;
	private int in0BtnCount = 0;
	private int in1BtnCount = 0;
	private int outBtnCount = 0;

	
	private boolean inputPinClicked = false;
	private boolean outputPinClicked = false;
	
	private int oldX, oldY, currentX, currentY;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					logisim window = new logisim();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public logisim() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 796, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JMenuBar menu = new JMenuBar();
		frame.setJMenuBar(menu);
		
		JMenu file = new JMenu("File");
		JMenu help = new JMenu("Help");
		menu.add(file);
		menu.add(help);
		
		JMenuItem new_file = new JMenuItem("New");
		new_file.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				area.newClicked = true;
				area.reset();
				frame.remove(area);
				area = new drawArea();
			    frame.getContentPane().add(area);
			    frame.revalidate();
			    area.repaint();
			}
		});


		file.add(new_file);
		
		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFrame parentFrame = new JFrame();
				File file = null;
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Open a project");
				
				String whichObj = "";
				
				int returnVal = fc.showOpenDialog(parentFrame);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fc.getSelectedFile();
				}
				
				try {
					Scanner x = new Scanner(file);
					x.useDelimiter("[\n]");
					while(x.hasNextLine()) {
						String data = x.nextLine();
						String [] arr = data.split(" ");
						if(arr.length > 1) {
							if(arr[1].trim().equals("CIRCUITS")) { whichObj = "CIRCUITS"; }
							else if(arr[1].trim().equals("CONNECTORS")) { whichObj = "CONNECTORS"; }
							else if(arr[1].trim().equals("INPUTPINS")) { whichObj = "INPUTPINS"; }
							else if(arr[1].trim().equals("OUTPUTPINS")) { whichObj = "OUTPUTPINS"; }
							else if(arr[1].trim().equals("WIRES")) { whichObj = "WIRES"; }
						
							else {
								if(whichObj == "CIRCUITS") { area.addToArray(arr, "CIRCUITS"); }
								else if(whichObj == "INPUTPINS") { area.addToArray(arr, "INPUTPINS"); }
								else if(whichObj == "OUTPUTPINS") { area.addToArray(arr, "OUTPUTPINS"); }
								else if(whichObj == "WIRES") { area.addToArray(arr, "WIRES"); }
							}
						}
					}
					
					x.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		file.add(open);
		
		JMenuItem saveAs = new JMenuItem("Save As");
		saveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFrame parentFrame = new JFrame();
				File file;
				try {
					file = area.save("C://Users//zeljk//Desktop//schema.txt");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				 
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");   
				 
				int userSelection = fileChooser.showSaveDialog(parentFrame);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
				}
			}
		});
		file.add(saveAs);
		
		
		
		
		
		area = new drawArea();
		frame.getContentPane().add(area);
		
		
		/*frame.remove(area);
		area = new drawArea();
	    frame.getContentPane().add(area);
	    frame.revalidate();*/
	    //area.repaint();
		
		
		JButton btnNOT = new JButton("NOT");
		btnNOT.setForeground(Color.BLACK);
		btnNOT.setBackground(Color.LIGHT_GRAY);
		btnNOT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				circuitClicked = true;
				circuitNOTBtnCount++;
				if (circuitNOTBtnCount % 2 != 0) {
					area.setCircuitBtnValue(true);
					area.setOp("NOT");
					btnNOT.setBackground(Color.DARK_GRAY);
					btnNOT.setForeground(Color.WHITE);
				}
				
				else if(circuitNOTBtnCount % 2 == 0) {
					area.setCircuitBtnValue(false);
					area.setOp("");
					btnNOT.setBackground(Color.LIGHT_GRAY);
					btnNOT.setForeground(Color.BLACK);
					circuitNOTBtnCount = 0;
				}
			}
		});
		btnNOT.setBounds(706, 33, 64, 23);
		frame.getContentPane().add(btnNOT);
		
		JButton btnXOR = new JButton("XOR");
		btnXOR.setBackground(Color.LIGHT_GRAY);
		btnXOR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				circuitClicked = true;
				circuitXORBtnCount++;
				if (circuitXORBtnCount % 2 != 0) {
					area.setCircuitBtnValue(true);
					area.setOp("XOR");
					btnXOR.setBackground(Color.DARK_GRAY);
					btnXOR.setForeground(Color.WHITE);
				}
				
				else if(circuitXORBtnCount % 2 == 0) {
					area.setCircuitBtnValue(false);
					area.setOp("");
					btnXOR.setBackground(Color.LIGHT_GRAY);
					btnXOR.setForeground(Color.BLACK);
					circuitXORBtnCount = 0;
				}
			}
		});
		btnXOR.setBounds(632, 33, 64, 23);
		frame.getContentPane().add(btnXOR);
		
		JButton btnOR = new JButton("OR");
		btnOR.setBackground(Color.LIGHT_GRAY);
		btnOR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				circuitClicked = true;
				circuitORBtnCount++;
				if (circuitORBtnCount % 2 != 0) {
					area.setCircuitBtnValue(true);
					area.setOp("OR");
					btnOR.setBackground(Color.DARK_GRAY);
					btnOR.setForeground(Color.WHITE);
				}
				
				else if(circuitORBtnCount % 2 == 0) {
					area.setCircuitBtnValue(false);
					area.setOp("");
					btnOR.setBackground(Color.LIGHT_GRAY);
					btnOR.setForeground(Color.BLACK);
					circuitORBtnCount = 0;
				}
			}
		});
		btnOR.setBounds(558, 33, 64, 23);
		frame.getContentPane().add(btnOR);
		
		JButton btnAND = new JButton("AND");
		btnAND.setBackground(Color.LIGHT_GRAY);
		btnAND.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				circuitClicked = true;
				circuitANDBtnCount++;
				if (circuitANDBtnCount % 2 != 0) {
					area.setCircuitBtnValue(true);
					area.setOp("AND");
					btnAND.setBackground(Color.DARK_GRAY);
					btnAND.setForeground(Color.WHITE);
				}
				
				else if(circuitANDBtnCount % 2 == 0) {
					area.setCircuitBtnValue(false);
					area.setOp("");
					btnAND.setBackground(Color.LIGHT_GRAY);
					btnAND.setForeground(Color.BLACK);
					circuitANDBtnCount = 0;
				}
			}
		});
		btnAND.setBounds(484, 33, 64, 23);
		frame.getContentPane().add(btnAND);
		
		
		JButton btnWire = new JButton("Wire");
		btnWire.setBackground(Color.LIGHT_GRAY);
		btnWire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				wireBtnCount++;
				if (wireBtnCount % 2 != 0) {
					area.setWireBtnValue(true);
					btnWire.setBackground(Color.DARK_GRAY);
					btnWire.setForeground(Color.WHITE);
				}
				
				else if(wireBtnCount % 2 == 0) {
					area.setWireBtnValue(false);
					btnWire.setBackground(Color.LIGHT_GRAY);
					btnWire.setForeground(Color.BLACK);
					wireBtnCount = 0;
				}
			}
		});
		btnWire.setBounds(187, 33, 64, 23);
		frame.getContentPane().add(btnWire);
		
		JButton btnNewButton_1 = new JButton("Out");
		btnNewButton_1.setBackground(Color.LIGHT_GRAY);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				outputPinClicked = true;
				outBtnCount++;
				
				if (outBtnCount % 2 != 0) {
					area.setOutputBtnValue(true);
					btnNewButton_1.setBackground(Color.DARK_GRAY);
					btnNewButton_1.setForeground(Color.WHITE);
				}
				
				else if(outBtnCount % 2 == 0) {
					area.setOutputBtnValue(false);
					btnNewButton_1.setBackground(Color.LIGHT_GRAY);
					btnNewButton_1.setForeground(Color.BLACK);
					outBtnCount = 0;
				}
				
			}
		});
		btnNewButton_1.setBounds(410, 33, 64, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("In 0");
		btnNewButton_2.setBackground(Color.LIGHT_GRAY);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inputPinClicked = true;
				in0BtnCount++;
				
				if (in0BtnCount % 2 != 0) {
					area.setInputBtnValue(true);
					area.setInputPin(0);
					btnNewButton_2.setBackground(Color.DARK_GRAY);
					btnNewButton_2.setForeground(Color.WHITE);
				}
				
				else if(in0BtnCount % 2 == 0) {
					area.setInputBtnValue(false);
					area.setInputPin(-1);
					btnNewButton_2.setBackground(Color.LIGHT_GRAY);
					btnNewButton_2.setForeground(Color.BLACK);
					in0BtnCount = 0;
				}
			}
		});
		btnNewButton_2.setBounds(336, 33, 64, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("In 1");
		btnNewButton_3.setBackground(Color.LIGHT_GRAY);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inputPinClicked = true;
				in1BtnCount++;
				
				if (in1BtnCount % 2 != 0) {
					area.setInputBtnValue(true);
					area.setInputPin(1);
					btnNewButton_3.setBackground(Color.DARK_GRAY);
					btnNewButton_3.setForeground(Color.WHITE);
				}
				
				else if(in1BtnCount % 2 == 0) {
					area.setInputBtnValue(false);
					area.setInputPin(-1);
					btnNewButton_3.setBackground(Color.LIGHT_GRAY);
					btnNewButton_3.setForeground(Color.BLACK);
					in1BtnCount = 0;
				}
			}
		});
		btnNewButton_3.setBounds(262, 33, 64, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\zeljk\\eclipse-workspace\\logisim\\imgs\\camera3.png"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Point p = area.getLocationOnScreen();
					Robot robot = new Robot();
					Dimension dim = area.getSize();
					Rectangle rect = new Rectangle(p, dim);
					BufferedImage img = robot.createScreenCapture(rect);
					ImageIO.write(img, "JPG", new File("C://Users//zeljk//Desktop//schema.JPG"));
					
					
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(142, 33, 35, 23);
		frame.getContentPane().add(btnNewButton);
		

		
	}
}

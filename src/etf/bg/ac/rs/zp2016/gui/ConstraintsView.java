package etf.bg.ac.rs.zp2016.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import etf.bg.ac.rs.zp2016.alg.CertificateClass;

public class ConstraintsView extends JFrame{
	private GenerateKeyWind myGKW;
	private JButton confirmB,exitB;
	public JComboBox<String> yesNoConstrains = new JComboBox<String>();
	public JComboBox<String> yesNoCA= new JComboBox<String>();
	public TextField myTextFiled;
	Label errorLabel = new Label("");
	Label tempLabel = new Label("");
	
	public void fillYesNo(){
		yesNoConstrains.addItem("No");
		yesNoConstrains.addItem("Yes");
		yesNoCA.addItem("No");
		yesNoCA.addItem("Yes");
	
		yesNoConstrains.setBackground(Color.ORANGE);
		yesNoCA.setBackground(Color.ORANGE);
		
	}
	
	public Panel makeGred(){
		Panel plate = new Panel(new GridLayout(5, 2));
		
		fillYesNo();
		Label l1 = new Label("Critical : ", Label.LEFT);
		l1.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l1);
		plate.add(yesNoConstrains);
		
		Label l2 = new Label("Subject is CA : ", Label.LEFT);
		l2.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l2);
		plate.add(yesNoCA);
		plate.add(errorLabel);
		plate.add(tempLabel);
		
		Label l3 = new Label("Path Lenght Constraint: ", Label.LEFT);
		l3.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l3);
		myTextFiled = new TextField();
		plate.add(myTextFiled);
		
		confirmB = new JButton ("Confirm");
		confirmB.setSize(2,2);
		confirmB.setFont(new Font(null,Font.BOLD, 15));
		confirmB.setBackground(Color.ORANGE);
		exitB = new JButton("EXIT");
		exitB.setSize(2,2);
		exitB.setFont(new Font(null,Font.BOLD, 15));
		exitB.setBackground(Color.ORANGE);
		plate.add(confirmB);
		plate.add(exitB);
		confirmB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedCritical = yesNoConstrains.getSelectedIndex();
				if(selectedCritical == 1) myGKW.cert.setBasicCritical(true);
				int selectedCA = yesNoCA.getSelectedIndex();
				if( selectedCA == 1) myGKW.cert.setCA(true);
				boolean exCat = false;
				try{				
					
					myGKW.cert.setPathLength(Integer.parseInt(myTextFiled.getText()));
					myGKW.cert.setBasicExt(true);
				}catch( NumberFormatException exc){
					exCat = true;
					errorLabel.setText("Path lenght must be integer!!");
					errorLabel.setFont(new Font(null,Font.BOLD, 10));
					errorLabel.setBackground(Color.RED);
					tempLabel.setBackground(Color.RED);
				}
				if(!exCat){
				myGKW.setEnabled(true);
				dispose();
				}
			}
		});
		
		exitB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				myGKW.setEnabled(true);
				dispose();
			}
		});
		
		return plate;
	}
	
	public ConstraintsView(GenerateKeyWind myGKW) {
		super("Constraints extension");
		setBounds(300,150,500,200);
		setResizable(false);
		errorLabel = new Label(" ", Label.LEFT);
		errorLabel.setFont(new Font(null,Font.BOLD, 10));
		this.myGKW = myGKW;
		add(makeGred(),BorderLayout.NORTH);
		
		
	}
}

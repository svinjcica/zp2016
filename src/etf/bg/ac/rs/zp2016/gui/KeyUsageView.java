package etf.bg.ac.rs.zp2016.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class KeyUsageView extends JFrame{
	 private GenerateKeyWind myGKW;
     private  ArrayList<JCheckBox> chBoxList = new ArrayList<JCheckBox>();
 	 private JButton confirmB,exitB;
     
     public void createChBox(){
    	 chBoxList.add(new JCheckBox("Certificate signing"));
    	 chBoxList.add(new JCheckBox("CRL sign"));
    	 chBoxList.add(new JCheckBox("Data  encipherment"));
    	 chBoxList.add(new JCheckBox("Decipher only"));
    	 chBoxList.add(new JCheckBox("Digital signature"));
    	 chBoxList.add(new JCheckBox("Encipher only"));
    	 chBoxList.add(new JCheckBox("Key agreement"));
    	 chBoxList.add(new JCheckBox("Key encipherment"));
    	 chBoxList.add(new JCheckBox("Non repudiation"));
     }
     
     public Panel createPanelB(){
    	Panel plate = new Panel(new GridLayout(1, 2));
    	 
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
				int i = 0;
			for(JCheckBox j: chBoxList){
				if(j.isSelected()){
					myGKW.cert.setKeyExt(true);
					myGKW.cert.keyUsagePolicies[i] = true;
				}
				else{
					myGKW.cert.keyUsagePolicies[i] = false;
				}
				i++;
			}
			myGKW.setEnabled(true);
			dispose();
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
     
     public Panel createPanel(){
    	 Panel plate = new Panel(new GridLayout(9, 1));
    	 for(JCheckBox j: chBoxList){
    		 plate.add(j,BorderLayout.CENTER);
    		 j.setFont(new Font(null,Font.BOLD, 15));
    	 }
    	 return plate;
     }
     
     public KeyUsageView(GenerateKeyWind myGKW) {
    	super("Constraints extension");
 		setBounds(300,150,500,250);
 		setResizable(false);
 		createChBox();
 		add(createPanel(),BorderLayout.CENTER);
 		add(createPanelB(),BorderLayout.SOUTH);
 		this.myGKW = myGKW;
 		
     }
}

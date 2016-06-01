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
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class AlternativeNameView extends JFrame{
	 private  ArrayList<JRadioButton> radioBList = new ArrayList<JRadioButton>();
	 private JButton confirmB,exitB;
	 private GenerateKeyWind myGKW;
	 private TextField myTextFiled;
	 public JComboBox<String> yesNoCritical= new JComboBox<String>();
	 private Label errorLabel = new Label("");
	 private ButtonGroup group;
	 
	 private Panel initRadioB(){ 
		 radioBList.add(new JRadioButton("DNS Name"));
		 radioBList.add(new JRadioButton("IP Address"));
		 radioBList.add(new JRadioButton("RFC 822 Name"));
		 radioBList.add(new JRadioButton("URI"));
		 radioBList.add(new JRadioButton("OID"));
		 Label crLabel = new Label("Critical :");
		 crLabel.setFont(new Font(null,Font.BOLD, 15));
		 yesNoCritical.addItem("No");
	 	 yesNoCritical.addItem("Yes");
	 	 yesNoCritical.setBackground(Color.ORANGE);
		 group = new ButtonGroup();
		 for(JRadioButton jb: radioBList)
			 group.add(jb);
		 
		 Panel plate = new Panel(new GridLayout(7, 2));
		 plate.add(crLabel);
		 plate.add(yesNoCritical);
		 Label l1 = new Label("Choose: ");
		 l1.setFont(new Font(null,Font.BOLD, 15));
		 plate.add(l1);
		 plate.add(new Label());
		 for(JRadioButton jb: radioBList)
			 plate.add(jb);
		 plate.add(new Label());
		 Label l2 = new Label("Type name value: ",Label.RIGHT);
		 l2.setFont(new Font(null,Font.BOLD, 15));
		 plate.add(l2);
		 myTextFiled = new TextField();
		 plate.add(myTextFiled);
		 plate.add(errorLabel);
		 plate.add(new Label());
		 return plate;
		 
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
					if( myTextFiled.getText().length() == 0){
						errorLabel.setText("You must input name!! ");
						errorLabel.setBackground(Color.RED);
					}
					else{
						try {
							int selectedCritical = yesNoCritical.getSelectedIndex();
							if(selectedCritical == 1) myGKW.cert.setAltNamesCritical(true);
							int selectedValue = 0;
							myGKW.cert.setAltNameExt(true);
							for(JRadioButton r: radioBList){
								if(r.isSelected()) break;
								selectedValue++;
							}
							System.out.println(""+selectedValue+myTextFiled.getText());
							myGKW.cert.subjectAltNames(selectedValue, myTextFiled.getText());
							myGKW.setEnabled(true);
							errorLabel.setText("");
							errorLabel.setBackground(Color.GRAY);
							dispose();
						
						}catch (IOException e1) {
							// TODO Auto-generated catch block
							errorLabel.setText("Wrong input format!!");
							errorLabel.setBackground(Color.RED);
						}
					
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
	     
	 
	 public AlternativeNameView(GenerateKeyWind myGKW) {
	    	super("Alternative names");
	 		setBounds(300,150,500,250);
	 		setResizable(false);
	 		add(initRadioB(),BorderLayout.CENTER);
	 		add(createPanelB(),BorderLayout.SOUTH);
	 		this.myGKW = myGKW;
	 	//	errorLabel = new Label(" ", Label.LEFT);
		//	errorLabel.setBackground(Color.ORANGE);
			errorLabel.setFont(new Font(null,Font.BOLD, 15));
	 }
	
}

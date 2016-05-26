package etf.bg.ac.rs.zp2016.gui;

import etf.bg.ac.rs.zp2016.alg.*;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;



public class GenerateKeyWind  extends JFrame{
	public TextField keysize, period, serNum,CN, OU, O, L, ST, C, E, constraints, issuerAlternativeName, keyUsage;
	private JButton confirmB;
	public JComboBox<String> solutionBox = new JComboBox<String>();
	public JComboBox<String> yesNoConstrains = new JComboBox<String>();
	public JComboBox<String> yesNoIssuerAlternativeName= new JComboBox<String>();
	public JComboBox<String> yesNoKeyUsage = new JComboBox<String>();
	public CertificateClass cert;
	public KeyPairView secW;
	Label errorLabel = new Label("BILO STA");
	
	
	public Panel clientText(int i){
		Panel plate = new Panel(new GridLayout(1, 1));
		
		Label myLabel = new Label();
		if( i == 1)
			myLabel = new Label("User information: ", Label.LEFT);
		else if(i == 2)
			myLabel = new Label("Optional extension: ", Label.LEFT);
		else if(i==3)
			myLabel = new Label("Input informartion: ", Label.LEFT);
		myLabel.setFont(new Font(null,Font.BOLD, 15));
		myLabel.setBackground(Color.ORANGE);
		plate.add(myLabel);
		return plate;
	}
	
	public Panel label1(){
		
		solutionBox.setBackground(Color.ORANGE);
		solutionBox.addItem("v3");
		solutionBox.setEditable(false);
		Panel plate = new Panel(new GridLayout(17, 2));
		
		plate.add(clientText(3));
		plate.add(clientText(0));
		
		Label l1 = new Label("Enter key size: ", Label.LEFT);
		l1.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l1);
		keysize = new TextField();
		plate.add(keysize);
		
		Label l2 = new Label("Enter certificate version: ", Label.LEFT);
		l2.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l2);
		plate.add(solutionBox);
		
		Label l3 = new Label("Enter validity period: \n(in days from today)", Label.LEFT);
		l3.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l3);
		period = new TextField();
		plate.add(period);
		
		
		Label l4 = new Label("Enter serial number", Label.LEFT);
		l4.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l4);
		serNum = new TextField();
		plate.add(serNum);
		
		plate.add(clientText(1));
		plate.add(clientText(0));
		

		
		Label l11 = new Label("CN : ", Label.RIGHT);
		l11.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l11);
		CN = new TextField();
		plate.add(CN);
		
		Label l12 = new Label("OU : ", Label.RIGHT);
		l12.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l12);
		OU = new TextField();
		plate.add(OU);
		
		Label l13 = new Label("O :", Label.RIGHT);
		l13.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l13);
		O = new TextField();
		plate.add(O);
		
		
		Label l14 = new Label("L :", Label.RIGHT);
		l14.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l14);
		L = new TextField();
		plate.add(L);
		
		Label l5 = new Label("ST :", Label.RIGHT);
		l5.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l5);
		ST = new TextField();
		plate.add(ST);
		
		Label l6 = new Label("C :", Label.RIGHT);
		l6.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l6);
		C = new TextField();
		plate.add(C);
		
		Label l7 = new Label("E :", Label.RIGHT);
		l7.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l7);
		E = new TextField();
		plate.add(E);
		
		plate.add(clientText(2));
		plate.add(clientText(0));
		
		return plate;
	}
	
	public void fillYesNo(){
		yesNoConstrains.addItem("No");
		yesNoConstrains.addItem("Yes");
		yesNoIssuerAlternativeName.addItem("No");
		yesNoIssuerAlternativeName.addItem("Yes");
		yesNoKeyUsage.addItem("No");
		yesNoKeyUsage.addItem("Yes");
		yesNoConstrains.setBackground(Color.ORANGE);
		yesNoIssuerAlternativeName.setBackground(Color.ORANGE);
		yesNoKeyUsage.setBackground(Color.ORANGE);
	}
	
	public Panel optionalEx(){
		Panel plate = new Panel(new GridLayout(3, 4));
		
		fillYesNo();
		
		Label l8 = new Label("Basic constraints :", Label.RIGHT);
		l8.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l8);
		constraints = new TextField();
		plate.add(constraints);
		Label l81 = new Label("Critical :", Label.RIGHT);
		l81.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l81);
		plate.add(yesNoConstrains);
		
		Label l9 = new Label("Issuer alternative name :", Label.RIGHT);
		l9.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l9);
		issuerAlternativeName = new TextField();
		plate.add(issuerAlternativeName);
		Label l91 = new Label("Critical :", Label.RIGHT);
		l91.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l91);
		plate.add(yesNoIssuerAlternativeName);
		
		Label l10 = new Label("Key usage :", Label.RIGHT);
		l10.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l10);
		keyUsage = new TextField();
		plate.add(keyUsage);
		Label l101 = new Label("Critical :", Label.RIGHT);
		l101.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l101);
		plate.add(yesNoKeyUsage);
		
		return plate;
	}
	
	public Panel  plateNextB(){
		Panel plate = new Panel(new GridLayout(1, 2));
		confirmB = new JButton ("Confirm");
		confirmB.setSize(2,2);
		confirmB.setFont(new Font(null,Font.BOLD, 15));
		plate.add(errorLabel, BorderLayout.NORTH);
		confirmB.setBackground(Color.ORANGE);
		plate.add(confirmB);
		
		confirmB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(keysize.getText().length()==0|| period.getText().length()==0 || serNum.getText().length()==0 || CN.getText().length()==0 || OU.getText().length()==0 || O.getText().length()==0|| L.getText().length()==0 || ST.getText().length()==0 || C.getText().length()==0|| E.getText().length()==0){
						
					errorLabel.setText("You didn't input all value!!");
					errorLabel.setBackground(Color.RED);
					
				}
				else{
					try{
					cert = new CertificateClass();
					
					cert.setSerialNum(Integer.parseInt(serNum.getText()));
					cert.setLength(Integer.parseInt(keysize.getText()));
					cert.setDays(Integer.parseInt(period.getText()));
					cert.setCN(CN.getText());
					cert.setOU(OU.getText());
					cert.setO(O.getText());
					cert.setL(L.getText());
					cert.setST(ST.getText());
					cert.setC(C.getText());
					cert.setE(E.getText());
					secW = new KeyPairView(cert);
					secW.setVisible(true);
					dispose();
				}catch( NumberFormatException exc){
					System.out.println("uso");
					errorLabel.setText("Serial number, Key size and Period must be integer!!");
					errorLabel.setFont(new Font(null,Font.BOLD, 10));
					errorLabel.setBackground(Color.RED);
				}
				}
			}
		});
		
		return plate;
	}


	public GenerateKeyWind() {
		super("X.509 Authentication Service: GENERATE NEW KEY PAIR");
		setBounds(300,150,700,580);
		setResizable(false);
		errorLabel = new Label(" ", Label.LEFT);
		errorLabel.setBackground(Color.ORANGE);
		errorLabel.setFont(new Font(null,Font.BOLD, 15));
		add(label1(),BorderLayout.NORTH);
		add(optionalEx(),BorderLayout.EAST);
		
		add(plateNextB(),BorderLayout.SOUTH);
		// TODO Auto-generated constructor stub
	}
}

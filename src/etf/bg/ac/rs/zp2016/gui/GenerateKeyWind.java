package etf.bg.ac.rs.zp2016.gui;

import etf.bg.ac.rs.zp2016.alg.*;
import java.security.InvalidParameterException;

import javax.swing.JOptionPane;
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
	public TextField keysize, period, serNum,CN, OU, O, L, ST, C, E;
	private JButton confirmB, constraintsB,issuerAlternativeNameB, keyUsageB;
	public ConstraintsView constraintsV;
	public JComboBox<String> solutionBox = new JComboBox<String>();
	public JComboBox<String> yesNoConstrains = new JComboBox<String>();
	public JComboBox<String> yesNoIssuerAlternativeName= new JComboBox<String>();
	public JComboBox<String> yesNoKeyUsage = new JComboBox<String>();
	public CertificateClass cert;
	public KeyPairView secW;
	public KeyUsageView keyUsageV;
	public AlternativeNameView altNameV;
	Label errorLabel = new Label("");
	private FirstWind fw;
	
	
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
		Panel plate = new Panel(new GridLayout(16, 2));
		
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
		Panel plate = new Panel(new GridLayout(4, 3));
		
		constraintsB = new JButton ("Set basic constraints");
		constraintsB.setSize(2,2);
		constraintsB.setBackground(Color.orange);
		constraintsB.setFont(new Font(null,Font.BOLD, 15));
		plate.add(constraintsB);
		plate.add(new Label());
		plate.add(new Label());
		constraintsV = new ConstraintsView(this);
		constraintsB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				setEnabled(false);
				constraintsV.setVisible(true);
								
			}
		});
	
		
		issuerAlternativeNameB = new JButton ("Set issuer alternative name ");
		issuerAlternativeNameB.setSize(2,2);
		issuerAlternativeNameB.setBackground(Color.orange);
		issuerAlternativeNameB.setFont(new Font(null,Font.BOLD, 15));
		plate.add(new Label());
		plate.add(issuerAlternativeNameB);
		plate.add(new Label());
		altNameV = new AlternativeNameView(this);
		issuerAlternativeNameB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				setEnabled(false);
				
				altNameV.setVisible(true);
			}
		});
		
	
		
		keyUsageB = new JButton ("Set key usage");
		keyUsageB.setSize(2,2);
		keyUsageB.setBackground(Color.orange);
		keyUsageB.setFont(new Font(null,Font.BOLD, 15));
		plate.add(new Label());
		plate.add(new Label());
		plate.add(keyUsageB);
		keyUsageV = new KeyUsageView(this);
		keyUsageB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				setEnabled(false);
				keyUsageV.setVisible(true);
				
			}
		});
		
		
		
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
				
				if(keysize.getText().length()==0 || period.getText().length()==0 || serNum.getText().length()==0 || CN.getText().length()==0 || OU.getText().length()==0 || O.getText().length()==0|| L.getText().length()==0 || ST.getText().length()==0 || C.getText().length()==0|| E.getText().length()==0){
						
					errorLabel.setText("You didn't input all value!!");
					errorLabel.setBackground(Color.RED);
					
				}
				else{
					try{
						
					
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
						
						errorLabel.setText("Serial number, Key size and Period must be integer!!");
						errorLabel.setFont(new Font(null,Font.BOLD, 10));
						errorLabel.setBackground(Color.RED);
					}catch (InvalidParameterException e2) {
						errorLabel.setText("RSA keys must be >= 512 bits long <= 16384");
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
		cert = new CertificateClass();
		add(plateNextB(),BorderLayout.SOUTH);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	fw = new FirstWind();
				 fw.setVisible(true);
				 fw.ex = true;
				 dispose();
		      
		    }
		});
		
	}
}

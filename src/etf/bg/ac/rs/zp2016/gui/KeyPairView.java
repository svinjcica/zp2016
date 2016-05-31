package etf.bg.ac.rs.zp2016.gui;

import etf.bg.ac.rs.zp2016.alg.*;
import java.io.IOException;
import java.security.cert.*;
import java.security.GeneralSecurityException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.plaf.metal.MetalBorders.TextFieldBorder;

public class KeyPairView extends JFrame{
	private TextArea txt ;
	//public static ArrayList<CertificateElemList> allCert = new ArrayList<CertificateElemList>();
	public boolean ex = true;
	private JButton next;
	private TextField textF;
	private CertificateClass cert;
	private StorageClass sc;
	private Label errorLabel = new Label("");
	protected X509Certificate cX509;
	private FirstWind fw;
	
	private Panel plateTxtArea(){
		Panel plate = new Panel();
		txt =  new TextArea();
		plate.add(txt,BorderLayout.CENTER);		
		return plate;
	}
	
	public Panel  plateSaveB(){
		Panel plate = new Panel(new GridLayout(2, 2));
		
		next = new JButton ("Save");
		next.setSize(2,2);
		next.setBackground(Color.ORANGE);
		next.setFont(new Font(null,Font.BOLD, 15));
		
		Label l1 = new Label("Name and save your certificate :", Label.RIGHT);
		l1.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l1);
		
		textF = new TextField();
		plate.add(textF);
		
		plate.add(errorLabel, BorderLayout.NORTH);
		plate.add(next);
		
		
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(textF.getText().length()!=0){
			//	allCert.add(new CertificateElemList(textF.getText(), cert));
				 sc = new StorageClass();
				  sc.setCertificate(cX509);
				  sc.setPair(cert.getPair());
				   try {
				    sc.saveKey(textF.getText());
				    fw = new FirstWind();
					fw.setVisible(true);
					fw.ex = true;
					dispose();
					
			}catch(Exception ex){
				errorLabel.setText(ex.toString());
				errorLabel.setFont(new Font(null,Font.BOLD, 10));
				errorLabel.setBackground(Color.RED);
			}
		
			}
			else{
				errorLabel.setText("You must input name!!");
				errorLabel.setFont(new Font(null,Font.BOLD, 10));
				errorLabel.setBackground(Color.RED);
				}
				}
		});
		return plate;
	}
	
	public KeyPairView(CertificateClass cert) {
		
		super("X.509 Authentication Service: GENERATE NEW KEY PAIR");

		this.cert = cert;
		setBounds(300,150,700,400);
		setResizable(false);
		errorLabel = new Label(" ", Label.LEFT);
		errorLabel.setBackground(Color.ORANGE);
		errorLabel.setFont(new Font(null,Font.BOLD, 15));
		txt =  new TextArea();
		add(txt);
		add(plateSaveB(),BorderLayout.SOUTH );
		
		
		try {
			cX509 = cert.generateCertificate();
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}
		this.txt.append(cX509.toString());
		
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent d){
				if(ex) dispose();
				else setVisible(false);
			}
		});
	}
}

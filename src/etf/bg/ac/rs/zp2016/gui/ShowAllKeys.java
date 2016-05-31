
package etf.bg.ac.rs.zp2016.gui;

import etf.bg.ac.rs.zp2016.alg.*;
import java.io.IOException;
import java.security.cert.*;
import java.security.GeneralSecurityException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
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

public class ShowAllKeys extends JFrame{
	private TextArea txt ;
	//public static ArrayList<CertificateElemList> allCert = new ArrayList<CertificateElemList>();
	public boolean ex = true;
	private JButton exitB;
	private TextField textF;
	private StorageClass sc;
	private Label errorLabel = new Label("");
	protected X509Certificate cX509;
	private FirstWind fw;
	private static final String name = "MyKeyStorage.p12";
	private static final String pass = "mystorage";
	
	private Panel plateTxtArea(){
		Panel plate = new Panel();
		txt =  new TextArea();
		plate.add(txt,BorderLayout.CENTER);		
		return plate;
	}
	
	public Panel  plateSaveB(){
		Panel plate = new Panel(new GridLayout(1, 2));
		
		
		
		
		plate.add(errorLabel, BorderLayout.NORTH);
		
		exitB = new JButton("EXIT");
		exitB.setSize(2,2);
		exitB.setFont(new Font(null,Font.BOLD, 15));
		exitB.setBackground(Color.ORANGE);
		plate.add(exitB);
		exitB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 fw = new FirstWind();
				 fw.setVisible(true);
				 fw.ex = true;
				 dispose();
			
			}
		});
		return plate;
	}
	
	public ShowAllKeys() {
		
		super("X.509 Authentication Service: VIEW DETAILS OF ALL EXISTING PAIRS");

		setBounds(300,150,700,400);
		setResizable(false);
		errorLabel = new Label(" ", Label.LEFT);
		errorLabel.setBackground(Color.ORANGE);
		errorLabel.setFont(new Font(null,Font.BOLD, 15));
		txt =  new TextArea();
		add(txt);
		add(plateSaveB(),BorderLayout.SOUTH );
		sc = new StorageClass(name, pass);
		
		try {
			this.txt.append(sc.viewAllKeys(name, pass));
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent d){
				if(ex) dispose();
				else setVisible(false);
			}
		});
	}
}

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
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import etf.bg.ac.rs.zp2016.alg.StorageClass;


public class ImportView extends JFrame{
	private StorageClass sc;
	private JButton open;
	private JFileChooser fc;
	private Label errorLabel = new Label("");
	private TextField txt, pass, name;
	private JButton importB, exitB, confirmPass;
	private FirstWind fw;
	public JComboBox<String> solutionBox = new JComboBox<String>();
	
	
	public void fillAllSolutions(String storeName,String storePass){
		ArrayList<String> allSolutions = new ArrayList<String>();
		System.out.println(storeName+"  "+storePass);
		  sc = new StorageClass(txt.getText(),pass.getText());
		allSolutions = sc.viewKeyAlies(storeName,storePass); 
		
		solutionBox.setBackground(Color.ORANGE);
		if(allSolutions != null)
			for(int i = 0; i < allSolutions.size(); i++){	
				solutionBox.addItem(allSolutions.get(i));
				solutionBox.addActionListener(new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent event) {
			            JComboBox<String> combo = (JComboBox<String>) event.getSource();
			              
			        }
			    });		
		}
	 }
	
	private String chooseFile(){
		open = new JButton();
		fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("my own home directory"));
		fc.setDialogTitle("Choose file");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PKCS #12", "p12", "pkcs12");
		fc.setFileFilter(filter);
		if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION){
		
		}
		if(fc.getSelectedFile()!= null) {
			System.out.println(fc.getSelectedFile().getAbsolutePath());
			return fc.getSelectedFile().getAbsolutePath();
			
		}
		 return null;
	}
	
	private static String getFileExtension(String fileName) {
	        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
	        return fileName.substring(fileName.lastIndexOf(".")+1);
	        else return null;
	    }
	
	public Panel plateImportB(){
		Panel plate = new Panel(new GridLayout(1, 2));
		importB = new JButton ("Import");
		importB.setSize(2,2);
		importB.setBackground(Color.orange);
		importB.setFont(new Font(null,Font.BOLD, 15));
		plate.add(importB);
		importB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				//setEnabled(false);
				
				if(txt.getText().length() == 0) {
					errorLabel.setText("You must choose file!!");
					errorLabel.setBackground(Color.RED);
				}
				else if(pass.getText().length() == 0){
					errorLabel.setText("You must input password!!");
					errorLabel.setBackground(Color.RED);
				}
				else{
					String extStr = getFileExtension(pass.getText());
					if( extStr == null){
						errorLabel.setText("You must choose file! ");
						errorLabel.setBackground(Color.RED);
					}
					else{
						if(!extStr.equals(".p12")) {
							errorLabel.setText("File extension must be .p12! ");
							errorLabel.setBackground(Color.RED);	
						}
						else {
							//import kljuceva
							 //prvo napravimo novi fajl za import
							
							  try {
								sc.importKeys(sc.getKeyStoreName(),sc.getKeyStorePass());
								fw = new FirstWind();
								fw.setVisible(true);
								fw.ex = true;
								dispose();
							} catch ( KeyStoreException| NoSuchAlgorithmException | CertificateException ex) {
								// TODO Auto-generated catch block
								ex.printStackTrace();
							} catch (UnrecoverableKeyException | IOException  e1) {
								// TODO Auto-generated catch block
								errorLabel.setText("Wrong password! ");
								errorLabel.setBackground(Color.RED);
							}
							
						}
					}
				}
				// fje za eksport
			}
		});
		
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
	
	public Panel plateChoose(){
		Panel plate = new Panel(new GridLayout(5, 3));
		JButton chooseB = new JButton("Browse");
		Label l1 = new Label("Choose file: ", Label.LEFT);
		l1.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l1);
		plate.add(txt);
		chooseB.setSize(2,2);
		chooseB.setBackground(Color.orange);
		chooseB.setFont(new Font(null,Font.BOLD, 15));
		plate.add(chooseB);
		
		Label l2 = new Label("Password: ", Label.LEFT);
		l2.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l2);
		pass = new TextField();
		plate.add(pass);
		confirmPass = new JButton("Confirm Pass");
		confirmPass.setSize(2,2);
		confirmPass.setBackground(Color.orange);
		confirmPass.setFont(new Font(null,Font.BOLD, 15));
		plate.add(confirmPass);
		
		Label l4 = new Label("Choose key pair: ", Label.LEFT);
		l4.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l4);
		//solutionBox.setEditable(false);
		plate.add(solutionBox);
		plate.add(new Label());
		
		Label l3 = new Label("Save key pair as: ", Label.LEFT);
		l3.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l3);
		name = new TextField();
		plate.add(name);
		plate.add(new Label());
		
		plate.add(errorLabel);
		plate.add(new Label());
		//plate.add(new Label());
		
		confirmPass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				//setEnabled(false);
				System.out.println(txt.getText()+"  "+pass.getText());
			//	fillAllSolutions(txt.getText(), pass.getText());
				fillAllSolutions(txt.getText(), "pass");
				// fje za eksport
			}
		});
		
		chooseB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				//setEnabled(false);
				String str = chooseFile();
				txt.setText(str);
				
				// fje za eksport
			}
		});
		
		return plate;
	}
	public ImportView() {
		super("X.509 Authentication Service: IMPORT KEY PAIR");
		
		setBounds(300,150,700,180);
		setResizable(false);
		errorLabel = new Label(" ", Label.LEFT);
		//errorLabel.setBackground(Color.ORANGE);
		errorLabel.setFont(new Font(null,Font.BOLD, 15));
		txt =  new TextField();
		add(plateChoose());
		add(plateImportB(),BorderLayout.SOUTH );
		//txt.setEditable(false);
	}
	
}

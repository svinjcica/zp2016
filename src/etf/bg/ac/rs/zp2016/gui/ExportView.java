package etf.bg.ac.rs.zp2016.gui;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import etf.bg.ac.rs.zp2016.alg.StorageClass;


public class ExportView extends JFrame{
	private JButton open;
	private JFileChooser fc;
	private Label errorLabel = new Label("");
	private TextField txt, pass,fileName;
	private JButton exportB, exitB;
	private FirstWind fw;
	private String fullPath;
	public JComboBox<String> solutionBox = new JComboBox<String>();
	private StorageClass sc;
	private static final String name = "MyKeyStorage.p12";
	private static final String mypass = "mystorage";
	private ArrayList<String> allSolutions;
	private JCheckBox chBox;
	
	public void fillAllSolutions(){
		allSolutions = new ArrayList<String>();
		//System.out.println(storeName+"  "+storePass);
		  sc = new StorageClass(name,mypass);
		allSolutions = sc.viewKeyAlies(name,mypass); 
		
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
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PKCS #12", "p12", "pkcs12");
		fc.setFileFilter(filter);
		if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION){
		
		}
		System.out.println(fc.getSelectedFile().getAbsolutePath());
		
		return fc.getSelectedFile().getAbsolutePath();
	}
	
	private static String getFileExtension(String fileName) {
	        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
	        return fileName.substring(fileName.lastIndexOf(".")+1);
	        else return null;
	    }
	
	public Panel plateExportB(){
		Panel plate = new Panel(new GridLayout(1, 2));
		exportB = new JButton ("Export");
		exportB.setSize(2,2);
		exportB.setBackground(Color.orange);
		exportB.setFont(new Font(null,Font.BOLD, 15));
		plate.add(exportB);
		exportB.addActionListener(new ActionListener() {
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
					String extStr = getFileExtension(txt.getText());
					if( (extStr == null) && (fileName.getText().length() == 0)){
						errorLabel.setText("You must choose file! ");
						errorLabel.setBackground(Color.RED);
					}
					else{
						if(extStr != null){
							if(!extStr.equals("p12")) {
								errorLabel.setText("File extension must be .p12! ");
								errorLabel.setBackground(Color.RED);	
						}
							else fullPath = txt.getText();
						}
						else{
							fullPath = txt.getText() +"\\"+fileName.getText();
						}
						
					}
				}
				int selectedItem = solutionBox.getSelectedIndex();
				String keyAlias = allSolutions.get(selectedItem);
				System.out.println("usooo");
				// fje za eksport
			if(fullPath.length() > 0) {
				try {
					if(chBox.isSelected())
						sc.exportKeyAES(fullPath,pass.getText(),keyAlias);
					else 
						sc.exportKey(fullPath,pass.getText(),keyAlias);
					fw = new FirstWind();
					 fw.setVisible(true);
					 fw.ex = true;
					 dispose();
				} catch (UnrecoverableKeyException e1) {
					errorLabel.setText("You must choose file! ");
					errorLabel.setBackground(Color.RED);
					e1.printStackTrace();
				} catch (InvalidKeyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (KeyStoreException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (CertificateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchPaddingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalBlockSizeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BadPaddingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
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
		chooseB.setBackground(Color.ORANGE);
		chooseB.setFont(new Font(null,Font.BOLD, 15));
		plate.add(chooseB);
		
		
		Label l3 = new Label("Name of new file: ", Label.LEFT);
		l3.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l3);
		fileName = new TextField();
		plate.add(fileName);
		plate.add(new Label());
		
		Label l2 = new Label("Password: ", Label.LEFT);
		l2.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l2);
		pass = new TextField();
		plate.add(pass);
		plate.add(new Label());
		plate.add(errorLabel);
		
		Label l4 = new Label("Choose key pair: ", Label.LEFT);
		l4.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l4);
		//solutionBox.setEditable(false);
		plate.add(solutionBox);
		plate.add(new Label());
		
		chBox = new JCheckBox("Protect via AES alg");
		chBox.setFont(new Font(null,Font.BOLD, 15));
		plate.add(new Label());
		plate.add(chBox);
		
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
	public ExportView() {
		super("X.509 Authentication Service: EXPORT KEY PAIR");
		fillAllSolutions();
		setBounds(300,150,700,180);
		setResizable(false);
		errorLabel = new Label(" ", Label.LEFT);
		errorLabel.setFont(new Font(null,Font.BOLD, 15));
		txt =  new TextField();
		add(plateChoose());
		add(plateExportB(),BorderLayout.SOUTH );
		
	}
	
}

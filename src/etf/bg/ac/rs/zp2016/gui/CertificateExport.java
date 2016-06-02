
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


public class CertificateExport extends JFrame{
	private StorageClass sc;
	private JButton open;
	private JFileChooser fc;
	private Label errorLabel = new Label("");
	private TextField txt, fileName, name;
	private JButton exportB, exitB, confirmPass;
	private FirstWind fw;
	private String extStr;
	public JComboBox<String> solutionBox = new JComboBox<String>();
	private String fullPath ;
	private ArrayList<String> allSolutions;
	
	public void fillAllSolutions(String storeName,String storePass){
		allSolutions = new ArrayList<String>();
		//System.out.println(storeName+"  "+storePass);
		  sc = new StorageClass(storeName,storePass);
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
		//fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CER", "cer", "cer");
		fc.setFileFilter(filter);
		if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION){
		
		}
		if(fc.getSelectedFile()!= null) {
			//System.out.println(fc.getSelectedFile().getAbsolutePath());
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
		exportB = new JButton ("Export");
		exportB.setSize(2,2);
		exportB.setBackground(Color.orange);
		exportB.setFont(new Font(null,Font.BOLD, 15));
		plate.add(exportB);
		exportB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				//setEnabled(false);
				 extStr = getFileExtension(txt.getText());
				 
				if(txt.getText().length() == 0) {
					errorLabel.setText("You must choose file!!");
					errorLabel.setBackground(Color.RED);
					return;
				}
				 if( extStr != null){
					
						if(!extStr.equals("cer")) {
							errorLabel.setText("File extension must be .cer! ");
							errorLabel.setBackground(Color.RED);
							return;
						}
					}
				if(fileName.getText().length() == 0 && extStr == null){
					errorLabel.setText("You must input file! ");
					errorLabel.setBackground(Color.RED);
					return;
				}
				
							
							if(extStr!=null) fullPath = txt.getText();
							else fullPath = txt.getText() +"\\"+ fileName.getText();
							try {
								int selS = solutionBox.getSelectedIndex();
								String selecAli = solutionBox.getItemAt(selS);
								sc.exportCertificate(fullPath, selecAli);
								fw = new FirstWind();
								fw.setVisible(true);
							
								fw.ex = true;
								 dispose();
							} catch (CertificateException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
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
		Panel plate = new Panel(new GridLayout(4, 3));
		JButton chooseB = new JButton("Browse");
		Label l1 = new Label("Choose file: ", Label.LEFT);
		l1.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l1);
		plate.add(txt);
		chooseB.setSize(2,2);
		chooseB.setBackground(Color.orange);
		chooseB.setFont(new Font(null,Font.BOLD, 15));
		plate.add(chooseB);
		
		Label l2 = new Label("Name of new file: ", Label.LEFT);
		l2.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l2);
		fileName = new TextField();
		plate.add(fileName);
		confirmPass = new JButton("Confirm ");
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
		
		
		plate.add(errorLabel);
		plate.add(new Label());
		//plate.add(new Label());
		
		confirmPass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
		
				fillAllSolutions("MyKeyStorage.p12", "mystorage");
			
			}
		});
		
		chooseB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				//setEnabled(false);
				String str = chooseFile();
				txt.setText(str);
				//String extStr = getFileExtension(txt.getText());
				// fje za eksport
			}
		});
		
		return plate;
	}
	public CertificateExport() {
		super("X.509 Authentication Service: EXPORT CERTIFICATE");
		
		setBounds(300,150,700,150);
		setResizable(false);
	//	errorLabel = new Label(" ", Label.LEFT);
		
		errorLabel.setFont(new Font(null,Font.BOLD, 15));
		txt =  new TextField();
		add(plateChoose());
		add(plateImportB(),BorderLayout.SOUTH );
	
	}
	
}

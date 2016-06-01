package etf.bg.ac.rs.zp2016.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;





public class FirstWind extends JFrame{

	private JFrame secW;
	private JButton next;
	public int selectedItem = 0;
	public boolean ex = true;
	private Label reg = new Label();
	public JComboBox<String> solutionBox = new JComboBox<String>();
	private ArrayList<String> allSolutions = new ArrayList<String>();
	 
	private Panel fillWind(){
		Panel plate = new Panel();
		reg = new Label ("Choose action: ", Label.CENTER);
		reg.setFont(new Font(null,Font.BOLD, 15));
		reg.setBackground(Color.ORANGE);
		solutionBox.setEditable(false);
		for(int i = 0; i < allSolutions.size(); i++){	
			solutionBox.addItem(allSolutions.get(i));
			solutionBox.addActionListener(new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent event) {
			            JComboBox<String> combo = (JComboBox<String>) event.getSource();
			              
			        }
			    });		
		}
		plate.add(reg);
		plate.add(solutionBox);
		
		return plate;
	}
	
	public void fillAllSolutions(){
		
		allSolutions.add("Generate new key pair");
		allSolutions.add("Import key pair");
		allSolutions.add("Export key pair");
		allSolutions.add("View details of all existing pairs");
	//	allSolutions.add("Sign certificate");
		allSolutions.add("Export certificate");
		solutionBox.setBackground(Color.ORANGE);
	}
	
	public Panel  plateNextB(){
		Panel plate = new Panel(new GridLayout(1, 2));
		next = new JButton ("Next");
		next.setSize(2,2);
		next.setBackground(Color.ORANGE);
		next.setFont(new Font(null,Font.BOLD, 15));
		plate.add(new Label());
		plate.add(next);
		
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedItem = solutionBox.getSelectedIndex(); 
				System.out.println(solutionBox.getSelectedIndex());
				switch(selectedItem){
				case 0: secW = new GenerateKeyWind(); break;
				case 1: secW = new ImportView(); break;
				case 2: secW = new ExportView();break;
				case 3:	secW = new ShowAllKeys(); break;
			//	case 4:
				case 4: secW = new CertificateExport();break;
				
				}
				
				secW.setVisible(true);
				dispose();
				
			}
		});
		return plate;
	}
	
	public FirstWind(){
		
		super("X.509 Authentication Service");
		fillAllSolutions();
		setBounds(300,150,500,100);
		setResizable(false);
		//add(plateTxt(), BorderLayout.NORTH);//SOUTH
		//add(plateNum(), BorderLayout.CENTER);
		
		add(fillWind(),BorderLayout.WEST);
		add(plateNextB(),BorderLayout.SOUTH );
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent d){
				if(ex) dispose();
				else setVisible(false);
			}
		});
	}
}

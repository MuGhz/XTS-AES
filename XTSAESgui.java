import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class XTSAESgui implements ActionListener{

	private JFrame frame;
	private Label titleLabel;
	private Label keyLabel;
	private TextField keyText;
	private Button inputFileButton;
	private Button encryptButton;
	private Button decryptButton;
	private final int KEY_LENGTH = 64;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					XTSAESgui window = new XTSAESgui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public XTSAESgui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setBackground(Color.white);
		
		Font titleFont = new Font("Serif",Font.BOLD,20);
		titleLabel = new Label("XTS-AES Encrypt/Decrypt");
		titleLabel.setAlignment(Label.CENTER);
		titleLabel.setFont(titleFont);
		frame.add("North", titleLabel);
		
		Panel actionPanel = new Panel();
		GridBagLayout gbl = new GridBagLayout();
		actionPanel.setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		
		Font keyLabelFont = new Font("Courier", Font.PLAIN, 14);
		keyLabel = new Label("Key (in hex)");
		keyLabel.setAlignment(Label.LEFT);
		keyLabel.setFont(keyLabelFont);
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbl.setConstraints(keyLabel, gbc);
		actionPanel.add(keyLabel);
		
		keyText = new TextField(KEY_LENGTH);
		keyText.setText("");
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(keyText, gbc);
		actionPanel.add(keyText);
		
		Panel buttonsPanel = new Panel();
		inputFileButton = new Button("Select File");
		inputFileButton.addActionListener(this);
		buttonsPanel.add(inputFileButton);
		encryptButton = new Button("Encrypt");
		encryptButton.addActionListener(this);
		buttonsPanel.add(encryptButton);
		decryptButton = new Button("Decrypt");
		decryptButton.addActionListener(this);
		buttonsPanel.add(decryptButton);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(buttonsPanel, gbc);
		actionPanel.add(buttonsPanel);
		frame.add("Center", actionPanel);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object source = arg0.getSource();
		
		if(source == inputFileButton){
			JFileChooser fc = new JFileChooser();
			fc.showOpenDialog(frame);
			File file = fc.getSelectedFile();
		}else if(source == encryptButton){
			
		}else if(source == decryptButton){
			
		}
	}

}

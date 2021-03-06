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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
*GUI untuk implementasi algoritma XTS-AES
*Galuh Estya A - Geswa Wahid T - Muhammad Ghozi
*CIS2018
*/
public class XTSAESgui implements ActionListener{

	private JFrame frame;
	private Label titleLabel;
	private TextField keyText;
	private TextField saveTo;
	private TextField inputText;
	private Button inputFileButton;
	private Button outputFileButton;
	private Button encryptButton;
	private Button decryptButton;
	private Button keyButton;
	private final int KEY_LENGTH = 64;
	private byte[] data = {};
	private String input="";
	private String output ="";

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
		frame.setBounds(100, 100, 750, 500);
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
		
		Font labelFont = new Font("Courier", Font.PLAIN, 14);
		
		inputFileButton = new Button("Select File");
		inputFileButton.addActionListener(this);
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbl.setConstraints(inputFileButton, gbc);
		actionPanel.add(inputFileButton);
		
		inputText = new TextField(64);
		inputText.setEditable(false);
		inputText.setFont(new Font("Arial Black", Font.BOLD,12));
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(inputText, gbc);
		actionPanel.add(inputText);
		
		outputFileButton = new Button("Save To");
		outputFileButton.addActionListener(this);
		gbc.gridwidth = 1;
		gbl.setConstraints(outputFileButton, gbc);
		actionPanel.add(outputFileButton);
		
		saveTo = new TextField(64);
		saveTo.setEditable(false);
		saveTo.setFont(new Font("Arial Black", Font.BOLD,12));
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(saveTo, gbc);
		actionPanel.add(saveTo);
		
		keyButton = new Button("Select Key");
		keyButton.addActionListener(this);
		gbc.gridwidth = 1;
		gbl.setConstraints(keyButton, gbc);
		actionPanel.add(keyButton);
		
		keyText = new TextField(KEY_LENGTH);
		keyText.setEditable(false);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(keyText, gbc);
		actionPanel.add(keyText);
		
		Panel buttonsPanel = new Panel();
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
			input = file.getAbsolutePath();
			inputText.setText(input);
			try {
				data = Files.readAllBytes(Paths.get(file.toURI()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(source == outputFileButton){
			JFileChooser fc = new JFileChooser();
			fc.showSaveDialog(frame);
			File file = fc.getSelectedFile();
			output = file.getAbsolutePath();
			saveTo.setText(output);
			
		}else if(source == encryptButton){
			String key = keyText.getText();
			Xts x = new Xts(data,key);
			byte[] res = x.encrypt();
			try {
				FileOutputStream stream = new FileOutputStream(output);
				stream.write(res);
				stream.flush();
				stream.close();
				JOptionPane.showMessageDialog(frame, "encrypt success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(source == decryptButton){
			String key = keyText.getText();
			Xts x = new Xts(data,key);
			byte[] res = x.decrypt();
			try {
				FileOutputStream stream = new FileOutputStream(output);
				stream.write(res);
				stream.flush();
				stream.close();
				JOptionPane.showMessageDialog(frame, "decrypt success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(source == keyButton){
			JFileChooser fc = new JFileChooser();
			fc.showOpenDialog(frame);
			File file = fc.getSelectedFile();
			try {
				Scanner sc = new Scanner(file);
				keyText.setText(sc.nextLine());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}

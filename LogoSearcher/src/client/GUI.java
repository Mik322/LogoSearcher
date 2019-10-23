package client;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class GUI {
	
	private JFrame frame;
	
	public GUI() {
		frame = new JFrame("Interface");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		addcontent();
	}
	
	private void addcontent() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel panelImage = new JPanel();
		panelImage.setLayout(new BorderLayout());
		
		
		
		JTextField pasta=new JTextField("");
		pasta.setPreferredSize(new Dimension(400,24));
		panelImage.add(pasta, BorderLayout.NORTH);
		
		JTextField imagem=new JTextField("");
		imagem.setPreferredSize(new Dimension(400,24));
		panelImage.add(imagem,BorderLayout.SOUTH);
		
		JButton procura = new JButton("procura");
		procura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			}	
		});
		panel.add(procura, BorderLayout.SOUTH);
		
		JPanel panelButton = new JPanel();
		panelButton.setLayout(new BorderLayout());
		
		JButton BPasta = new JButton("pasta");
		BPasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}	
		});
		panelButton.add(BPasta, BorderLayout.NORTH);
		
		JButton BImagem = new JButton("imagem");
		BImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filename = File.separator+"tmp";
				JFileChooser fc = new JFileChooser(new File(filename));
				fc.showOpenDialog(frame);
				File selFile = fc.getSelectedFile();
				
				
				ImageIcon image = new ImageIcon(selFile.getName());
				JLabel imageLabel = new JLabel(image);
				frame.add(imageLabel,BorderLayout.CENTER);
				frame.revalidate();
	            frame.repaint();
	            imagem.setText(selFile.getName());
			}	
		});
		panelButton.add(BImagem, BorderLayout.SOUTH);
		frame.add(panel,BorderLayout.SOUTH);
		panel.add(panelButton, BorderLayout.EAST);
		panel.add(panelImage, BorderLayout.CENTER);
		frame.setLocation(250, 50);
	}

	
	
	public void open() {
		frame.setSize(700, 550);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		GUI i = new GUI();
		i.open();
		
	}

}

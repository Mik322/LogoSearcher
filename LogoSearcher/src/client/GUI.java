package client;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI implements Observer {

	private JFrame frame;
	private Client client;
	
	public GUI(Client c) {
		frame = new JFrame("Interface");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		addcontent();
		client = c;
	}

	private void addcontent() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel panelImage = new JPanel();
		panelImage.setLayout(new BorderLayout());

		JTextField pasta = new JTextField("");
		pasta.setPreferredSize(new Dimension(400, 24));
		panelImage.add(pasta, BorderLayout.NORTH);

		JTextField imagem = new JTextField("");
		imagem.setPreferredSize(new Dimension(400, 24));
		panelImage.add(imagem, BorderLayout.SOUTH);

		DefaultListModel<String> model = new DefaultListModel<>();
		model.addElement("Imagens encontradas");
		//model.addElement("B");
		//model.addElement("C");

		JList<String> list = new JList<>(model);
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					String selectedValue = list.getSelectedValue();
					//POR TERMINAR
				}
			}
		});
		frame.add(list, BorderLayout.EAST);
		
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
				
				JFileChooser jfc = new JFileChooser(".");
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = jfc.showOpenDialog(null);
				File selectedFile = null;
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = jfc.getSelectedFile();
					//System.out.println(selectedFile.getAbsolutePath());
				}

				pasta.setText(selectedFile.getName());
			}
		});
		panelButton.add(BPasta, BorderLayout.NORTH);

		JButton BImagem = new JButton("imagem");
		BImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				JFileChooser imageChooser = new JFileChooser(".");
				imageChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnValue2 = imageChooser.showOpenDialog(null);
				File selectedImage = null;
				if (returnValue2 == JFileChooser.APPROVE_OPTION) {
					selectedImage = imageChooser.getSelectedFile();
				}
				
				ImageIcon image = new ImageIcon(selectedImage.getAbsolutePath());
				JLabel imageLabel = new JLabel(image);
				frame.add(imageLabel,BorderLayout.CENTER);
				frame.revalidate();
	            frame.repaint();
	            imagem.setText(selectedImage.getName());
			}
		});
		panelButton.add(BImagem, BorderLayout.SOUTH);
		frame.add(panel, BorderLayout.SOUTH);
		panel.add(panelButton, BorderLayout.EAST);
		panel.add(panelImage, BorderLayout.CENTER);
		frame.setLocation(250, 50);
	}

	public void open() {
		frame.setSize(700, 550);
		frame.setVisible(true);
	}

	/*
	public static void main(String[] args) {
		GUI i = new GUI();
		i.open();

	}*/

	//Quando o cliente recebe um resultado de pesquisa vai ativar este procedimento
	@Override
	public void update(Observable o, Object arg) {
		
	}

}
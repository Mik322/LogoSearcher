package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI implements Observer {

	private JFrame frame;
	private Client client;
	private BufferedImage logo;
	private BufferedImage image;
	private String directory;

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

		JPanel panelIcon = new JPanel();
		JScrollPane paneIcon = new JScrollPane(panelIcon);
		frame.add(paneIcon, BorderLayout.CENTER);

		JTextField pasta = new JTextField("");
		pasta.setPreferredSize(new Dimension(400, 24));
		panelImage.add(pasta, BorderLayout.CENTER);

		JTextField imagem = new JTextField("");
		imagem.setPreferredSize(new Dimension(400, 24));
		panelImage.add(imagem, BorderLayout.SOUTH);

		DefaultListModel<String> model = new DefaultListModel<>();
		model.addElement("Imagens encontradas");

		JList<String> list = new JList<>(model);
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					// File selectedValue = new File(list.getSelectedValue());
					String fullDir = directory + "\\" + list.getSelectedValue();

					ImageIcon imageIcon = new ImageIcon(fullDir);
					try {
						image = ImageIO.read(new File(fullDir));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JLabel imageLabel = new JLabel(imageIcon);
					panelIcon.removeAll();
					panelIcon.add(imageLabel, BorderLayout.CENTER);
					panelIcon.revalidate();
					panelIcon.repaint();
				}
			}
		});
		JScrollPane listScroll = new JScrollPane(list);
		frame.add(listScroll, BorderLayout.EAST);

		JPanel panelButton = new JPanel();
		panelButton.setLayout(new BorderLayout());

		JButton BPasta = new JButton("pasta");
		BPasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser jfc = new JFileChooser(".");
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.setMultiSelectionEnabled(true);
				int returnValue = jfc.showOpenDialog(null);
				File selectedFile = null;
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = jfc.getSelectedFile();
				}

				File dir = new File(jfc.getSelectedFile().getAbsolutePath());
				directory = dir.getAbsolutePath();

				File[] list = dir.listFiles();

				pasta.setText(selectedFile.getName());
				model.clear();
				for (File f : list) {
					if (f.getName().contains("png")) {
						model.addElement(f.getName());
					}
				}
			}
		});
		panelButton.add(BPasta, BorderLayout.NORTH);

		JButton BLogo = new JButton("Logo");
		BLogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser imageChooser = new JFileChooser(".");
				imageChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnValue2 = imageChooser.showOpenDialog(null);
				File selectedImage = null;
				if (returnValue2 == JFileChooser.APPROVE_OPTION) {
					selectedImage = imageChooser.getSelectedFile();
				}
				imagem.setText(selectedImage.getName());

				try {
					logo = ImageIO.read(new File(selectedImage.getAbsolutePath()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panelButton.add(BLogo, BorderLayout.SOUTH);

		// Vai buscar a Imagem de BImagem e do JList
		JButton procura = new JButton("procura");
		procura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.sendImages(image, logo);
			}
		});
		panel.add(procura, BorderLayout.SOUTH);

		frame.add(panel, BorderLayout.SOUTH);
		panel.add(panelButton, BorderLayout.EAST);
		panel.add(panelImage, BorderLayout.CENTER);
		frame.setLocation(250, 50);
	}

	public void open() {
		frame.setSize(700, 550);
		frame.setVisible(true);
	}

	// Quando o cliente recebe um resultado de pesquisa vai ativar este procedimento
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Resultados chegaram ao GUI");
		@SuppressWarnings("unchecked")
		ArrayList<Integer[]> results = (ArrayList<Integer[]>) arg;
		System.out.println(results.toString());

	}

}
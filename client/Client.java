package client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import data.DataFile;
import server.FileWorker;

public class Client extends JFrame implements ActionListener, ISocketListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField ipInput, portInput, searchInput;
	JButton connectButton, cancelButton, searchButton, downLoadFile, uploadFileButton;
	JProgressBar jb;
	JList<String> list;
	ClientSocketThread clientSocketThread = null;
	private JPanel idControl;
	private JPanel fileOperation;
	private JLabel imagelb;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Client frame = new Client();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Client() {
		idControl = new JPanel();
		idControl.setForeground(Color.BLACK);
		idControl.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(238, 238, 238)),
				"ID Connect", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		idControl.setBounds(95, 130, 345, 142);
		getContentPane().add(idControl);
		idControl.setLayout(null);

		JLabel ipLabel = new JLabel("IP: ");
		ipLabel.setForeground(Color.BLACK);
		ipLabel.setBounds(89, 16, 32, 25);
		idControl.add(ipLabel);
		ipLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		ipInput = new JTextField("127.0.0.1");
		ipInput.setForeground(Color.BLACK);
		ipInput.setBounds(145, 15, 148, 25);
		idControl.add(ipInput);
		ipInput.setFont(new Font("Times New Roman", Font.BOLD, 20));
		JLabel portLabel = new JLabel("PORT: ");
		portLabel.setForeground(Color.BLACK);
		portLabel.setBounds(53, 66, 88, 25);
		idControl.add(portLabel);
		portLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		portInput = new JTextField("10");
		portInput.setForeground(Color.BLACK);
		portInput.setBounds(145, 65, 148, 25);
		idControl.add(portInput);
		portInput.setFont(new Font("Times New Roman", Font.BOLD, 20));
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(166, 115, 136, 25);
		idControl.add(cancelButton);
		cancelButton.setForeground(Color.BLACK);
		cancelButton.setBackground(Color.WHITE);
		cancelButton.setFont(new Font("Times New Roman", Font.BOLD, 20));

		connectButton = new JButton("Connect");
		connectButton.setBounds(37, 115, 119, 25);
		idControl.add(connectButton);
		connectButton.setForeground(Color.BLACK);
		connectButton.setBackground(Color.WHITE);
		connectButton.setFont(new Font("Times New Roman", Font.BOLD, 20));

		connectButton.addActionListener(this);
		cancelButton.addActionListener(this);

		fileOperation = new JPanel();
		fileOperation.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(238, 238, 238)),
				"File Operation", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		fileOperation.setBounds(64, 74, 414, 253);
		getContentPane().add(fileOperation);
		fileOperation.setLayout(null);
		searchButton = new JButton("Search");
		searchButton.setBounds(130, 58, 113, 33);
		fileOperation.add(searchButton);
		searchButton.setForeground(Color.BLACK);
		searchButton.setBackground(Color.WHITE);
		searchButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		searchInput = new JTextField();
		searchInput.setBounds(130, 15, 274, 25);
		fileOperation.add(searchInput);
		searchInput.setForeground(Color.BLACK);
		searchInput.setFont(new Font("Times New Roman", Font.BOLD, 20));

		JLabel searchLabel = new JLabel("Search: ");
		searchLabel.setBounds(24, 16, 75, 25);
		fileOperation.add(searchLabel);
		searchLabel.setForeground(Color.BLACK);
		searchLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));

		downLoadFile = new JButton("Download");
		downLoadFile.setBounds(63, 183, 135, 33);
		fileOperation.add(downLoadFile);
		downLoadFile.setBackground(Color.WHITE);
		downLoadFile.setForeground(Color.BLACK);
		downLoadFile.setFont(new Font("Times New Roman", Font.BOLD, 20));

		uploadFileButton = new JButton("Upload");
		uploadFileButton.setBounds(219, 183, 123, 33);
		fileOperation.add(uploadFileButton);
		uploadFileButton.setBackground(Color.WHITE);
		uploadFileButton.setForeground(Color.BLACK);
		uploadFileButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		jb = new JProgressBar(0, 100);
		jb.setBackground(Color.WHITE);
		jb.setBounds(37, 224, 351, 16);
		fileOperation.add(jb);
		jb.setForeground(Color.GREEN);
		jb.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		jb.setValue(0);
		jb.setStringPainted(true);
		uploadFileButton.addActionListener(this);
		downLoadFile.addActionListener(this);
		searchButton.addActionListener(this);

		list = new JList<>();
		list.setBorder(new TitledBorder(null, "File List", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		list.setForeground(Color.BLACK);
		list.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Client");
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		imagelb = new JLabel("");
		imagelb.setIcon(new ImageIcon("image.jpg"));
		imagelb.setBounds(10, 356, 516, 190);
		getContentPane().add(imagelb);
		this.setBounds(0, 0, 1200, 624);
		this.setVisible(true);
		fileOperation.setVisible(false);
		list.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == connectButton) {
			String ip = ipInput.getText();
			String port = portInput.getText();
			System.out.println(ip + " : " + port);
			try {
				clientSocketThread = new ClientSocketThread(this);
				if (clientSocketThread.setSocket(ip, Integer.parseInt(port))) {
					idControl.setVisible(false);
					fileOperation.setVisible(true);
					JScrollPane listScrollPane = new JScrollPane();
					listScrollPane.setBounds(536, 38, 640, 508);
					getContentPane().add(listScrollPane);
					listScrollPane.setViewportView(list);
					list.setVisible(true);
					FileWorker.createFolder();
					clientSocketThread.sendString("VIEW_ALL_FILE");
					clientSocketThread.start();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == cancelButton) {
			// clientSocketThread.closeSocket();
			this.dispose();
		} else if (e.getSource() == searchButton) {
			String search = searchInput.getText();
			if (clientSocketThread != null) {
				if (search.isEmpty())
					clientSocketThread.sendString("VIEW_ALL_FILE");
				else
					clientSocketThread.sendString("SEARCH_FILE:" + "--" + search);
			}
		} else if (e.getSource() == downLoadFile) {
			if (list.getSelectedIndex() != -1) {
				String str = list.getSelectedValue();
				clientSocketThread.sendString("DOWNLOAD_FILE" + "--" + str);
			}
		} else if (e.getSource() == uploadFileButton) {
			JFileChooser fileChooser = new JFileChooser();
			int returnVal = fileChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File fileToSave = fileChooser.getSelectedFile();
				String filePath = fileToSave.getPath();
				clientSocketThread.sendFile(filePath);
			}
		}
	}

	@Override
	public void updateListFile(String[] listFile) {
		list.setListData(listFile);
	}

	@Override
	public void setProgress(int n) {
		jb.setValue(n);
	}

	@Override
	public void showDialog(String str, String type) {
		if (type.equals("ERROR"))
			JOptionPane.showMessageDialog(this, str, type, JOptionPane.ERROR_MESSAGE);
		else if (type.equals("INFOR"))
			JOptionPane.showMessageDialog(this, str, type, JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public String chooseFileToSave(DataFile dataFile) {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			String filePath = fileToSave.getPath();
			try {
				dataFile.saveFile(filePath);
				JOptionPane.showMessageDialog(null, "File Saved");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		return null;
	}

}

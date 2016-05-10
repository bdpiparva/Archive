package com.ui.bhupendra;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.archive.bhupendra.Archive;

public class MainWindow extends JFrame {

	private static final String STATUS = " Status: ";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtSource;
	private JTextField txtDest;
	private JLabel lblStatus;
	private JPasswordField txtPassword;
	private JProgressBar progressBar;
	private long totalSize = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setName("zipper");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/com/ui/bhupendra/logo.png")));
		setTitle("Zipper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Error while setting system theme " + e);
		}
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel pnlCenter = new JPanel();
		contentPane.add(pnlCenter, BorderLayout.CENTER);

		JLabel lblSource = new JLabel("Source");

		txtSource = new JTextField();
		txtSource.setColumns(10);

		JButton btnSource = new JButton("Browse");
		btnSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateStatus("Selecting source");
				showFileChooser(e, txtSource);
			}
		});

		JLabel lblDest = new JLabel("Destination");

		txtDest = new JTextField();
		txtDest.setColumns(10);

		JButton btnDest = new JButton("Browse");
		btnDest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateStatus("Selecting destination directory");
				showFileChooser(e, txtDest);
			}
		});

		JButton btnCompress = new JButton("Compress");
		compressListner(btnCompress);

		JButton btnExtract = new JButton("Extract");
		extractListner(btnExtract);

		JLabel lblPassword = new JLabel("Password");

		txtPassword = new JPasswordField();

		progressBar = new JProgressBar(0, 100);
		progressBar.setVisible(false);

		GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
		gl_pnlCenter.setHorizontalGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_pnlCenter
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_pnlCenter
										.createParallelGroup(Alignment.TRAILING)
										.addComponent(progressBar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
										.addGroup(
												Alignment.LEADING,
												gl_pnlCenter.createSequentialGroup().addComponent(lblSource).addGap(38)
														.addComponent(txtSource, GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE).addGap(18).addComponent(btnSource))
										.addGroup(
												Alignment.LEADING,
												gl_pnlCenter.createSequentialGroup().addComponent(btnExtract).addPreferredGap(ComponentPlacement.RELATED, 338, Short.MAX_VALUE)
														.addComponent(btnCompress))
										.addGroup(
												Alignment.LEADING,
												gl_pnlCenter
														.createSequentialGroup()
														.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING).addComponent(lblDest).addComponent(lblPassword))
														.addGap(16)
														.addGroup(
																gl_pnlCenter.createParallelGroup(Alignment.LEADING)
																		.addComponent(txtPassword, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
																		.addComponent(txtDest, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)).addGap(18).addComponent(btnDest)))
						.addContainerGap()));
		gl_pnlCenter.setVerticalGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING).addGroup(
				gl_pnlCenter
						.createSequentialGroup()
						.addGap(6)
						.addGroup(
								gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
										.addComponent(txtSource, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblSource)
										.addComponent(btnSource))
						.addGap(18)
						.addGroup(
								gl_pnlCenter.createParallelGroup(Alignment.BASELINE).addComponent(lblDest)
										.addComponent(txtDest, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(btnDest))
						.addGap(18)
						.addGroup(
								gl_pnlCenter.createParallelGroup(Alignment.BASELINE).addComponent(lblPassword)
										.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(13)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE).addComponent(btnExtract).addComponent(btnCompress)).addGap(18)
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE).addContainerGap(127, Short.MAX_VALUE)));
		pnlCenter.setLayout(gl_pnlCenter);

		JPanel pnlStatus = new JPanel();
		pnlStatus.setBackground(Color.WHITE);
		contentPane.add(pnlStatus, BorderLayout.SOUTH);
		pnlStatus.setLayout(new BorderLayout(0, 0));

		lblStatus = new JLabel(STATUS);
		lblStatus.setToolTipText("Status");
		lblStatus.setPreferredSize(new Dimension(38, 25));
		lblStatus.setMinimumSize(new Dimension(38, 25));
		lblStatus.setMaximumSize(new Dimension(38, 25));
		lblStatus.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		lblStatus.setBackground(Color.WHITE);
		lblStatus.setFont(new Font("Arial", Font.PLAIN, 11));
		lblStatus.setForeground(Color.BLACK);
		pnlStatus.add(lblStatus, BorderLayout.CENTER);
	}

	private void compressListner(final JButton btnCompress) {
		btnCompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String dest = txtDest.getText();
				final String source = txtSource.getText();
				final File fSource = new File(source);
				File fDest = new File(dest);
				if (validate(fSource, false) && validate(fDest, false)) {
					final Archive archive = new Archive(MainWindow.this);
					new Thread(new Runnable() {
						@Override
						public void run() {
							FileSize fileSize = new FileSize(MainWindow.this, fSource);
							totalSize = fileSize.getSize();
							progressBar.setValue(0);
							progressBar.setVisible(true);
							btnCompress.setEnabled(false);
							try {
								archive.archive(dest, source, txtPassword.getText());
								updateStatus("Compression completed.");
							} catch (Exception e1) {
								updateStatus(e1.getMessage());
								e1.printStackTrace();
							}
							btnCompress.setEnabled(true);
							progressBar.setVisible(false);
						}
					}).start();
				}
			}
		});
	}

	private void extractListner(final JButton btnExtract) {
		btnExtract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String dest = txtDest.getText();
				final String source = txtSource.getText();
				File fSource = new File(source);
				File fDest = new File(dest);
				if (validate(fDest, false) && validate(fSource, true)) {
					final Archive archive = new Archive(MainWindow.this);
					new Thread(new Runnable() {
						@Override
						public void run() {
							btnExtract.setEnabled(false);
							try {
								archive.extract(dest, source, txtPassword.getText(), true);
								updateStatus("Extraction completed.");
							} catch (Exception e1) {
								updateStatus(e1.getMessage());
								e1.printStackTrace();
							}
							btnExtract.setEnabled(true);
						}
					}).start();
				}
			}
		});
	}

	private boolean validate(File file, boolean isFile) {
		if (!file.exists()) {
			updateStatus("File does not exsist: " + file.getAbsolutePath());
			return false;
		}

		if (!file.canRead()) {
			updateStatus("Do not have permission to read file: " + file.getAbsolutePath());
			return false;
		}

		if (isFile && file.isDirectory()) {
			updateStatus("Please specifiy file. Given " + file.getAbsolutePath() + " is a directory.");
			return false;
		}

//		if (!isFile && file.isFile()) {
//			updateStatus("Please specifiy directory. Given " + file.getAbsolutePath() + " is a file.");
//			return false;
//		}
		return true;
	}

	private void showFileChooser(ActionEvent e, JTextField tf) {

		String dir = tf.getText();
		JFileChooser fc = (dir == null || dir.isEmpty()) ? new JFileChooser() : new JFileChooser(dir);
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showOpenDialog(tf);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			tf.setText(fc.getSelectedFile().getAbsolutePath());
			updateStatus("");
		}

	}

	public void updateStatus(String status) {
		lblStatus.setText(STATUS + status);
	}

	public void addProgress(final long value) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				int per = (int) Math.ceil(((value * 100.0) / totalSize));
				progressBar.setValue(per);
				updateStatus(progressBar.getValue() + "% completed");
			}
		}).start();

	}
}

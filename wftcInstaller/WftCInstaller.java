package wftcInstaller;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.CodeSource;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class WftCInstaller extends JFrame {

	public static void main(String[] args) throws IOException {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WftCInstaller frame = new WftCInstaller();
					frame.setVisible(true);
					//frame.pack();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public WftCInstaller() throws IOException {
		
		setResizable(false);
		setTitle("War for the Colonies Installer");
		setBounds(100, 100, 375, 215);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Installation i1 = new WftCInstaller.Installation();
		final JFileChooser fcORG = new JFileChooser();
		
		JButton btnSelectMedievalIi = new JButton("Select Medieval II directory");
		btnSelectMedievalIi.setToolTipText("Select the directory where 'Medieval II' is installed.");
		btnSelectMedievalIi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  fcORG.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				  fcORG.showOpenDialog(fcORG.getParent());	
			}
		});
		
		JButton btnInstallWarFor = new JButton("Install");
		btnInstallWarFor.setToolTipText("Install 'War for the Colonies'.");
		btnInstallWarFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fcORG.getSelectedFile() != null) {
					if(AuxMeth.showTextMessage() == 0) {
						try {
							i1.performInstall(fcORG.getSelectedFile().toString());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "It is necessary to select the directory\nwhere"
														+ " the game is installed first.");
				}
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(47)
					.addComponent(btnSelectMedievalIi, GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
					.addGap(45))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(84)
					.addComponent(btnInstallWarFor, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
					.addGap(81))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addComponent(btnSelectMedievalIi, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
					.addGap(48)
					.addComponent(btnInstallWarFor, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
					.addGap(31))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	public static class Installation {
		
	     AuxMeth am = new AuxMeth();
	     
	     public void performInstall(String loc_path) throws Exception {
	    	 File bkDir = new File(loc_path + "/wftcBK");
	    	 CodeSource codeSource = WftCInstaller.class.getProtectionDomain().getCodeSource();
	    	 File jarFile = new File(codeSource.getLocation().toURI().getPath());
	    	 String jarDir = jarFile.getParentFile().getPath();
	    	 AuxMeth.mkDirIfNotPresent(bkDir);
	    	 File resFolder = new File(jarDir + "/wftc");
	    	 AuxMeth.bkFilesIfExist(loc_path, resFolder, bkDir);
	    	 AuxMeth.copyFolder(resFolder, (new File(loc_path )));
	    	 Files.deleteIfExists((new File(loc_path + "/mods/americas/data/descr_geography_new.txt").toPath()));
	    	 Files.deleteIfExists((new File(loc_path + "/mods/americas/data/descr_geography_new.db").toPath()));
	    	 JOptionPane.showMessageDialog(null, "Installation complete. The backup directory is\n"
	    			 				              + bkDir.toString() + "\n" +
	    			 				              "Launch the mod by double-clicking on 'War for the Colonies.bat'\n" +
	    			 				              "in the /mods/americas/ directory.");
	    }
	}
}

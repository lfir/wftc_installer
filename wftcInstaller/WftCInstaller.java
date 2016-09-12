package wftcInstaller;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.CodeSource;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class WftCInstaller extends JFrame {

	public static void main(String[] args) throws IOException {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WftCInstaller frame = new WftCInstaller();
					frame.setVisible(true);
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
					License lic = new License();
					int accepted = JOptionPane.showConfirmDialog(null, lic.getContentPane(), 
																"License information", JOptionPane.YES_NO_OPTION,
																JOptionPane.PLAIN_MESSAGE);
					try {
						i1.performInstallIfLicAccepted(fcORG.getSelectedFile().toString(), accepted);
					} catch (Exception e1) {
						e1.printStackTrace();
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
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(84)
					.addComponent(btnInstallWarFor, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
					.addGap(81))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addComponent(btnSelectMedievalIi, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
					.addComponent(btnInstallWarFor, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(31))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	public static class Installation {
		
	     private AuxMeth am = new AuxMeth();
	     
	     public void performInstallIfLicAccepted(String loc_path, int licenseAccepted) throws Exception {
	    	 if(licenseAccepted == 0) {
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
		    	 AuxMeth.createDesktopShortcut(loc_path);
		    	 JOptionPane.showMessageDialog(null, "Installation complete. The backup directory is\n"
		    			 				              + bkDir.toString() + "\n" +
		    			 				              "Launch the mod by double-clicking on \n" +
		    			 				              "'War for the Colonies.bat'\n" +
		    			 				              "in the /mods/americas/ directory.\n" +
		    			 				              "or its shortcut on the desktop.");
	    	 }
	    }
	}
}

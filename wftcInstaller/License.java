package wftcInstaller;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;

public class License extends JInternalFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public License() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 465, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTextPane txtpnWarForThe = new JTextPane();
		txtpnWarForThe.setEditable(false);
		txtpnWarForThe.setText("War for the Colonies installer.\nCopyright (C) 2016 Asta666.\n"
				+ "This program is free software: you can redistribute it and/or modify\nit under"
				+ " the terms of the GNU General Public License as published by\nthe Free Software Foundation,"
				+ " either version 3 of the License, or\n(at your option) any later version.\n\n"
				+ "This program is distributed in the hope that it will be useful,\nbut WITHOUT ANY WARRANTY; without"
				+ " even the implied warranty of\nMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE."
				+ "  See the\nGNU General Public License for more details.\n\nYou should have received a copy"
				+ " of the GNU General Public License\nalong with this program."
				+ "  If not, see <http://www.gnu.org/licenses/>.");
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("Do you accept these terms and conditions?");
		tglbtnNewToggleButton.setSelected(true);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(txtpnWarForThe, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(57)
					.addComponent(tglbtnNewToggleButton)
					.addContainerGap(48, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(txtpnWarForThe, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
					.addGap(12)
					.addComponent(tglbtnNewToggleButton)
					.addGap(5))
		);
		contentPane.setLayout(gl_contentPane);
	}
}

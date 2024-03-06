package dialogs;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;



public class DlgHexagon extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtRadius;
	private boolean isOk;
	private Color clr;
	private Color outerClr;
	private JButton btnSetInnerColor;
	private JButton btnSetOuterColor;
	private JTextField txtCenterX;
	private JTextField txtCenterY;



	public static void main(String[] args) {
		
		try {
			DlgHexagon dialog = new DlgHexagon();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public DlgHexagon() {
		setTitle("Hexagon");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblRadius = new JLabel("Radius:");
		
		txtRadius = new JTextField();
		txtRadius.setColumns(10);
		
		JLabel lblCenterX = new JLabel("Center X:");
		
		JLabel lblCenterY = new JLabel("Center Y:");
		
		txtCenterX = new JTextField();
		txtCenterX.setColumns(10);
		
		txtCenterY = new JTextField();
		txtCenterY.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(15)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblRadius)
						.addComponent(lblCenterX)
						.addComponent(lblCenterY))
					.addGap(87)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtCenterY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtCenterX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(149))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(34)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCenterX)
						.addComponent(txtCenterX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCenterY)
						.addComponent(txtCenterY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(44)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblRadius)
						.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(62, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
							isOk = true;			
							dispose();
						
					}
				});
				
				btnSetInnerColor = new JButton("Set inner color");
				btnSetInnerColor.setForeground(Color.GRAY);
				btnSetInnerColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Color color = JColorChooser.showDialog(null, "Select a color", btnSetInnerColor.getBackground());
						btnSetInnerColor.setBackground(color);
						clr = color;
					}
				});
				
				btnSetOuterColor = new JButton("Set outer color");
				btnSetOuterColor.setForeground(Color.GRAY);
				btnSetOuterColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Color color = JColorChooser.showDialog(null, "Select a color", btnSetOuterColor.getBackground());
						btnSetOuterColor.setBackground(color);
						outerClr = color;
					}
				});
				
				buttonPane.add(btnSetOuterColor);
				
				buttonPane.add(btnSetInnerColor);
				
				
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}


	public JButton getBtnSetInnerColor() {
		return btnSetInnerColor;
	}

	public void setBtnSetInnerColor(JButton btnSetInnerColor) {
		this.btnSetInnerColor = btnSetInnerColor;
	}

	public JButton getBtnSetOuterColor() {
		return btnSetOuterColor;
	}

	public void setBtnSetOuterColor(JButton btnSetOuterColor) {
		this.btnSetOuterColor = btnSetOuterColor;
	}

	public boolean isOk() {
		return isOk;
	}


	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}


	public Color getClr() {
		return clr;
	}

	public void setClr(Color clr) {
		this.clr = clr;
	}

	public Color getOuterClr() {
		return outerClr;
	}

	public JTextField getTxtRadius() {
		return txtRadius;
	}


	public void setTxtRadius(JTextField txtRadius) {
		this.txtRadius = txtRadius;
	}


	public JTextField getTxtCenterX() {
		return txtCenterX;
	}


	public void setTxtCenterX(JTextField txtCenterX) {
		this.txtCenterX = txtCenterX;
	}


	public JTextField getTxtCenterY() {
		return txtCenterY;
	}


	public void setTxtCenterY(JTextField txtCenterY) {
		this.txtCenterY = txtCenterY;
	}


	public void setOuterClr(Color outerClr) {
		this.outerClr = outerClr;
	}


	

}

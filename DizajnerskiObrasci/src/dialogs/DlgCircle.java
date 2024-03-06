package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Circle;

import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DlgCircle extends JDialog {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblCenterX;
	private JLabel lblCenterY;
	private JLabel lblRadius;
	private JTextField txtCenterX;
	private JTextField txtCenterY;
	private JTextField txtRadius;
	private Color clr;
	private Color outerClr;
	private JButton btnSetInnerColor;
	private JButton btnSetOuterColor;
	private boolean isOk;
	private Circle circle;





	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgCircle dlg = new DlgCircle();
			dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dlg.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * Create the dialog.
	 */
	public DlgCircle() {
		setTitle("Circle");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		lblCenterX = new JLabel("Center X:");
		lblCenterY = new JLabel("Center Y:");		
		lblRadius = new JLabel("Radius:");
		txtCenterX = new JTextField();
		txtCenterX.setColumns(10);
		txtCenterY = new JTextField();
		txtCenterY.setColumns(10);
		txtRadius = new JTextField();
		txtRadius.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(96)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCenterX)
						.addComponent(lblCenterY)
						.addComponent(lblRadius))
					.addPreferredGap(ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtRadius)
						.addComponent(txtCenterY)
						.addComponent(txtCenterX, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(118, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(30)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCenterX)
						.addComponent(txtCenterX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCenterY)
						.addComponent(txtCenterY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(50)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRadius)
						.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(53, Short.MAX_VALUE))
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
						getRootPane().setDefaultButton(okButton);
						isOk = true;
						dispose();
						
					}
				});
				
				btnSetOuterColor = new JButton("Set outer color");
				btnSetOuterColor.setForeground(Color.GRAY);
				btnSetOuterColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Color color = JColorChooser.showDialog(null, "Select a color", btnSetOuterColor.getBackground());
						btnSetOuterColor.setBackground(color);
						outerClr = color;
					}
				});
				buttonPane.add(btnSetOuterColor);
				
				btnSetInnerColor = new JButton("Set inner color");
				btnSetInnerColor.setForeground(Color.GRAY);
				buttonPane.add(btnSetInnerColor);
				btnSetInnerColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Color color = JColorChooser.showDialog(null, "Select a color", btnSetInnerColor.getBackground());
						btnSetInnerColor.setBackground(color);
						clr = color;
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				dispose();
			}
		}
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



	public Circle getCircle() {
		return circle;
	}



	public void setCircle(Circle circle) {
		this.circle = circle;
	}



	public void setClr(Color clr) {
		this.clr = clr;
	}


	public JTextField getTxtRadius() {
		return txtRadius;
	}



	public void setTxtRadius(JTextField txtRadius) {
		this.txtRadius = txtRadius;
	}



	public Color getOuterClr() {
		return outerClr;
	}



	public void setOuterClr(Color outerClr) {
		this.outerClr = outerClr;
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
}

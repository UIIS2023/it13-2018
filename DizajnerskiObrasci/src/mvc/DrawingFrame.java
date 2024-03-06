package mvc;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;



public class DrawingFrame extends JFrame {
	
 
	private DrawingView view = new DrawingView();
	private DrawingController controller = new DrawingController();
  
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JToolBar toolBar;
	private JToggleButton tglbtnSelection;
	private JToggleButton tglbtnPoint;
	private JToggleButton tglbtnLine;
	private JToggleButton tglbtnRectangle;
	private JToggleButton tglbtnCircle;
	private JToggleButton tglbtnDonut;
	private JButton btnModify;
	private JButton btnDelete;
	private ButtonGroup group = new ButtonGroup();
	private JButton btnUndo;
	private JButton btnRedo;
	DefaultListModel<String> dlm = new DefaultListModel<String>();
	private JButton btnToFront;
	private JButton btnToBack;
	private JButton btnBringToFront;
	private JButton btnBringToBack;
	private JToolBar toolBar_colors;
	private JButton btnInnerColor;
	private JButton btnOuterColor;
	private JToggleButton tglbtnHexagon;
	private JList<String> list;
	private JScrollPane scrollPane;
	private JToolBar toolBar_undoRedo;
	private JMenuBar menuBar;
	private JMenu mnActions;
	private JMenuItem mntmSaveLog;
	private JMenuItem mntmSaveDrawing;
	private JMenuItem mntmLoadLog;
	private JMenuItem mntmLoadDrawing;
	private JButton btnLoadNext;

	
	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	

	public DrawingFrame() {
		
		setTitle("Vukovic Nikolina IT13-2018");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnActions = new JMenu("Actions");
		menuBar.add(mnActions);
		
		mntmSaveLog = new JMenuItem("Save Log");
		mnActions.add(mntmSaveLog);
		
		mntmLoadLog = new JMenuItem("Load Log");
		mntmLoadLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadLog();
			}
		});
		
		mntmSaveDrawing = new JMenuItem("Save Drawing");
		mnActions.add(mntmSaveDrawing);
		mntmSaveDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveDrawing();
			}
		});
		mnActions.add(mntmLoadLog);
		
		mntmLoadDrawing = new JMenuItem("Load Drawing");
		mntmLoadDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.loadDrawing();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		mnActions.add(mntmLoadDrawing);
		mntmSaveLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.saveLog();
			}
		});

		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		btnLoadNext = new JButton("Load Next");
		btnLoadNext.setVisible(false);

		btnLoadNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadNext();
			}
		});
		toolBar.add(btnLoadNext);
		
		tglbtnSelection = new JToggleButton("Selection");
		toolBar.add(tglbtnSelection);
		group.add(tglbtnSelection);
		
		tglbtnPoint = new JToggleButton("Point");
		toolBar.add(tglbtnPoint);
		group.add(tglbtnPoint);
		
		tglbtnLine = new JToggleButton("Line");
		toolBar.add(tglbtnLine);
		group.add(tglbtnLine);
		
		tglbtnRectangle = new JToggleButton("Rectangle");
		toolBar.add(tglbtnRectangle);
		group.add(tglbtnRectangle);
		
		tglbtnCircle = new JToggleButton("Circle");
		toolBar.add(tglbtnCircle);
		group.add(tglbtnCircle);
		
		tglbtnDonut = new JToggleButton("Donut");
		toolBar.add(tglbtnDonut);
		group.add(tglbtnDonut);
		
		tglbtnHexagon = new JToggleButton("Hexagon");
		toolBar.add(tglbtnHexagon);
		group.add(tglbtnHexagon);
		
		btnModify = new JButton("Modify");
		btnModify.setVisible(false);
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.modify();
			}
		});
		toolBar.add(btnModify);
		
		
		
		btnDelete = new JButton("Delete");
		btnDelete.setVisible(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.delete();
					}
		});
		toolBar.add(btnDelete);
		group.add(btnDelete);
		
		btnToFront = new JButton("To Front");
		btnToFront.setVisible(false);
		btnToFront.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				controller.toFront();
			}
		});
		toolBar.add(btnToFront);

		btnToBack = new JButton("To Back");
		btnToBack.setVisible(false); 
		btnToBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				controller.toBack();
			}
		});
		toolBar.add(btnToBack);

		btnBringToFront = new JButton("Bring To Front");
		btnBringToFront.setVisible(false);
		btnBringToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				controller.bringToFront();
			}
		});
		toolBar.add(btnBringToFront);

		btnBringToBack = new JButton("Bring To Back");
		btnBringToBack.setVisible(false);
		btnBringToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				controller.bringToBack();
			}
		});
		toolBar.add(btnBringToBack);

				
		
		
		toolBar_colors = new JToolBar();
		toolBar_colors.setOrientation(SwingConstants.VERTICAL);
		getContentPane().add(toolBar_colors, BorderLayout.WEST);
						btnOuterColor = new JButton("Outer Color");
						btnOuterColor.setForeground(Color.GRAY);
						btnOuterColor.setBackground(Color.BLACK);
						btnOuterColor.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								Color color = JColorChooser.showDialog(null, "Select a color", btnOuterColor.getBackground());
								btnOuterColor.setBackground(color);
							}
						});
						toolBar_colors.add(btnOuterColor);
		
				btnInnerColor = new JButton("Inner Color");
				btnInnerColor.setBackground(Color.WHITE);
				btnInnerColor.setForeground(Color.GRAY);

				
						btnInnerColor.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
				
								Color color = JColorChooser.showDialog(null, "Select a color", btnInnerColor.getBackground());
								btnInnerColor.setBackground(color);
							}
						});
						toolBar_colors.add(btnInnerColor);
		
		
		view.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				controller.thisMouseClicked(e);

			}
		});
		getContentPane().add(view, BorderLayout.CENTER);
		
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.SOUTH);
		
		list = new JList<String>();
//		contentPane.add(list, BorderLayout.SOUTH);
		scrollPane.setColumnHeaderView(list);
		list.setModel(dlm);
		scrollPane.setViewportView(list);
		
		toolBar_undoRedo = new JToolBar();
		toolBar_undoRedo.setOrientation(SwingConstants.VERTICAL);
		contentPane.add(toolBar_undoRedo, BorderLayout.EAST);
		
		
		btnUndo = new JButton("Undo");
		toolBar_undoRedo.add(btnUndo);
		
				btnRedo = new JButton("Redo");
				toolBar_undoRedo.add(btnRedo);
				btnRedo.setVisible(false);
				btnRedo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						controller.redo();
					}
				});
		btnUndo.setVisible(false);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				controller.undo();
			}
		});

	}
		
		
	
	public JButton getBtnLoadNext() {
		return btnLoadNext;
	}

	public void setBtnLoadNext(JButton btnLoadNext) {
		this.btnLoadNext = btnLoadNext;
	}

	public JToggleButton getTglbtnHexagon() {
		return tglbtnHexagon;
	}

	public void setTglbtnHexagon(JToggleButton tglbtnHexagon) {
		this.tglbtnHexagon = tglbtnHexagon;
	}

	public JToggleButton getTglbtnSelection() {
		return tglbtnSelection;
	}
	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}
	public JToggleButton getTglbtnLine() {
		return tglbtnLine;
	}
	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}
	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}
	public JToggleButton getTglbtnDonut() {
		return tglbtnDonut;
	}

	
	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public void setBtnInnerColor(JButton btnInnerColor) {
		this.btnInnerColor = btnInnerColor;
	}

	public JButton getBtnOuterColor() {
		return btnOuterColor;
	}

	public void setBtnOuterColor(JButton btnOuterColor) {
		this.btnOuterColor = btnOuterColor;
	}

	public JButton getBtnModify() {
		return btnModify;
	}

	public void setBtnModify(JButton btnModify) {
		this.btnModify = btnModify;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(JButton btnDelete) {
		this.btnDelete = btnDelete;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}

	public void setBtnToFront(JButton btnToFront) {
		this.btnToFront = btnToFront;
	}

	public JButton getBtnToBack() {
		return btnToBack;
	}

	public void setBtnToBack(JButton btnToBack) {
		this.btnToBack = btnToBack;
	}

	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}

	public void setBtnBringToFront(JButton btnBringToFront) {
		this.btnBringToFront = btnBringToFront;
	}

	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}

	public void setBtnBringToBack(JButton btnBringToBack) {
		this.btnBringToBack = btnBringToBack;
	}

	public void setBtnUndo(JButton btnUndo) {
		this.btnUndo = btnUndo;
	}

	public void setBtnRedo(JButton btnRedo) {
		this.btnRedo = btnRedo;
			
	}
	
	public JList<String> getList() {
		return list;
	}

	public void setList(JList<String> list) {
		this.list = list;
	}

	public JButton getBtnUndo() {
  	return btnUndo;
	}
  
	public JButton getBtnRedo() {
  	return btnRedo;
	}
	
	

}

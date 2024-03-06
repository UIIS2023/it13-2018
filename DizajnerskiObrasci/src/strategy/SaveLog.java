package strategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;


public class SaveLog implements Save {
	
	DefaultListModel<String> dlm;
	
	public SaveLog() {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void save(Object o) {
		
		this.dlm = (DefaultListModel<String>) o;
		JFileChooser fileChooser = new JFileChooser();
		
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			
			File file = fileChooser.getSelectedFile();
			
			try {
				String path = file.getPath() + ".txt";
				file = new File(path);
				FileWriter fileWriter = new FileWriter(file.getPath(), true);
				BufferedWriter buffWriter = new BufferedWriter(fileWriter);
				PrintWriter writer = new PrintWriter(buffWriter);
				
				for (int i = 0; i < dlm.getSize(); i++) {
					writer.write(dlm.get(i) + "\n");
				}
				writer.close();				
			}
			catch (FileNotFoundException e2) {
				e2.printStackTrace();
			}
			catch (IOException e1) {
				e1.printStackTrace();;
			}
		}
	}
		
}
	


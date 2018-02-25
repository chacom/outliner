package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import application.model.DataItemExt;

public class MarkdownExport implements Export{

	@Override
	public void export(String filePath, List<DataItemExt> data) throws IOException {

		FileOutputStream fos = new FileOutputStream(new File(filePath));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

		for(DataItemExt item : data) {
			
			exportTitle(bw, item.getTitle(), item.getItemLevel());
			exportText(bw, item.getItemText());
		}
			
		bw.close();

	}
	
	
	void exportTitle(BufferedWriter bw, String title, int level) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		sb.append("\n#");
		
		for(int i = 0; i < level; i++) {
			sb.append("#");
		}
		
		sb.append(" ");
		sb.append(title);
		
		bw.write(sb.toString());
	}
	
	void exportText(BufferedWriter bw, String text) throws IOException {
		
		if(!text.startsWith("\n")) {
			bw.write("\n");
		}
		bw.write(text);
	}
}

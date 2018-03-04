package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import application.model.DataItemExt;

public class CsvExport implements Export{

	
	int getBlocks(String text) 
	{
		int cnt = 0;
		boolean lastLineContrainText = false;
		
		String[] res = text.split("\n");
		
		for (String item : res) {
			if(!lastLineContrainText && (item.length() > 0)){
				lastLineContrainText = true;
				cnt++;
			}
			
			if(item.length() == 0) {
				lastLineContrainText = false;
			}
		}
		
		return cnt;
	}
	
	List<String> getBlock(int idx, String text) 
	{
		int cnt = 0;
		boolean lastLineContrainText = false;
		
		ArrayList<String> resList = new ArrayList<>();
		
		String[] res = text.split("\n");
		
		for (String item : res) {
			
			if(lastLineContrainText && (item.length() > 0)){
				if(cnt == idx) 
				{
					resList.add(item);
				}
			}
			
			if(!lastLineContrainText && (item.length() > 0)){
				lastLineContrainText = true;
				
				cnt++;
				
				if(cnt == idx) 
				{
					resList.add(item);
				}
			}
									
			if(item.length() == 0) {
				lastLineContrainText = false;
			}
		}
		
		return resList;
	}
	
	
	@Override
	public void export(String filePath, List<DataItemExt> data) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File(filePath));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

		for(DataItemExt item : data) {
			
			if(WhiteList.checkWhitelist(item.getItemText())) 
			{
				int blocks = getBlocks(item.getItemText());
				
				for(int i = 1; i <= blocks; i++) {
					List<String> line = new ArrayList<>();
					line.add(item.getTitle());
					List<String> block = getBlock(i, item.getItemText());
					line.addAll(block);
					exportLine(bw, line);
				}
			}
		}
			
		bw.close();
		
	}
	
void exportLine(BufferedWriter bw, List<String> enties) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		
		for(String entry : enties) {
			sb.append(entry + ";");
		}
		
		sb.append("\n");
		bw.write(sb.toString());
	}

}

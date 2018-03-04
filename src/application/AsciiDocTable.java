package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import application.model.DataItemExt;

public class AsciiDocTable implements Export {

	final static int ROW_LIMIT = 15;
	
	
	int getBlocks(String text) {
		int cnt = 0;
		boolean lastLineContrainText = false;

		String[] res = text.split("\n");

		for (String item : res) {
			if (!lastLineContrainText && (item.length() > 0)) {
				lastLineContrainText = true;
				cnt++;
			}

			if (item.length() == 0) {
				lastLineContrainText = false;
			}
		}

		return cnt;
	}

	List<String> getBlock(int idx, String text) {
		int cnt = 0;
		boolean lastLineContrainText = false;

		ArrayList<String> resList = new ArrayList<>();

		String[] res = text.split("\n");

		for (String item : res) {

			if (lastLineContrainText && (item.length() > 0)) {
				if (cnt == idx) {
					resList.add(item);
				}
			}

			if (!lastLineContrainText && (item.length() > 0)) {
				lastLineContrainText = true;

				cnt++;

				if (cnt == idx) {
					resList.add(item);
				}
			}

			if (item.length() == 0) {
				lastLineContrainText = false;
			}
		}

		return resList;
	}
	
	int export(BufferedWriter bw, List<List<String>> exportData,int startIdx, int maxRows, int maxColums) throws IOException {
		
		bw.write("[width=\"15%\"]\n");
		bw.write("|=======\n");
		
		for(int cnt = startIdx; cnt < (startIdx + maxRows); cnt++) {
			
			if(cnt >= exportData.size()) {
				break;
			}
			List<String> currData = exportData.get(cnt);
			exportLine(bw, currData, maxColums);
		}
	
		bw.write("|=======\n\n");
		
		bw.write("<<<\n\n");
	
		return startIdx + maxRows;
	}
	
	String getParentName(List<DataItemExt> data, int idx) {
		
		int level = data.get(idx).getItemLevel();
		for (int cnt = idx - 1; cnt >= 0; cnt--) {
			if(data.get(cnt).getItemLevel() < level) {
				return data.get(cnt).getTitle();
			}
		}
		throw new RuntimeException("out of bounds");
	}

	@Override
	public void export(String filePath, List<DataItemExt> data) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File(filePath));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

		List<List<String>> exportData = new ArrayList<>();

		for (int idx = 0; idx < data.size(); idx++) {
			DataItemExt item = data.get(idx);
			
			if (WhiteList.checkWhitelist(item.getItemText())) {
				int blocks = getBlocks(item.getItemText());

				for (int i = 1; i <= blocks; i++) {
					List<String> line = new ArrayList<>();
					line.add(getParentName(data,idx));
					line.add(item.getTitle());
					List<String> block = getBlock(i, item.getItemText());
					line.addAll(block);
					exportData.add(line);
				}
			}
		}
		

		int maxColumns = 0;
		for (List<String> currData : exportData) {

			if (maxColumns < currData.size()) {
				maxColumns = currData.size();
			}
		}
		
		export(bw, exportData,0,1000000, maxColumns);
		
		int entriesCnt = 0;
		while(entriesCnt < exportData.size()) 
		{
			export(bw, exportData,entriesCnt,ROW_LIMIT, maxColumns);
			entriesCnt += ROW_LIMIT;
		}
		
		bw.close();

	}

	void exportLine(BufferedWriter bw, List<String> enties, int maxColums) throws IOException {

		StringBuilder sb = new StringBuilder();

		int temp = maxColums - enties.size();

		for (String entry : enties) {
			sb.append("|" + entry);
		}

		for (int i = 0; i < temp; i++) {
			sb.append("|");
		}

		sb.append("\n");
		bw.write(sb.toString());
	}

}

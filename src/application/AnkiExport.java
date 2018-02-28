package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import application.model.DataItemExt;

public class AnkiExport implements Export {

	
	boolean checkWhitelist(String text) {
		String[] whitelist = { "A1", "V1", "R1", "W1" };

		for (String entry : whitelist) {
			if (text.contains(entry)) {
				return true;
			}
		}

		return false;
	}

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
			
			if (checkWhitelist(item.getItemText())) {
				int blocks = getBlocks(item.getItemText());

				for (int i = 1; i <= blocks; i++) {
					List<String> line = new ArrayList<>();
					line.add(getParentName(data,idx) + " " + item.getTitle() + " " + i);
					List<String> block = getBlock(i, item.getItemText());
					line.addAll(block);
					exportData.add(line);
				}
			}
		}
		
		for (List<String> currData : exportData) {

			exportLine(bw, currData);
		}
		
	
		bw.close();

	}

	void exportLine(BufferedWriter bw, List<String> enties) throws IOException {
		
		if(enties.size() >= 2) {
			
			bw.write(enties.get(0) + "\t");
			bw.write(enties.get(1));
			
			for (int idx = 2; idx < enties.size(); idx++) {
				bw.write("<div>" + enties.get(idx)+ "</div>");
			}
			bw.write("\n");
		}
	}

}

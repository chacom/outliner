package HolLib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.model.DataItem;
import application.model.DataItemExt;

public class HolLibImpl implements HolLibIF {
	
	private static  Map<String, NodeColor> colorMap = new HashMap<>();
	
	ArrayList<EntryData> entries = new ArrayList<>();
	
	ArrayList<DataItemExt> dataItems;
	
	public HolLibImpl() {
		colorMap.put("000000", NodeColor.Black);
		colorMap.put("0000ff", NodeColor.Blue);
		colorMap.put("00ffff", NodeColor.Cyan);
		colorMap.put("00ff00", NodeColor.Green);
		colorMap.put("ff00ff", NodeColor.Magenta);
		colorMap.put("ff0000", NodeColor.Red);
		colorMap.put("ffffff", NodeColor.White);
		colorMap.put("ffff00", NodeColor.Yellow);
	}

	@Override
	public List<DataItemExt> read(String FilePath) throws Exception {
		
		dataItems = new ArrayList<>();
		ArrayList<String> InputData = new ArrayList<>();
		Logger logger = Logger.getLogger("ErrorLog.txt");
		FileInputStream fis = new FileInputStream(new File(FilePath));
		BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
		
		logger.log( Level.INFO, "Read Input file");
		

		String Temp = br.readLine();
		
		while(Temp != null)
		{
			InputData.add(Temp);
			Temp = br.readLine();
		}
		fis.close();
		br.close();
		
		parseInputData(InputData);
		
		return dataItems;
	}
	
	void parseInputData(ArrayList<String> input)
	{
		for(int idx = 0; idx < input.size();idx++)
		{
			String line = input.get(idx);
			int currLevel = CheckLevel(line);
			
			if(currLevel > 0){
				entries.add(new EntryData(idx,currLevel));
			}
		}
		
		
		for(int idx = 0; idx < entries.size();idx++){
			EntryData entry = entries.get(idx);
			
			String currNodeLine = input.get(entry.start);
			DataItemExt node = parseNodeLine(currNodeLine);
			
			DataItemExt currItem = new DataItemExt(node);
			currItem.setItemId(UUID.randomUUID());
			int endIdx;
			
			if((entries.size()-1) > idx){
				
				endIdx = entries.get(idx+1).start - 1;
			}
			else{
				endIdx = input.size() - 1;
			}
			
			currItem.setItemText("");
			
			for(int cnt = entry.start + 1 ; cnt <= endIdx; cnt++){
				currItem.setItemText(currItem.getItemText()+ input.get(cnt) + "\n");
			}
			currItem.setItemLevel(entry.level);
			
			dataItems.add(currItem);
		}
	
		
	}
	
	DataItemExt parseNodeLine(String line)
	{
		DataItemExt di = new DataItemExt();
		String temp = line.substring(line.indexOf('\t')+1);
		int level = CheckLevel(line);
		
		di.setTitle(line.substring(0 + level, line.indexOf('\t')));
		
		String[] split = temp.split(",");
		
		if(colorMap.containsKey(split[1]))
		{
			di.setItemColor(colorMap.get(split[1]));	
		}
		else
		{
			di.setItemColor(NodeColor.Default);
		}
		//System.out.println(split);
		return di;
	}

	int CheckLevel(String line)
	{
		int Cnt = 0;
		if(line.startsWith(".", 0))
		{
			//Node detect
			do
			{
				Cnt++;
			}
			while('.' == line.charAt(Cnt));
		}
		
		return Cnt;
	}
	
	@Override
	public boolean write(String FilePath, List<DataItemExt> data) throws IOException {
 		FileOutputStream fos = new FileOutputStream(new File(FilePath));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
		
		String newline = System.getProperty("line.separator");
		
		bw.write("VERSION=2.0\n");
		bw.write("HM:BACKGROUND_COLOR=cccccc\n");
		bw.write("HO:AUTO_NUMBERING=OFF\n");
		
		for(int idx = 0; idx < data.size();idx++){
			DataItemExt item = data.get(idx);
			if(item.getItemLevel() >= 0){
				String levStr = buildLevelString(item.getItemLevel());
				String colStr = buildColorString(item.getItemColor());
				
				String temp = levStr + item.getTitle() + "\t0,"+ colStr + ",0,0," + (idx+1) + "\n";
				bw.write(temp);
				if(item.getItemText().endsWith(newline)){
					bw.write(item.getItemText());
				}
				else{
					bw.write(item.getItemText() + newline);
				}
				
				
			}
		}
		
		bw.close();
		
		
		return false;
	}
	
	String buildLevelString(int level){
		String res = ".";
		for(int idx = 0; idx < level; idx++){
			res = res + ".";
		}
		
		return res;
	}
	
	String buildColorString(NodeColor color)
	{
		String res = "";
		Object[] keys = colorMap.keySet().toArray();
		Object[] values = colorMap.values().toArray();
	
		for(int idx = 0; idx < values.length; idx++){
			if(values[idx] == color){
				res = (String)keys[idx];
				return res;
			}
		}
		return res;
	}

}

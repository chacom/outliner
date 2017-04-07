package HolLib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.model.DataItem;

public class HolLibImpl implements HolLibIF {
	
	private static  Map<String, Color> colorMap = new HashMap<>();
	
	ArrayList<EntryData> entries = new ArrayList<>();
	
	ArrayList<DataItem> dataItems;
	
	public HolLibImpl() {
		colorMap.put("000000", Color.Black);
		colorMap.put("0000ff", Color.Blue);
		colorMap.put("00ffff", Color.Cyan);
		colorMap.put("00ff00", Color.Green);
		colorMap.put("ff00ff", Color.Magenta);
		colorMap.put("ff0000", Color.Red);
		colorMap.put("ffffff", Color.White);
		colorMap.put("ffff00", Color.Yellow);
	}

	@Override
	public List<DataItem> read(String FilePath) throws Exception {
		
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
			DataItem node = parseNodeLine(currNodeLine);
			
			DataItem currItem = new DataItem(node);
			currItem.setItemId(idx);
			int endIdx;
			
			if((entries.size()-1) > idx){
				
				endIdx = entries.get(idx+1).start - 1;
			}
			else{
				endIdx = entries.size() - 1;
			}
			
			currItem.setItemText("");
			
			for(int cnt = entry.start + 1 ; cnt <= endIdx; cnt++){
				currItem.setItemText(currItem.getItemText()+ input.get(cnt) + "\n");
			}
			currItem.setItemLevel(entry.level);
			
			dataItems.add(currItem);
		}
	
		
	}
	
	DataItem parseNodeLine(String line)
	{
		DataItem di = new DataItem();
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
			di.setItemColor(Color.Black);
		}
		System.out.println(split);
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
	public boolean write(String FilePath, List<DataItem> data) {
		// TODO Auto-generated method stub
		return false;
	}

}

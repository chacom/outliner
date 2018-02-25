package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import application.model.DataItem;
import application.model.DataItemExt;

public class JsonExport implements Export{

	OrderedDataItemExt baseItem = null;
	
	@Override
	public void export(String filePath, List<DataItemExt> data) throws IOException {

		FileOutputStream fos = new FileOutputStream(new File(filePath));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		OrderedDataItem ordItem = createOrderedDataitems(data);
		
		gson.toJson(ordItem, bw);
		
				
		bw.close();

	}
	
	
	OrderedDataItem createOrderedDataitems(List<DataItemExt> data){
		
		OrderedDataItemExt lastItem = null;
		
		for(DataItemExt item : data){
			OrderedDataItemExt ordItem = new OrderedDataItemExt(item);
			
			if(baseItem == null){
				baseItem = ordItem;
				baseItem.parent = null;
				baseItem.children = new ArrayList<>();
				lastItem = baseItem;
			}
			else{
			
				if(lastItem.itemLevel == item.getItemLevel()){
					OrderedDataItemExt temp = new OrderedDataItemExt(item);
					lastItem.parent.children.add(temp);
					temp.parent = lastItem.parent;
					lastItem = temp;
					
				}
				if(lastItem.itemLevel < item.getItemLevel()){
					OrderedDataItemExt temp = new OrderedDataItemExt(item);
					lastItem.children.add(temp);
					temp.parent = lastItem;
					lastItem = temp;
				}
				if(lastItem.itemLevel > item.getItemLevel()){
					
					boolean matchFound = false;
					
					while(!matchFound){
						
						if(lastItem.parent.itemLevel < item.getItemLevel()){
							lastItem = lastItem.parent;
						}
						else{
							OrderedDataItemExt temp = new OrderedDataItemExt(item);
							lastItem.parent.children.add(temp);
							temp.parent = lastItem.parent;
							lastItem = temp;
							matchFound = true;
						}
						
					}
					
				}
					
			}
			
			
		}
		OrderedDataItem newBaseItem = new OrderedDataItem(baseItem.title, baseItem.itemId, baseItem.itemText, 
				baseItem.itemLevel, baseItem.itemColor, baseItem.nodeCnt);
		
		convertExtToOrdered(newBaseItem, baseItem);
		return newBaseItem;
		
	}
	
	
	void convertExtToOrdered(OrderedDataItem dstItem, OrderedDataItemExt item){
		for(OrderedDataItem child : item.children){
			
			OrderedDataItem newItem = new OrderedDataItem(child.title, child.itemId, child.itemText, 
					child.itemLevel, child.itemColor, child.nodeCnt);
			
			dstItem.children.add(newItem);
			
			convertExtToOrdered(newItem, (OrderedDataItemExt)child);
		}
		
	}
	
	class OrderedDataItemExt extends OrderedDataItem{
		OrderedDataItemExt parent = null;
		
		public OrderedDataItemExt(DataItem item) {
			super(item);
		}
	}

}

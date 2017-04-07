package application.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import HolLib.NodeColor;

public class DataStorage {

	int lastAddNodeId = 0;

	ArrayList<DataItem> nodeList = new ArrayList<>();
	
	ArrayList<ListChangeListener> listeners = new ArrayList<>();

	public DataStorage() {
		nodeList.add(new DataItem("root", NodeColor.Black, ""));

		nodeList.get(0).setItemId(0);
	}

	public void addChild(DataItem node) {

		nodeList.add(node);
		for(ListChangeListener listener : listeners){
			listener.onChange(ChangeType.Add, node);
		}

	}

	public void addChildren(List<DataItem> list) {

		for (DataItem item : list) {
			addChild(item);
		}
		nodeList.addAll(list);
	}

	public DataItem getRootNode() {
		return nodeList.get(0);
	}

	public int getLastAddNodeId() {
		return lastAddNodeId;
	}

	public Optional<String> getText(int nodeId) {
		for (DataItem item : nodeList) {
			if (item.itemId == nodeId) {
				return Optional.of(item.itemText);
			}
		}
		return Optional.empty();
	}

	public void cleanList() {
		nodeList.clear();
		lastAddNodeId = 0;
	}
	
	public void addListener(ListChangeListener currListener){
		listeners.add(currListener);
	}

}

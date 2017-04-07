package HolLib;

public class DataItem extends NodeInfo{
	String ItemText;
	int itemLevel;
	int itemId;
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemText() {
		return ItemText;
	}
	public void setItemText(String itemText) {
		ItemText = itemText;
	}
	public int getItemLevel() {
		return itemLevel;
	}
	public void setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
	}
	
	

}

package application.model;

public class ExtTreeItem {
	
	String text;
	int id;
	
	public ExtTreeItem(String text, int id) {
		this.text = text;
		this.id = id;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	public int getId(){
		return id;
	}

}

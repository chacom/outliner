package application.model;

public class ExtTreeItem {
	
	String text;
	int id;
	int level;
	
	public ExtTreeItem(String text, int id, int level) {
		this.text = text;
		this.id = id;
		this.level = level;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	public int getId(){
		return id;
	}
	
	public int getLevel(){
		return level;
	}

}

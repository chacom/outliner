package application.model;

public interface ListChangeListener {

	public void onChange(ChangeType type, DataItem item, int extInfo);
}
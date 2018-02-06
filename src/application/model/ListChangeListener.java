package application.model;

import java.util.UUID;

public interface ListChangeListener {

	public void onChange(ChangeType type, DataItem item, UUID parent);
}

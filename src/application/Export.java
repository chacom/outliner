package application;

import java.io.IOException;
import java.util.List;

import application.model.DataItemExt;

public interface Export {
	public void export(String filePath, List<DataItemExt> data) throws IOException;
}

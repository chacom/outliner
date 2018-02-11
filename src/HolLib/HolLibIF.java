package HolLib;

import java.util.List;

import application.model.DataItemExt;

public interface HolLibIF {
	
	List<DataItemExt> read(String FilePath) throws Exception;
	boolean write(String FilePath, List<DataItemExt> data) throws Exception;

}

package HolLib;

import java.util.List;

public interface HolLibIF {
	
	List<DataItem> read(String FilePath) throws Exception;
	boolean write(String FilePath, List<DataItem> data) throws Exception;

}

package HolLib;

import java.util.List;

public class Test {

	public static void main(String[] args) {
		
		HolLibIF myHolLib = new HolLibImpl();
		
		try {
			System.out.println(System.getProperty("user.dir"));
			myHolLib.read("test.hol");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			System.out.println(e.getMessage());
			
			
		}

	}

}

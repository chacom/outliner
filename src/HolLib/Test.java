package HolLib;

public class Test {

	public static void main(String[] args) {
		
		HolLibIF myHolLib = new HolLibImpl();
		
		try {
			System.out.println(System.getProperty("user.dir"));
			myHolLib.read("test.hol");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}

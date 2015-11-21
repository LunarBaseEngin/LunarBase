package LCG.DB.Dangerous;

public class readme {

	static String lunar_mmu_read_me = "this package uses the native memory solution LunarMemoryManagement Unit, \r\n"
			+ " which alloc and free memory directly from operation system via a bunch of apis. \r\n"
			+ " It is out of the JVM controll, and "
			+ " user who allocates must manully free. \r\n"
			+ "the example under this package tells how to do it ";
	public static void main(String[] args) {
		System.out.println(lunar_mmu_read_me);

	}

}

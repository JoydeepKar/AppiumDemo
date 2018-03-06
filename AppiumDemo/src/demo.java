import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class demo {
private Properties configProp = new Properties();
	
	public static void main(String[] args) {
		demo sample = new demo();
		sample.loadProps2();
		sample.sayHello();
	}

	public void loadProps1() {
		InputStream in = this.getClass().getResourceAsStream("/AppiumDemo/Objects/login.properties");
		try {
			configProp.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadProps2() {
		InputStream in = demo.class.getResourceAsStream("/login.properties");
		try {
			configProp.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sayHello() {
		System.out.println(configProp.getProperty("userName"));
	}
}

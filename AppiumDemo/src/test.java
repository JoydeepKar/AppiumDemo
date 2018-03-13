import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

class A{
	protected AndroidDriver<AndroidElement> driver;
	
}
interface GenericInterface<T>{
	void setT(T t);    
	T getT();
}
class test<T> implements GenericInterface<T>{
	T t; 
	@Override
	public void setT(T t){
		this.t = t;
	}
	
	@Override
	public T getT(){
		return t;
	}
	
	public static void main(String[] s) {
		test obj = new test<>();
		obj.setT(obj);
		
		A obj1 = new A();
		obj.setT(obj1);
		System.out.println(obj.getT());
	}
}

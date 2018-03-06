package com.eribank.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MapObject {
    private Properties configProp = new Properties();
    InputStream mapLoginObj = BaseEribank.class.getResourceAsStream("/login.properties");
	
    public void loadProps2() {
		try {
			configProp.load(mapLoginObj);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}

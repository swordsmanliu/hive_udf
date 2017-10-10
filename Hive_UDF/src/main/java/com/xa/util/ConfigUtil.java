package com.xa.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 配置文件操作
 * @author rootwang
 * 
 */

public class ConfigUtil {

	private Properties props = new Properties();

	public ConfigUtil(String file) {
		String path = "/" + file;
		InputStream is = null;
		try {
			is = ConfigUtil.class.getResourceAsStream(path);
			props.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int getInt(String key) {
		return Integer.parseInt(props.getProperty(key));
	}

	public String getString(String key) {
		return props.getProperty(key);
	}

}

package tech.csm.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.Part;

public class FileUtil {

	
	public static String getDirPath() {
		InputStream is = FileUtil.class.getClassLoader().getResourceAsStream("app.properties");
		Properties p=new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p.getProperty("upload.dir");
	}
	
	public static String uploadFile(Part part) {
		InputStream is = FileUtil.class.getClassLoader().getResourceAsStream("app.properties");
		Properties p=new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String filePath=p.getProperty("upload.dir")+part.getSubmittedFileName();
		try {
			part.write(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return part.getSubmittedFileName();
		
	}
	
}

package com.hl.rest.key;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GetKEY {
	
	/** 비밀키 읽기 */
	public static String getKey() {		
		String key = "";
		try {
			File file = new File("C:\\Users\\multicampus\\Desktop\\까마귀\\HelpHomework\\BACKEND\\SpringbootBackend\\SpringbootHelome\\src\\main\\java\\com\\hl\\rest\\key\\key.txt");
			FileReader filereader = new FileReader(file);
			int singleCh = 0;
			while ((singleCh = filereader.read()) != -1) {
				key += ((char) singleCh);
			}
			filereader.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			System.out.println(e);
		}
		return key;
	}
	
}

package com.emissenger.metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileDS {

	public InputStream convertfile(File filePath) throws FileNotFoundException {

		// String filePath = "D:/Photos/Tom.jpg";
		return new FileInputStream(filePath);
	}

}

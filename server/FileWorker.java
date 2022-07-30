package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FileWorker {

	public static String URL_FOLDER = "C:\\Users\\tranc\\Desktop\\Save";

	String[] getAllFileName() {
		File file = new File(URL_FOLDER);
		String[] files = file.list();

		return files;
	}

	public static void createFolder() {
		File file = new File(URL_FOLDER);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	String[] searchFile(String keyword) {
		File file = new File(URL_FOLDER);
		String[] files = file.list();
		ArrayList<String> fileSearches = new ArrayList<String>();
		for (String file1 : files)
			if (file1.contains(keyword) || searchStringInFile(URL_FOLDER + "\\" + file1, keyword))
				fileSearches.add(file1);
		String[] result = new String[fileSearches.size()];
		result = fileSearches.toArray(result);
		return result;
	}

	boolean searchStringInFile(String fileName, String keyword) {
		File file = new File(fileName);
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.contains(keyword)) {
					return true;
				}
			}
		} catch (FileNotFoundException e) {
		}
		return false;
	}

	public boolean checkFile(String fileNameReceived) {
		File file = new File(URL_FOLDER);
		String[] files = file.list();
		for (String file1 : files)
			if (file1.equals(fileNameReceived))
				return false;
		return true;
	}

	public String getFileName(String str) {
		String result = "";
		int len = str.length();
		for (int i = len - 1; i > 0; i--)
			if (str.charAt(i) == '\\')
				return (new StringBuilder(result)).reverse().toString();
			else
				result += str.charAt(i);

		return null;
	}
}

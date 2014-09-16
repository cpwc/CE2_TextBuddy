//*******************************************************************
// CS2103T (AY2014/15 Semester 1)
// CE1: Text Buddy
// Name: POH WEI CHENG
// Matric. No.: A0111815R
// Group: C05
// This program will take in a total of 4 points 2 each for a Rectangle.
// It will then calculate the total area that are being overlapped by the
// Rectangles using the 4 points given by the user.
//*******************************************************************

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class TextBuddy {
	private Path path;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(args[0]);

		if (args.length != 1) {
			System.out.println("Please input a filename or path.");
			return;
		}

		TextBuddy tb = new TextBuddy(args[0]);
		tb.run();
	}

	/**
	 * 
	 */
	public TextBuddy(String filePath) {
		this.path = Paths.get(filePath);
	}

	public void run() {
		if (isFileReady()) {
			System.out.println("Welcome to TextBuddy. " + getFileName()
					+ " is ready for use");
			Scanner sc = new Scanner(System.in);
			boolean stop = false;
			do {
				System.out.print("command: ");
				String command = sc.next();
				switch (command) {
				case "add":
					String content = sc.nextLine().trim();
					if (add(content)) {
						System.out.println("added to " + getFileName() + ": \""
								+ content + "\"");
					}
					break;
				case "display":
					if (!display()) {
						System.out.println(getFileName() + " is empty");
					}
					break;
				case "delete":
					int lineNumber = sc.nextInt();
					String deleted = delete(lineNumber);
					if (deleted != null) {
						System.out.println("deleted from " + getFileName()
								+ ": \"" + deleted + "\"");
					}
					break;
				case "clear":
					if (clear()) {
						System.out.println("all content deleted from "
								+ getFileName());
					}
					break;
				case "exit":
					stop = true;
					break;
				}
			} while (!stop);
			sc.close();
		}
	}

	public boolean isFileReady() {
		return Files.isReadable(path) && Files.isWritable(path);
	}

	public String getFileName() {
		return this.path.getFileName().toString();
	}

	public boolean add(String content) {
		content += "\r\n";
		try {
			Files.write(path, content.getBytes("UTF-8"),
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			return true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	public boolean display() {
		try {
			List<String> list = Files.readAllLines(path,
					Charset.defaultCharset());
			if (list.isEmpty()) {
				return false;
			}
			int lineNumber = 1;
			for (String line : list) {
				System.out.println(lineNumber + ". " + line);
				lineNumber++;
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean clear() {
		try {
			Files.write(path, new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public String delete(int lineNumber) {
		try {
			List<String> list = Files.readAllLines(path,
					Charset.defaultCharset());
			String temp = list.remove(lineNumber - 1);
			Files.write(path, list, Charset.defaultCharset(),
					StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);
			return temp;
		} catch (Exception ex) {
			return null;
		}
	}
}
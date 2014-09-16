import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * CS2103T (AY2014/15 Semester 1) CE2: TextBuddy++ Group: T17-3J
 * 
 * @author WeiCheng (A0111815R)
 * 
 */
public class TextBuddy {

	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %s is ready for use";
	private static final String MESSAGE_INPUT_FILE = "Please input a filename or path.";
	private static final String MESSAGE_ADDED = "added to %s: \"%s\"";
	private static final String MESSAGE_EMPTY = "%s is empty";
	private static final String MESSAGE_DELETED = "deleted from %s: \"%s\"";
	private static final String MESSAGE_CLEAR = "all content deleted from %s";
	private static final String MESSAGE_LINE_NUMBER = "%s. %s";
	private static final String MESSAGE_SORT_SUCCESS = "sorted %s successfully";

	private static final String ERROR_IO = "Unexpected IO error.";
	private static final String ERROR_CLEARING_TEXT = "Error clearing text file. Please try again.";

	private static Path path;

	/**
	 * This is the main function which dictates the flow of the program. All the
	 * functionality is abstracted out to other methods.
	 * 
	 * @param args
	 *            contains the filename entered by the user.
	 */
	public static void main(String[] args) {

		if (args.length == 0) {
			printMessage(MESSAGE_INPUT_FILE);
			return;
		}

		TextBuddy tb = new TextBuddy(args[0]);
		if (tb.checkFileStatus()) {
			tb.run();
		} else {
			printMessage(ERROR_IO);
		}

	}

	/**
	 * TextBuddy Constructor
	 * 
	 * @param filePath
	 *            of text file for program to manipulate.
	 */
	public TextBuddy(String filePath) {
		path = Paths.get(filePath);
	}

	/**
	 * Check if file exist. If does not exist create it else use existing.
	 * 
	 * @return true if file exist.
	 */
	public boolean checkFileStatus() {
		try {
			File file = new File(path.toString());
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException ex) {
			return false;
		}

		return true;
	}

	/**
	 * Overall logic of application for handling of command and method calls for
	 * whole application.
	 */
	public void run() {
		printMessage(String.format(MESSAGE_WELCOME, getFileName()));

		Scanner sc = new Scanner(System.in);
		boolean exit = false;

		do {
			System.out.print("command: ");
			String command = sc.next();

			switch (command) {
			case "add":
				addTextToFile(sc.nextLine().trim());
				break;
			case "display":
				displayTextToScreen();
				break;
			case "delete":
				deleteTextFromFile(sc.nextInt());
				break;
			case "clear":
				clearTextFromFile();
				break;
			case "sort":
				sortTextFromFile();
				break;
			case "search":
				searchTextFromFile(sc.nextLine().trim());
				break;
			case "exit":
				exit = true;
				break;
			}
		} while (!exit);

		sc.close();
	}

	/**
	 * Method to call addText function from command method.
	 * 
	 * @param content
	 *            to be added to file.
	 */
	private static void addTextToFile(String content) {
		if (addText(content)) {
			printMessage(String.format(MESSAGE_ADDED, getFileName(), content));
		}
	}

	/**
	 * Method to call displayText function from command method.
	 */
	private static void displayTextToScreen() {
		if (displayText()) {
			return;
		}

		printMessage(String.format(MESSAGE_EMPTY, getFileName()));
	}

	/**
	 * Method to call deleteText function from command method.
	 * 
	 * @param lineNumber
	 *            of the text to be deleted.
	 */
	private static void deleteTextFromFile(int lineNumber) {
		String deleted = deleteText(lineNumber);

		if (deleted == null) {
			return;
		}

		printMessage(String.format(MESSAGE_DELETED, getFileName(), deleted));
	}

	/**
	 * Method to call clearText function from command method.
	 */
	private static void clearTextFromFile() {
		if (clearText()) {
			printMessage(String.format(MESSAGE_CLEAR, getFileName()));
		} else {
			printMessage(ERROR_CLEARING_TEXT);
		}
	}

	private static void sortTextFromFile() {
		List<String> list = sortText();

		if (list == null) {
			printMessage("Unexpected error!");
		} else if (list.isEmpty()) {
			printMessage(String.format(MESSAGE_EMPTY, getFileName()));
		} else {
			printMessage(String.format(MESSAGE_SORT_SUCCESS, getFileName()));
		}

	}

	private static void searchTextFromFile(String keyword) {
		List<String> list = searchText(keyword);
		
		if (list == null) {
			printMessage("Unexpected error!");
		} else if (list.isEmpty()) {
			printMessage(String.format(MESSAGE_EMPTY, getFileName()));
		} else {
			printMessage(String.format(MESSAGE_SORT_SUCCESS, getFileName()));
		}
	}

	/**
	 * Get the filename of the text file.
	 * 
	 * @return filename of the text file.
	 */
	private static String getFileName() {
		return path.getFileName().toString();
	}

	/**
	 * To add content to text file.
	 * 
	 * @param content
	 *            to be appended to text file.
	 * @return true if successfully added content to text file.
	 */
	public static boolean addText(String content) {
		content += "\r\n";

		try {
			Files.write(path, content.getBytes(), StandardOpenOption.CREATE,
					StandardOpenOption.APPEND);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Displays all the contents within text file.
	 * 
	 * @return true if successfully displayed all the contents from text file.
	 */
	public static boolean displayText() {
		try {
			List<String> list = Files.readAllLines(path,
					Charset.defaultCharset());

			if (list.isEmpty()) {
				return false;
			}

			int lineNumber = 1;
			for (String line : list) {
				printMessage(String.format(MESSAGE_LINE_NUMBER, lineNumber,
						line));
				lineNumber++;
			}

			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Clears the content of text file.
	 * 
	 * @return true if the overwriting file with empty contents is successful.
	 */
	public static boolean clearText() {
		try {
			Files.write(path, new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Delete text with given line number form text file.
	 * 
	 * @param lineNumber
	 *            the line number to be deleted from text file.
	 * @return true if line is successfully deleted from text file.
	 */
	private static String deleteText(int lineNumber) {
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

	public static List<String> sortText() {
		try {
			List<String> list = Files.readAllLines(path,
					Charset.defaultCharset());

			Collections.sort(list, new SortIgnoreCase());
			Files.write(path, list, Charset.defaultCharset(),
					StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);

			return list;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static List<String> searchText(String keyword) {
		try {
			List<String> list = Files.readAllLines(path,
					Charset.defaultCharset());
			
			List<String> filteredList = new ArrayList<String>();
			
			for (String line : list) {
				if (line.toLowerCase().contains(keyword.toLowerCase())) {
					filteredList.add(line);
				}
			}

			return filteredList;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Method to call System.out.println to console.
	 * 
	 * @param message
	 */
	private static void printMessage(String message) {
		System.out.println(message);
	}
}
package Store;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * A class for managing storing processes.
 * @author Mika Thein
 * @version 1.0
 */
public class Storage {
	
	/**
	 * @param path The file path (relative to the Storage directory).
	 * @return The file with that path.
	 */
	public static File getFile(String path) {
		try {
			return new File(new File(Storage.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getAbsolutePath() + "/Storage/" + path);
		} catch(Exception e) { e.printStackTrace(); return null; }
	}
	
	/**
	 * The default file extensions of files storing data.
	 */
	public static final String defaultFileExtension = "json";
	
	/**
	 * @param id The ID of the entity which data is supposed to be stored.
	 * @return A recommended file for storing data about an entity with an ID.
	 */
	public static File getFileFor(String id) {
		return getFile(id.charAt(id.length()-1) + "." + defaultFileExtension);
	}
	
	/**
	 * @param f The file
	 * @return The text of the file
	 */
	public static String read(File f) {
		try {
			BufferedReader r = new BufferedReader(new FileReader(f));
			StringBuilder b = new StringBuilder();
			for(String l; (l = r.readLine()) != null; b.append(l));
			r.close(); return b.toString();
		} catch(Exception e) { e.printStackTrace(); return null; }
	}
	
	/**
	 * Writes a file.
	 * @param text The text.
	 * @param f The file.
	 * @return Whether the process was a success or not.
	 * @see #write(String, File, boolean)
	 */
	public static boolean write(String text, File f) {
		return write(text, f, false);
	}
	
	/**
	 * Writes a file.
	 * @param text The text.
	 * @param f The file.
	 * @param append Whether to append the text to the end of the file or replace file contents.
	 * @return Whether the process was a success or not.
	 * @write {@link #write(String, File)}
	 */
	public static boolean write(String text, File f, boolean append) {
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter(f, append));
			w.write(text);
			w.close(); return true;
		} catch(Exception e) { e.printStackTrace(); return false; }
	}

}

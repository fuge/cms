package foo.common.fck;

import java.util.HashMap;
import java.util.Map;

/**
 * File Browser <code>GET</code> and <code>POST</code> commands.<br />
 * The File Browser sends a specific command for each and every request. This
 * class is intended to reflect these in an Enum-like manner.
 * <p>
 * The commands are for <code>GET</code>:
 * <ul>
 * <li>GetFolders</li>
 * <li>GetFoldersAndFiles</li>
 * <li>CreateFolder</li>
 * </ul>
 * and for <code>POST</code>:
 * <ul>
 * <li>FileUpload</li>
 * <li>QuickUpload</li>
 *</ul>
 * 
 * @version $Id: Command.java,v 1.1 2011/12/06 01:36:06 administrator Exp $
 */
public class Command {

	private String name;
	private static final Map<String, Command> getCommands = new HashMap<String, Command>(
			3);
	private static final Map<String, Command> postCommands = new HashMap<String, Command>(
			2);
	/** GET command <code>GetFolders</code> */
	public static final Command GET_FOLDERS = new Command("GetFolders");
	/** GET command <code>GetFoldersAndFiles</code> */
	public static final Command GET_FOLDERS_AND_FILES = new Command(
			"GetFoldersAndFiles");
	/** GET command <code>CreateFolder</code> */
	public static final Command CREATE_FOLDER = new Command("CreateFolder");
	/** POST command <code>FileUpload</code> */
	public static final Command FILE_UPLOAD = new Command("FileUpload");
	/** POST command <code>QuickUpload</code> */
	public static final Command QUICK_UPLOAD = new Command("QuickUpload");

	static {
		// initialize the get commands
		getCommands.put(GET_FOLDERS.getName(), GET_FOLDERS);
		getCommands.put(GET_FOLDERS_AND_FILES.getName(), GET_FOLDERS_AND_FILES);
		getCommands.put(CREATE_FOLDER.getName(), CREATE_FOLDER);

		// initialize the post commands
		postCommands.put(FILE_UPLOAD.getName(), FILE_UPLOAD);
		postCommands.put(QUICK_UPLOAD.getName(), QUICK_UPLOAD);
	}

	/**
	 * Constructs a command with the given name.
	 * 
	 * @param name
	 *            the name of the new command
	 */
	private Command(final String name) {
		this.name = name;
	}

	/**
	 * Returns the name of this command.
	 * 
	 * @return the name of this command
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the command constant with the specified name.
	 * 
	 * @param name
	 *            the name of the constant to return
	 * @return the command constant with the specified name
	 * @throws IllegalArgumentException
	 *             if this class has no constant with the specified name
	 * @throws NullPointerException
	 *             if <code>name</code> is null or empty
	 */
	public static Command valueOf(final String name) {
		if (Utils.isEmpty(name))
			throw new NullPointerException("Name is null or empty");

		Command command = getCommands.get(name);
		if (command == null)
			command = postCommands.get(name);
		if (command == null)
			throw new IllegalArgumentException("No command const " + name);

		return command;
	}

	/**
	 * Returns <code>true</code> if name represents a valid <code>GET</code>
	 * command constant.
	 * 
	 * @param name
	 *            the command to check
	 * @return <code>true</code> if name represents a valid command, else
	 *         <code>false</code>
	 */
	public static boolean isValidForGet(final String name) {
		return getCommands.containsKey(name);
	}

	/**
	 * Returns <code>true</code> if name represents a valid <code>POST</code>
	 * command constant.
	 * 
	 * @param name
	 *            the command to check
	 * @return <code>true</code> if name represents a valid command, else
	 *         <code>false</code>
	 */
	public static boolean isValidForPost(final String name) {
		return postCommands.containsKey(name);
	}

	/**
	 * Returns the command constant with the specified name. In contrast to
	 * {@link #valueOf(String)} it returns a null instead of throwing an
	 * exception if a command constant was not found.
	 * 
	 * @param name
	 *            the name of the constant to return
	 * @return the command constant with the specified name, else
	 *         <code>null</code>
	 */
	public static Command getCommand(final String name) {
		try {
			return Command.valueOf(name);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Compares the specified object with this command for equality. The
	 * comparison is based on class and name only.
	 * 
	 * @param obj
	 *            Object to be compared with this command.
	 * @return <code>true</code> if the specified object is equal to this
	 *         command
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || this.getClass() != obj.getClass())
			return false;

		final Command command = (Command) obj;
		return name.equals(command.getName());
	}

	/**
	 * Returns the hash code value for this command. The hash code equals the
	 * hash code of the name field.
	 * 
	 * @return the hash code value for this command
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	/**
	 * Returns a string representation of this command.
	 * 
	 * @return a string representation of this command
	 * @see #getName()
	 */
	@Override
	public String toString() {
		return name;
	}
}

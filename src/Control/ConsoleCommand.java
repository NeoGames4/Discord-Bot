package Control;

import net.dv8tion.jda.api.JDA;

/**
 * A class for console commands.
 * @author Mika Thein
 * @version 1.0
 */
public class ConsoleCommand {
	
	/**
	 * The name of the command.
	 */
	public final String name;
	/**
	 * Whatever is supposed to happen whenever the command was executed.
	 */
	public final Action action;
	/**
	 * The names of all necessary arguments.
	 */
	public final String[] arguments;
	
	/**
	 * Creates a new console command instance.
	 * @param name the name
	 * @param action the action
	 * @param arguments the argument names
	 * @see #name
	 * @see #action
	 * @see #arguments
	 */
	public ConsoleCommand(String name, Action action, String... arguments) {
		this.name = name;
		this.action = action;
		this.arguments = arguments;
	}
	
	/**
	 * This class is being used to define console command actions.
	 * @author Mika Thein
	 * @version 1.0
	 */
	public static abstract class Action {
		/**
		 * Whatever is supposed to happen whenever the command was executed.
		 * @param arguments all given arguments
		 * @param jda the JDA
		 */
		abstract void execute(String[] arguments, JDA jda);
	}

}

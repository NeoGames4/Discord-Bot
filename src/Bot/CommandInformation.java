package Bot;

import java.util.HashMap;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;

/**
 * A class for more precise command information.
 * @author Mika Thein
 * @version 1.0
 */
public class CommandInformation {
	
	/**
	 * The name and description of a command.
	 */
	public final String name,
			description;
	/**
	 * Auto complete suggestions.
	 * @see CommandInterface.AutoComplete
	 */
	public final CommandInterface.AutoComplete[] autoComplete;
	/**
	 * The command category.
	 * @see CommandCategory
	 */
	public final CommandCategory category;
	/**
	 * The default permissions.
	 * @see DefaultMemberPermissions#ENABLED
	 * @see DefaultMemberPermissions#DISABLED
	 * @see DefaultMemberPermissions#enabledFor(java.util.Collection)
	 * @see DefaultMemberPermissions#enabledFor(net.dv8tion.jda.api.Permission...)
	 * @see DefaultMemberPermissions#enabledFor(long)
	 */
	public DefaultMemberPermissions defaultPermissions;
	/**
	 * Whether the command should work in guilds only.
	 */
	public boolean guildOnly = false;
	
	/**
	 * Creates a new command information instance, which is used for pushing the command to the bot.
	 * @param name The name of the command.
	 * @param description The description of the command.
	 * @param autoComplete Auto complete suggestions.
	 * @param category The command category.
	 */
	public CommandInformation(String name, String description, CommandInterface.AutoComplete[] autoComplete, CommandCategory category) {
		this.name = name;
		this.description = description;
		this.autoComplete = autoComplete == null ? new CommandInterface.AutoComplete[0] : autoComplete;
		this.category = category;
	}
	
	private static final HashMap<String, Long> GLOBAL_IDS = new HashMap<>();
	
	/**
	 * @return The global ID of this command (or -1 if it could not be loaded yet).
	 * @see #getGlobalId(String)
	 * @see #loadGlobalIds(JDA)
	 */
	public long getGlobalId() {
		return getGlobalId(name);
	}
	
	/**
	 * @param name The name of the command.
	 * @return The global ID of that command (or -1 if it could not be loaded yet).
	 * @see #getGlobalId()
	 * @see #loadGlobalIds(JDA)
	 */
	public static long getGlobalId(String name) {
		return GLOBAL_IDS.containsKey(name) ? GLOBAL_IDS.get(name) : -1;
	}
	
	/**
	 * Retrieves all global command IDs and stores them into the cache.
	 * @param jda The JDA.
	 * @see #getGlobalId(String)
	 * @see #getGlobalId()
	 */
	public static void loadGlobalIds(JDA jda) {
		jda.retrieveCommands().queue(d -> {
			for(Command c : d) {
				if(c.getType().equals(Command.Type.SLASH)) GLOBAL_IDS.put(c.getName(), c.getIdLong());
			}
		});
	}

}

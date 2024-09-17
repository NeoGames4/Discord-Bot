package Bot;

import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;

/**
 * An interface for user (context) commands.
 * @author Mika Thein
 * @version 1.0
 */
public interface UserCommandInterface {
	
	/**
	 * @return command name.
	 */
	public String getName();
	
	/**
	 * @return default member permissions.
	 */
	public DefaultMemberPermissions getDefaultPermissions();
	
	/**
	 * @return whether this command should be available on guilds only.
	 */
	public boolean isGuildOnly();
	
	/**
	 * Whatever supposed to happen whenever the command was executed.
	 * @param e the message context interaction event
	 */
	public void execute(UserContextInteractionEvent e);

}

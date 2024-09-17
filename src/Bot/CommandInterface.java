package Bot;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

/**
 * A command interface.
 * @author Mika Thein
 * @version 1.0
 */
public interface CommandInterface {
	
	/**
	 * @return command information.
	 * @see CommandInformation
	 */
	public CommandInformation getInfo();
	
	/**
	 * @return subcommand groups.
	 */
	public SubcommandGroupData[] getSubcommandGroups();
	
	/**
	 * <b>Will be ignored if subcommand groups exist.</b>
	 * @return subcommands.
	 */
	public SubcommandData[] getSubcommands();
	
	/**
	 * <b>Will be ignored if subcommands or subcommand groups exist.</b>
	 * @return command options.
	 */
	public OptionData[] getOptions();
	
	/**
	 * Whatever supposed to happen whenever the command was executed.
	 * @param e the slash command interaction event
	 */
	public void execute(SlashCommandInteractionEvent e);
	
	/**
	 * An interface for auto complete for commands.
	 * @author Mika Thein
	 * @version 1.0
	 */
	public interface AutoComplete {
		
		public String getName();
		
		public void execute(CommandAutoCompleteInteractionEvent e);
		
	}

}

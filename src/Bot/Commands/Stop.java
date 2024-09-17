package Bot.Commands;

import Bot.CommandCategory;
import Bot.CommandInformation;
import Bot.CommandInterface;
import Bot.Launcher;
import Bot.Buttons.StopConfirmation;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class Stop implements CommandInterface {
	
	public CommandInformation getInfo() {
		return new CommandInformation("stop", "Stops the bot (administrators only).", null, CommandCategory.BOT);
	}
	
	public SubcommandGroupData[] getSubcommandGroups() {
		return new SubcommandGroupData[] {};
	}
	
	public SubcommandData[] getSubcommands() {
		return new SubcommandData[] {};
	}
	
	public OptionData[] getOptions() {
		return new OptionData[] {};
	}
	
	public void execute(SlashCommandInteractionEvent e) {
		for(String o : Launcher.OPS) {
			if(e.getUser().getId().equals(o)) {
				e.reply("Are you sure about shutting me down?").addActionRow(Button.success(StopConfirmation.id + ":yes", "Yes, I am"), Button.danger(StopConfirmation.id + ":no", "No, please cancel")).setEphemeral(true).queue();
			} else e.reply(Launcher.NO_PERMISSIONS_MESSAGE);
		}
	}

}

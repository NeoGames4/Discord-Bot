package Bot.Commands;

import Bot.ColorSet;
import Bot.CommandCategory;
import Bot.CommandInformation;
import Bot.CommandInterface;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

public class Status implements CommandInterface {
	
	public CommandInformation getInfo() {
		return new CommandInformation("status", "Returns the status.", null, CommandCategory.BOT);
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
		e.deferReply(false).queue();
		long now = System.currentTimeMillis();
		e.getHook().sendMessage("Hello!").queue(event -> {
			long ping = System.currentTimeMillis() - now;
			e.getJDA().getRestPing().queue(restPing -> {
				EmbedBuilder embedBuilder = new EmbedBuilder();
				embedBuilder.setColor(ColorSet.GREEN);
				embedBuilder.setDescription("Ping (reaction time): " + ping + " ms\nGateway: " + e.getJDA().getGatewayPing() + " ms\nRest-API: " + restPing + " ms");
				event.editMessageEmbeds(embedBuilder.build()).queue();
			});
		});
	}

}

package Bot.Commands;

import java.util.ArrayList;
import java.util.List;

import Bot.ColorSet;
import Bot.CommandCategory;
import Bot.CommandInformation;
import Bot.CommandInterface;
import Bot.Launcher;
import Bot.SelectMenus.HelpCategory;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public class Help implements CommandInterface {
	
	public CommandInformation getInfo() {
		return new CommandInformation("help", "Returns a help menu.", null, CommandCategory.BOT);
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
		e.deferReply(true).queue();
		String category = CommandCategory.values()[0].title;
		e.getHook().sendMessageEmbeds(getEmbedBuilder(e.getUser(), e.getMember(), category).build()).setActionRow(getActionRow(category).getComponents()).setEphemeral(true).queue();
	}
	
	public static EmbedBuilder getEmbedBuilder(User user, Member member, String category) {
		double uptime = Math.round((System.currentTimeMillis() - Launcher.LAUNCH_EPOCH_MILLIS)/36000d)/100d;
		ArrayList<CommandInterface> commands = new ArrayList<>();
		for(CommandInterface c : Launcher.COMMANDS) {
			if(c.getInfo().category.title.equalsIgnoreCase(category)) commands.add(c);
		}
		EmbedBuilder embedBuilder = new EmbedBuilder()
				.setTitle("Help")
				.setAuthor(user.getName(), Launcher.GITHUB_URL, user.getEffectiveAvatarUrl())
				.setColor(member != null ? member.getGuild().getSelfMember().getColor() : ColorSet.GREEN)
				.setDescription("Welcome to P.R.I.D.E-Bot.\nThe bot is online for " + uptime + " hours now.")
				.setFooter(user.getAsTag(), user.getEffectiveAvatarUrl());
		if(Launcher.GITHUB_URL.length() > 0) embedBuilder.appendDescription("\n[GitHub](" + Launcher.GITHUB_URL + ")");
		if(Launcher.WEBSITE.length() > 0) embedBuilder.appendDescription((Launcher.GITHUB_URL.length() > 0 ? " • " : "\n") + "[Website](" + Launcher.WEBSITE + ")");
		ArrayList<ListObject> objects = new ArrayList<>();
		for(CommandInterface c : commands) {
			if(c.getSubcommandGroups() != null && c.getSubcommandGroups().length > 0) {
				for(SubcommandGroupData s : c.getSubcommandGroups()) {
					for(SubcommandData sd : s.getSubcommands()) objects.add(new ListObject(c.getInfo().name + " " + s.getName() + " " + sd.getName(), sd.getDescription(), c.getInfo().getGlobalId(), sd.getOptions()));
				}
			}
			if(c.getSubcommands() != null && c.getSubcommands().length > 0) {
				for(SubcommandData s : c.getSubcommands()) objects.add(new ListObject(c.getInfo().name + " " + s.getName(), s.getDescription(), c.getInfo().getGlobalId(), s.getOptions()));
			} else if(c.getSubcommandGroups() == null || c.getSubcommandGroups().length <= 0) objects.add(new ListObject(c.getInfo().name, c.getInfo().description, c.getInfo().getGlobalId(), c.getOptions()));
		}
		StringBuilder t = new StringBuilder(); int i = 0;
		for(ListObject l : objects) {
			if(embedBuilder.getFields().size() >= 24) {
				System.err.println("Warning: Cannot display all commands of category \"" + category + "\" (" + (commands.size()-i) + " missing)."); break;
			}
			String ct = (l.id > 0 ? "</" + l.name + ":" + l.id + ">" : "**/" + l.name + "**") + optionsToString(l.options) + " – " + l.description + "\n";
			if(t.length() + ct.length() <= 1025) t.append(ct);
			if(t.length() + ct.length() >= 1024 || i >= objects.size()-1) {
				embedBuilder.addField("Commands " + (embedBuilder.getFields().size()+1), t.substring(0, t.length()-1), false);
				t = new StringBuilder(ct);
			} i++;
		}
		embedBuilder.addField("Contributors", "[NeoGames4](https://twitter.com/mikathein)", false);
		return embedBuilder;
	}
	
	public static ActionRow getActionRow(String category) {
		StringSelectMenu.Builder s = StringSelectMenu.create(HelpCategory.id).setRequiredRange(1, 1);
		for(CommandCategory c : CommandCategory.values()) s.addOption(c.title, c.title, c.description, c.emoji);
		ArrayList<String> v = new ArrayList<>();
		v.add(category);
		s.setDefaultValues(v);
		return ActionRow.of(s.build());
	}
	
	private static String optionsToString(OptionData[] options) {
		if(options == null) return "";
		String str = "";
		for(OptionData o : options) {
			str += " [" + o.getName() + "]" + (o.isRequired() ? "*" : "");
		} return str.length() > 0 ? " `" + str.substring(1) + "`" : str;
	}
	
	private static class ListObject {
		
		public final String name, description;
		public final long id;
		public final OptionData[] options;
		
		public ListObject(String name, String description, long id, List<OptionData> options) {
			this.name = name;
			this.description = description;
			this.id = id;
			this.options = new OptionData[options.size()];
			for(int i = 0; i<options.size(); i++) this.options[i] = options.get(i);
		}
		
		public ListObject(String name, String description, long id, OptionData[] options) {
			this.name = name;
			this.description = description;
			this.id = id;
			this.options = options;
		}
	}

}

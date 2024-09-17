package Control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import Bot.CommandInformation;
import Bot.CommandInterface;
import Bot.Launcher;
import Bot.MessageCommandInterface;
import Bot.UserCommandInterface;
import Control.ConsoleCommand.Action;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

/**
 * This class is being used to manage console output.
 * @author Mika Thein
 * @version 1.0
 */
public class Console {
	
	/**
	 * The thread of the console command handler.
	 */
	public final Thread thread;
	/**
	 * All console commands.
	 */
	public static final ConsoleCommand[] commands = new ConsoleCommand[] {
			new ConsoleCommand("pushcommands", new Action() {
				@Override
				public void execute(String[] arguments, JDA jda) {
					ArrayList<CommandData> commandList = new ArrayList<>();
					for(CommandInterface c : Launcher.COMMANDS) commandList.add(Commands.slash(c.getInfo().name, c.getInfo().description).addOptions(c.getOptions() != null ? c.getOptions() : new OptionData[0]).addSubcommands(c.getSubcommands() != null ? c.getSubcommands() : new SubcommandData[0]).addSubcommandGroups(c.getSubcommandGroups() != null ? c.getSubcommandGroups() : new SubcommandGroupData[0]).setDefaultPermissions(c.getInfo().defaultPermissions != null ? c.getInfo().defaultPermissions : DefaultMemberPermissions.ENABLED).setGuildOnly(c.getInfo().guildOnly));
					for(MessageCommandInterface c : Launcher.MESSAGE_COMMANDS) commandList.add(Commands.message(c.getName()).setDefaultPermissions(c.getDefaultPermissions() != null ? c.getDefaultPermissions() : DefaultMemberPermissions.ENABLED).setGuildOnly(c.isGuildOnly()));
					for(UserCommandInterface c : Launcher.USER_COMMANDS) commandList.add(Commands.user(c.getName()).setDefaultPermissions(c.getDefaultPermissions() != null ? c.getDefaultPermissions() : DefaultMemberPermissions.ENABLED).setGuildOnly(c.isGuildOnly()));
					log("Pushing " + commandList.size() + " commands to " + jda.getGuilds().size() + " test servers…");
					for(String id : Launcher.TEST_SERVERS) {
						jda.getGuildById(id).updateCommands().addCommands(commandList).queue(d -> log("Pushed to " + id + " successfully."), failure -> errlog("Push failed for " + id + "!" + "\n-> " + failure.getLocalizedMessage() + ", " + failure.getMessage()));
					} log("Pushing globally…");
					jda.updateCommands().addCommands(commandList).queue(d -> {
						log("Global push sucessfully.");
						CommandInformation.loadGlobalIds(jda);
					}, failure -> errlog("Global push failed!" + "\n-> " + failure.getLocalizedMessage() + ", " + failure.getMessage()));
				}
			}),
			new ConsoleCommand("setstatus", new Action() {
				@Override
				public void execute(String[] arguments, JDA jda) {
					OnlineStatus status = arguments[0].equals("0") ? Launcher.defaultStatus.status : OnlineStatus.fromKey(arguments[0]);
					Activity activity = arguments[1].equals("0") ? Launcher.defaultStatus.activity : Activity.of(ActivityType.CUSTOM_STATUS, arguments[1]);
					jda.getPresence().setStatus(status);
					jda.getPresence().setActivity(activity);
				}
			}, "0|online|idle|dnd|invisible|offline", "0|message"),
			new ConsoleCommand("stop", new Action() {
				@Override
				public void execute(String[] arguments, JDA jda) {
					log("Shutting down…");
					jda.shutdown();
					log("Have a nice day!");
					System.exit(0);
				}
			}),
			new ConsoleCommand("help", new Action() {
				@Override
				public void execute(String[] arguments, JDA jda) {
					log("Console commands: pushcommands | setstatus ['0'|'online'|'idle'|'dnd'|'invisible'|'offline'] ['0'|message] | stop | help");
				}
			})
	};
	
	/**
	 * Creates a new Console instance (with a command handler included).<p>
	 * It's necessary to {@link #open()} the console first.
	 * @param jda the JDA
	 * @see #thread
	 * @see #commands
	 * @see Bot.Launcher
	 */
	public Console(JDA jda) {
		thread = new Thread(() -> {
			try(Scanner scanner = new Scanner(System.in)) {
				while(true) {
					try {
						String input = scanner.nextLine();
						for(ConsoleCommand c : commands) {
							if(input.split(" ")[0].equalsIgnoreCase(c.name)) {
								String[] arguments = input.trim().substring(c.name.length()).trim().split(" ", c.arguments.length);
								if(arguments.length < c.arguments.length) {
									String a = "";
									for(String b : c.arguments) a += " [" + b + "]";
									System.err.println("Missing arguments: " + a.substring(1));
								} else c.action.execute(arguments, jda);
							}
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			} catch(Exception e1) { e1.printStackTrace(); }
		});
	}
	
	/**
	 * Logs a message.
	 * @param obj the message.
	 */
	public static void log(Object obj) {
		System.out.println("[" + new SimpleDateFormat("dd/MM/yyyy'T'HH:mm:ss+z").format(new Date()) + "] " + obj.toString());
	}
	
	/**
	 * Logs an error message.
	 * @param obj the message.
	 */
	public static void errlog(Object obj) {
		System.err.println("[" + new SimpleDateFormat("dd/MM/yyyy'T'HH:mm:ss+z").format(new Date()) + "] " + obj.toString());
	}
	
	/**
	 * Starts the thread and opens the console.
	 * @return this instance.
	 */
	public Console open() {
		thread.start();
		return this;
	}

}

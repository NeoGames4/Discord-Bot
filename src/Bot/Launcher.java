package Bot;

import Bot.Buttons.StopConfirmation;
import Bot.Commands.Help;
import Bot.Commands.Status;
import Bot.Commands.Stop;
import Bot.SelectMenus.HelpCategory;
import Control.Console;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

/**
 * This class is used to launch the bot. Stores commands, buttons, ….
 * @author Mika Thein
 * @version 1.0
 * @see #main(String[])
 */
public class Launcher {
	
	/**
	 * All relevant bot commands (for the {@link InteractionHandler}).
	 * <br><b>Maximum amount: 100</b>
	 * @see #buttonEvents
	 * @see #modalEvents
	 * @see #MESSAGE_COMMANDS
	 * @see #USER_COMMANDS
	 * @see #selectMenuEvents
	 */
	public static final CommandInterface[] COMMANDS = new CommandInterface[] {new Status(), new Stop(), new Help()};
	/**
	 * All relevant bot button events (for the {@link InteractionHandler}).
	 * @see #COMMANDS
	 * @see #modalEvents
	 * @see #MESSAGE_COMMANDS
	 * @see #USER_COMMANDS
	 * @see #selectMenuEvents
	 */
	public static final ButtonInterface[] BUTTONS = new ButtonInterface[] {new StopConfirmation()};
	/**
	 * All relevant bot modal events (for the {@link InteractionHandler}).
	 * @see #COMMANDS
	 * @see #buttonEvents
	 * @see #MESSAGE_COMMANDS
	 * @see #USER_COMMANDS
	 * @see #selectMenuEvents
	 */
	public static final ModalInterface[] MODALS = new ModalInterface[] {};
	/**
	 * All relevant bot message (context) commands (application commands) (for the {@link InteractionHandler}).
	 * <br><b>Maximum amount: 5</b>
	 * @see #COMMANDS
	 * @see #buttonEvents
	 * @see #modalEvents
	 * @see #USER_COMMANDS
	 * @see #selectMenuEvents
	 */
	public static final MessageCommandInterface[] MESSAGE_COMMANDS = new MessageCommandInterface[] {};
	/**
	 * All relevant bot user (context) commands (for the {@link InteractionHandler}).
	 * <br><b>Maximum amount: 5</b>
	 * @see #COMMANDS
	 * @see #buttonEvents
	 * @see #modalEvents
	 * @see #MESSAGE_COMMANDS
	 * @see #selectMenuEvents
	 */
	public static final UserCommandInterface[] USER_COMMANDS = new UserCommandInterface[] {};
	/**
	 * All relevant bot select menu events (for the {@link InteractionHandler}).
	 * @see #COMMANDS
	 * @see #buttonEvents
	 * @see #modalEvents
	 * @see #MESSAGE_COMMANDS
	 * @see #USER_COMMANDS
	 */
	public static SelectMenuInterface[] selectMenus = new SelectMenuInterface[] {new HelpCategory()};
	
	/**
	 * The epoch milliseconds when the bot launched.
	 */
	public static final long LAUNCH_EPOCH_MILLIS = System.currentTimeMillis();
	
	/**
	 * All Discord users with administrator permissions. (E.g. for /stop.)
	 */
	public static final String[] OPS = {"360101926404358144"}; // @mikathein
	/**
	 * All Discord test servers. (For updating commands instantly.)
	 */
	public static final String[] TEST_SERVERS = {"772752505129664522"};
	
	/**
	 * The prefix of every error message.
	 */
	public static final String ERROR_PREFIX = "**Oh no!** ";
	/**
	 * The error message for missing permissions.
	 */
	public static final String NO_PERMISSIONS_MESSAGE = ERROR_PREFIX + " You don't have enough permissions.";
	
	/**
	 * The URL to the GitHub repository.
	 */
	public static final String GITHUB_URL = "https://github.com/NeoGames4/Discord-Bot";
	/**
	 * The URL to the bot's website.
	 */
	public static final String WEBSITE = "";
	
	/**
	 * The console.
	 * @see Control.Console
	 */
	public static Console console;
	
	/**
	 * The default status of the bot.
	 */
	public static BotStatus defaultStatus = new BotStatus(OnlineStatus.DO_NOT_DISTURB, Activity.of(ActivityType.LISTENING, "my code"));
	
	/**
	 * Launches the bot.
	 * @param args the token (args[0])
	 */
	public static void main(String[] args) {
		if(args.length < 1) throw new RuntimeException("Missing token: Please use the bot token as the first argument.");
		try {
			JDA jda = JDABuilder.createDefault(args[0])
					.enableIntents(GatewayIntent.getIntents(GatewayIntent.DEFAULT))
					.setMemberCachePolicy(MemberCachePolicy.ALL)
					.addEventListeners(new InteractionHandler())
					.setStatus(defaultStatus.status)
					.setActivity(defaultStatus.activity).build();
			console = new Console(jda).open();
			CommandInformation.loadGlobalIds(jda);
		} catch(Exception e) { e.printStackTrace(); }
	}

}

package Bot.Buttons;

import Bot.ButtonInterface;
import Bot.Tools;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class StopConfirmation implements ButtonInterface {
	
	public static final String id = "stopbot";
	
	public String getId() {
		return id;
	}
	
	public void execute(ButtonInteractionEvent e) {
		if(e.getButton().getId().endsWith("yes")) {
			e.editMessage(e.getMessage().getContentRaw()).setActionRow(Tools.disableAllButtons(e.getMessage().getButtons())).queue(event -> {
				e.getChannel().sendMessage("Shutting down! (Due to " + e.getUser().getAsMention() + ", after `" + ((System.currentTimeMillis() - Bot.Launcher.LAUNCH_EPOCH_MILLIS)/3600000l) + " h` uptime.)").queue(eventTwo -> {
					e.getJDA().shutdown();
					System.exit(0);
				});
			});
		} else e.editMessage(e.getMessage().getContentRaw()).setActionRow(Tools.disableAllButtons(e.getMessage().getButtons())).queue();
	}

}

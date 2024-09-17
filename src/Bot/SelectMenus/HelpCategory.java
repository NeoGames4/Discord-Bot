package Bot.SelectMenus;

import Bot.SelectMenuInterface;
import Bot.Commands.Help;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public class HelpCategory implements SelectMenuInterface {
	
	public static final String id = "help";

	@Override
	public String getName() {
		return id;
	}

	@Override
	public void execute(SelectMenuEvent e) {
		StringSelectInteractionEvent se = e.getAsStringSelect();
		if(se != null) {
			String category = se.getSelectedOptions().get(0).getValue();
			se.editMessageEmbeds(Help.getEmbedBuilder(se.getUser(), se.getMember(), category).build()).setActionRow(Help.getActionRow(category).getComponents()).queue();
		}
	}

}

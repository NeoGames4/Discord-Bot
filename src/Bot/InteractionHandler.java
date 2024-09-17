package Bot;

import Bot.CommandInterface.AutoComplete;
import Control.Console;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * This is where interactions are being handled.<p>
 * The bot only supports slash commands, buttons, modals, context commands, auto completion and select menu interactions.
 * @author Mika Thein
 * @version 1.0
 * @see Launcher#COMMANDS
 * @see Launcher#BUTTONS
 * @see Launcher#MODALS
 * @see Launcher#MESSAGE_COMMANDS
 * @see Launcher#USER_COMMANDS
 * @see Launcher#selectMenus
 */
public class InteractionHandler extends ListenerAdapter {
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
		for(CommandInterface c : Launcher.COMMANDS) {
			if(e.getName().equalsIgnoreCase(c.getInfo().name)) {
				c.execute(e);
				Console.log(e.getUser().getAsTag() + " executed /" + e.getName() + "!");
				break;
			}
		}
	}
	
	@Override
	public void onButtonInteraction(ButtonInteractionEvent e) {
		for(ButtonInterface b : Launcher.BUTTONS) {
			if(e.getButton().getId().split(":")[0].equalsIgnoreCase(b.getId())) {
				b.execute(e);
				Console.log(e.getUser().getAsTag() + " pressed " + e.getButton().getId() + "!");
			}
		}
	}
	
	@Override
	public void onModalInteraction(ModalInteractionEvent e) {
		for(ModalInterface m : Launcher.MODALS) {
			if(e.getModalId().split(":")[0].equalsIgnoreCase(m.getId())) {
				m.execute(e);
				Console.log(e.getUser().getAsTag() + " sent " + e.getModalId() + "!");
				break;
			}
		}
	}
	
	@Override
	public void onGenericCommandInteraction(GenericCommandInteractionEvent e) {
		if(e instanceof MessageContextInteractionEvent) {
			for(MessageCommandInterface c : Launcher.MESSAGE_COMMANDS) {
				if(c.getName().equalsIgnoreCase(e.getName())) {
					c.execute((MessageContextInteractionEvent) e);
					Console.log(e.getUser().getAsTag() + " used " + e.getName() + "!");
					break;
				}
			}
		} else if(e instanceof UserContextInteractionEvent) {
			for(UserCommandInterface c : Launcher.USER_COMMANDS) {
				if(c.getName().equalsIgnoreCase(e.getName())) {
					c.execute((UserContextInteractionEvent) e);
					Console.log(e.getUser().getAsTag() + " used " + e.getName() + "!");
					break;
				}
			}
		}
	}
	
	@Override
	public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent e) {
		outer:
		for(CommandInterface c : Launcher.COMMANDS) {
			if(c.getInfo().name.equals(e.getName())) {
				if(c.getInfo().autoComplete.length > 0) {
					for(AutoComplete a : c.getInfo().autoComplete) {
						if(e.getFocusedOption().getName().equals(a.getName())) {
							a.execute(e);
							break outer;
						}
					}
				} else break;
			}
		}
	}
	
	@Override
	public void onStringSelectInteraction(StringSelectInteractionEvent e) {
		onSelectMenuInteraction(new SelectMenuInterface.SelectMenuEvent(e), e.getUser());
	}
	
	@Override
	public void onEntitySelectInteraction(EntitySelectInteractionEvent e) {
		onSelectMenuInteraction(new SelectMenuInterface.SelectMenuEvent(e), e.getUser());
	}
	
	public void onSelectMenuInteraction(SelectMenuInterface.SelectMenuEvent e, User u) {
		for(SelectMenuInterface s : Launcher.selectMenus) {
			if(e.menuId.split(":")[0].equalsIgnoreCase(s.getName())) {
				s.execute(e);
				Console.log(u.getAsTag() + " interacted with " + s.getName() + "!");
				break;
			}
		}
	}

}

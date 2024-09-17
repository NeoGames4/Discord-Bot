package Bot;

import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

/**
 * A selection menu event interface.
 * @author Mika Thein
 * @version 1.0
 */
public interface SelectMenuInterface {
	
	/**
	 * @return the name (the first part of the select menu ID).
	 */
	public String getName();
	
	/**
	 * Whatever is supposed to happen when someone interacts with the select menu.
	 * @param e the select menu interaction event
	 */
	public void execute(SelectMenuEvent e);
	
	public static class SelectMenuEvent {
		
		private StringSelectInteractionEvent se = null;
		private EntitySelectInteractionEvent ee = null;
		
		public final String menuId;
		
		public SelectMenuEvent(StringSelectInteractionEvent e) {
			se = e;
			menuId = e.getSelectMenu().getId();
		}
		
		public SelectMenuEvent(EntitySelectInteractionEvent e) {
			ee = e;
			menuId = e.getSelectMenu().getId();
		}
		
		public StringSelectInteractionEvent getAsStringSelect() {
			return se;
		}
		
		public EntitySelectInteractionEvent getAsEntitySelect() {
			return ee;
		}
		
	}

}

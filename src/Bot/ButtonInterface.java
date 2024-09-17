package Bot;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * A button event interface.
 * @author Mika Thein
 * @version 1.0
 */
public interface ButtonInterface {

	/**
	 * @return the prefix of the button ID.
	 */
	public String getId();
	
	/**
	 * Whatever is supposed to happen when the button is pressed.
	 * @param e the button interaction event
	 */
	public void execute(ButtonInteractionEvent e);

}

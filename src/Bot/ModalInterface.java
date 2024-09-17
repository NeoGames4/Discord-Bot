package Bot;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

/**
 * A modal event interface.
 * @author Mika Thein
 * @version 1.0
 */
public interface ModalInterface {
	
	/**
	 * @return the prefix of the modal ID.
	 */
	public String getId();
	
	/**
	 * Whatever is supposed to happen when the modal has been posted.
	 * @param e the modal interaction event
	 */
	public void execute(ModalInteractionEvent e);

}

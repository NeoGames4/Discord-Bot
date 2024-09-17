package Bot;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.interactions.components.buttons.Button;

/**
 * A class for useful tools.
 * @author Mika Thein
 * @version 1.0
 * @see #disableAllButtons(List)
 * @see #enableAllButtons(List)
 */
public class Tools {
	
	public static List<Button> disableAllButtons(List<Button> buttons) {
		List<Button> b = new ArrayList<>();
		for(Button button : buttons) b.add(button.asDisabled());
		return b;
	}
	
	public static List<Button> enableAllButtons(List<Button> buttons) {
		List<Button> b = new ArrayList<>();
		for(Button button : buttons) b.add(button.asEnabled());
		return b;
	}

}

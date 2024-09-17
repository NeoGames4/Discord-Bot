package Bot;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

/**
 * A class for storing status information more easily and compact.
 * @author Mika Thein
 * @version 1.0
 */
public class BotStatus {
	
	public final OnlineStatus status;
	public final Activity activity;
	
	public BotStatus(OnlineStatus status, Activity activity) {
		this.status = status;
		this.activity = activity;
	}

}

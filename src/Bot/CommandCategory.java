package Bot;

import net.dv8tion.jda.api.entities.emoji.Emoji;

public enum CommandCategory {
	
	UTILITIES("Utilities", "Useful utilities.", Emoji.fromUnicode("🛠")),
	FUN("Fun", "Commands that are just for fun.", Emoji.fromUnicode("🤡")),
	BOT("Bot Control", "Commands for bot control.", Emoji.fromUnicode("🤖"));
	
	public final String title, description;
	public final Emoji emoji;
	
	private CommandCategory(String title, String description, Emoji emoji) {
		this.title = title;
		this.description = description;
		this.emoji = emoji;
	}
	
}

package Bot;

import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.LayoutComponent;
import net.dv8tion.jda.api.utils.FileUpload;

/**
 * A class to store message data in a single instance.
 * @author Mika Thein
 * @version 1.0
 */
public class MessageSet {
	
	public String message;
	public ArrayList<EmbedBuilder> builders = new ArrayList<>();
	public ArrayList<MessageEmbed> embeds = new ArrayList<>();
	public ArrayList<LayoutComponent> actionRows = new ArrayList<>();
	public ArrayList<FileUpload> files = new ArrayList<>();
	
	public MessageSet() {}
	
	public MessageSet(String message) {
		this.message = message;
	}
	
	public MessageSet(String message, EmbedBuilder... builder) {
		this.message = message;
		for(EmbedBuilder b : builder) builders.add(b);
	}
	
	public MessageSet(String message, MessageEmbed... embed) {
		this.message = message;
		for(MessageEmbed e : embed) embeds.add(e);
	}
	
	public MessageSet(String message, ItemComponent... component) {
		this.message = message;
		actionRows.add(ActionRow.of(component));
	}
	
	public MessageSet(EmbedBuilder builder, ItemComponent... component) {
		builders.add(builder);
		actionRows.add(ActionRow.of(component));
	}
	
	public MessageSet(EmbedBuilder builder, LayoutComponent... actionRow) {
		builders.add(builder);
		for(LayoutComponent c : actionRow) actionRows.add(c);
	}
	
	public MessageSet(MessageEmbed embed, ItemComponent... component) {
		embeds.add(embed);
		actionRows.add(ActionRow.of(component));
	}
	
	public MessageSet(MessageEmbed embed, LayoutComponent... actionRow) {
		embeds.add(embed);
		for(LayoutComponent c : actionRow) actionRows.add(c);
	}
	
	public MessageSet setMessage(String message) {
		this.message = message; return this;
	}
	
	public MessageSet setBuilders(EmbedBuilder... builder) {
		for(EmbedBuilder b : builder) builders.add(b);
		return this;
	}
	
	public MessageSet setEmbeds(MessageEmbed... embed) {
		for(MessageEmbed e : embed) embeds.add(e);
		return this;
	}
	
	public MessageSet setActionRows(LayoutComponent... actionRow) {
		for(LayoutComponent a : actionRow) actionRows.add(a);
		return this;
	}
	
	public MessageSet setComponents(ItemComponent... component) {
		actionRows.add(ActionRow.of(component));
		return this;
	}
	
	public MessageSet setFiles(FileUpload... file) {
		for(FileUpload f : file) files.add(f);
		return this;
	}

}

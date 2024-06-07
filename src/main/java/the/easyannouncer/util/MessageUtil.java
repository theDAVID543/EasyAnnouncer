package the.easyannouncer.util;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import the.easyannouncer.config.MessageConfigManager;
import the.easyannouncer.impl.Message;

public class MessageUtil{
	public static void parseConfigMessageAndSend(Player player, Message message){
		player.sendMessage(fullParseMessage(player, MessageConfigManager.getMessage(message)));
	}

	public static void parseAndSend(Player player, String message){
		player.sendMessage(fullParseMessage(player, message));
	}

	public static Component fullParseMessage(Player player, String message){
		message = parsePlaceholder(player, message);
		return parseMiniMessage(message);
	}

	public static Component parseMiniMessage(String message){
		return MiniMessage.miniMessage().deserialize(message);
	}

	public static String parsePlaceholder(Player player, String message){
		message = message.replaceAll("%prefix%", "<gradient:#fff454:#ffba66>[EasyAnnouncer]</gradient>");
		message = PlaceholderAPI.setPlaceholders(player, message);
		return message;
	}
}

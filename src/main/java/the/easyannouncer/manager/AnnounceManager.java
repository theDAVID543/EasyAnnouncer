package the.easyannouncer.manager;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import the.easyannouncer.config.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnounceManager{
	public static Map<String, Config> announceConfigs = new HashMap<>();

	public static void addAnnounceConfig(String name, Config config){
		announceConfigs.put(name, config);
	}

	public static Component getAnnounceMessage(Player player, String name){
		List<String> messageStringList = announceConfigs.get(name).getStringList("Message");
		Component component = Component.text().build();
		for(int i = 0; i < messageStringList.size(); i++){
			String message = messageStringList.get(i);
			message = PlaceholderAPI.setPlaceholders(player, message);
			component = component
					.append(MiniMessage.miniMessage().deserialize(message));
			if(i < messageStringList.size() - 1){
				component = component.appendNewline();
			}
		}
		return component;
	}
}

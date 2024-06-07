package the.easyannouncer.command;

import org.bukkit.entity.Player;

import java.util.Map;

public interface SubCommand{
	default String permission(){
		return "";
	}

	void execute(Player player, Map<String, String> parsedArgs);

	default boolean hasPermission(Player player, Map<String, String> parsedArgs){
		return player.hasPermission(permission());
	}

	default boolean hasPermission(Player player){
		return player.hasPermission(permission());
	}

	default boolean acceptArguments(Player player, Map<String, String> parsedArgs){
		return true;
	}
}

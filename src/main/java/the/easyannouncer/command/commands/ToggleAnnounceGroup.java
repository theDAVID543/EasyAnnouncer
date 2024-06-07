package the.easyannouncer.command.commands;

import org.bukkit.entity.Player;
import the.easyannouncer.command.SubCommand;
import the.easyannouncer.config.MessageConfigManager;
import the.easyannouncer.impl.AnnounceGroup;
import the.easyannouncer.impl.Message;
import the.easyannouncer.manager.AnnounceGroupManager;
import the.easyannouncer.util.MessageUtil;

import java.util.Map;

public class ToggleAnnounceGroup implements SubCommand{
	@Override
	public String permission(){
		return "easyannouncer.announcegroup.{announceGroup}.toggle";
	}

	@Override
	public boolean hasPermission(Player player, Map<String, String> parsedArgs){
		if(parsedArgs.containsKey("announceGroup")){
			String group = parsedArgs.get("announceGroup");
			return player.hasPermission("easyannouncer.announcegroup." + group + ".toggle");
		}
		return false;
	}

	@Override
	public boolean hasPermission(Player player){
		for(String announceGroupName : AnnounceGroupManager.announceGroups.keySet()){
			if(player.hasPermission("easyannouncer.announcegroup." + announceGroupName + ".toggle")){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean acceptArguments(Player player, Map<String, String> parsedArgs){
		if(parsedArgs.containsKey("announceGroup")){
			String group = parsedArgs.get("announceGroup");
			return AnnounceGroupManager.announceGroups.containsKey(group);
		}
		return false;
	}

	@Override
	public void execute(Player player, Map<String, String> parsedArgs){
		String announceGroupName = parsedArgs.get("announceGroup");
		AnnounceGroup announceGroup = AnnounceGroupManager.getAnnounceGroup(announceGroupName);
		String message = MessageConfigManager.getMessage(Message.PLAYER_TOGGLE_ANNOUNCE).replaceAll("%AnnounceGroupName%", announceGroupName);
		if(!parsedArgs.containsKey("boolean")){
			announceGroup.toggleReceiveMessage(player);
			MessageUtil.parseAndSend(player, message);
			return;
		}
		String receive = parsedArgs.get("boolean");
		if(receive.equalsIgnoreCase("true")){
			announceGroup.toggleReceiveMessage(player, true);
			MessageUtil.parseAndSend(player, message);
		}else if(receive.equalsIgnoreCase("false")){
			announceGroup.toggleReceiveMessage(player, false);
			MessageUtil.parseAndSend(player, message);
		}else{
			MessageUtil.parseConfigMessageAndSend(player, Message.PLAYER_TOGGLE_ANNOUNCE_WRONG_INPUT);
		}
	}
}

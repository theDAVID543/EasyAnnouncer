package the.easyannouncer.command.commands;

import org.bukkit.entity.Player;
import the.easyannouncer.command.SubCommand;
import the.easyannouncer.manager.AnnounceManager;

import java.util.Map;

public class PreviewAnnounce implements SubCommand{
	@Override
	public String permission(){
		return "easyannouncer.command.preview";
	}

	@Override
	public void execute(Player player, Map<String, String> parsedArgs){
		String announceName = parsedArgs.get("announce");
		player.sendMessage(AnnounceManager.getAnnounceMessage(player, announceName));
	}
}

package the.easyannouncer.command.commands;

import org.bukkit.entity.Player;
import the.easyannouncer.command.SubCommand;
import the.easyannouncer.config.ConfigManager;
import the.easyannouncer.config.MessageConfigManager;
import the.easyannouncer.impl.Message;
import the.easyannouncer.manager.PermissionManager;
import the.easyannouncer.util.MessageUtil;

import java.util.Map;

public class Reload implements SubCommand{
	@Override
	public String permission(){
		return "easyannouncer.command.reload";
	}

	@Override
	public void execute(Player player, Map<String, String> parsedArgs){
		ConfigManager.reload();
		PermissionManager.reloadPermissions();
		MessageConfigManager.reload();
		MessageUtil.parseConfigMessageAndSend(player, Message.RELOAD);
	}
}

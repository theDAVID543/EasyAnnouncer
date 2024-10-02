package the.easyannouncer.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import the.easyannouncer.config.MessageConfigManager;
import the.easyannouncer.impl.AnnounceGroup;
import the.easyannouncer.impl.Message;
import the.easyannouncer.manager.AnnounceGroupManager;

import static the.easyannouncer.Main.plugin;

public class PlaceholderApiHook extends PlaceholderExpansion{
	@Override
	public @NotNull String getIdentifier(){
		return "EasyAnnouncer";
	}

	@Override
	public @NotNull String getAuthor(){
		return String.join(", ", plugin.getDescription().getAuthors());
	}

	@Override
	public @NotNull String getVersion(){
		return plugin.getPluginMeta().getVersion();
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public String onRequest(OfflinePlayer player, @NotNull String params){
		String[] args = params.split("_");
		if(args[0].equals("receiving")){
			if(AnnounceGroupManager.announceGroups.containsKey(args[1])){
				AnnounceGroup announceGroup = AnnounceGroupManager.announceGroups.get(args[1]);
				if(Boolean.TRUE.equals(announceGroup.isReceiveMessage(player.getPlayer()))){
					return MessageConfigManager.getMessage(Message.PLACEHOLDER_TRUE);
				}else if(Boolean.FALSE.equals(announceGroup.isReceiveMessage(player.getPlayer()))){
					return MessageConfigManager.getMessage(Message.PLACEHOLDER_FALSE);
				}else{
					return MessageConfigManager.getMessage(Message.PLACEHOLDER_NULL);
				}
			}
		}

		return null;
	}
}

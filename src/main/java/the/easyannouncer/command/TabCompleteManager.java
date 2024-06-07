package the.easyannouncer.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import the.easyannouncer.manager.AnnounceGroupManager;
import the.easyannouncer.manager.AnnounceManager;

import java.util.*;

public class TabCompleteManager implements TabCompleter{
	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args){
		if(!(sender instanceof Player)){
			return null;
		}
		Player player = (Player) sender;
		return matchCommand(args, player);
	}

	private List<String> matchCommand(String[] args, Player player){
		List<String> matchList = new ArrayList<>();
		for(Map.Entry<String, SubCommand> entry : CommandManager.subCommands.entrySet()){
			if(!entry.getValue().hasPermission(player)){
				continue;
			}
			String[] commandParts = entry.getKey().split(" ");
			int needLength = commandParts.length;
			if(args.length > needLength){
				continue;
			}

			boolean addNeeded = true;
			for(int i = 0; i < args.length - 1; i++){
				boolean isHitArg = commandParts[i].startsWith("{") && commandParts[i].endsWith("}") || commandParts[i].startsWith("(") && commandParts[i].endsWith(")");
				if(!isHitArg && !Objects.equals(args[i], commandParts[i])){
					addNeeded = false;
					break;
				}
			}
			if(addNeeded){
				int i = args.length - 2;
				if(commandParts[i + 1].equals("{announceGroup}")){
					Set<String> announceGroupNames = AnnounceGroupManager.announceGroups.keySet();
					for(String announceGroupName : announceGroupNames){
						if(entry.getValue().hasPermission(player, new HashMap<>(){{
							put("announceGroup", announceGroupName);
						}})){
							matchList.add(announceGroupName);
						}
					}
					addNeeded = false;
				}else if(commandParts[i + 1].equals("{announce}")){
					Set<String> announceNames = AnnounceManager.announceConfigs.keySet();
					matchList.addAll(announceNames);
					addNeeded = false;
				} if(commandParts[i + 1].equals("(boolean)")){
					matchList.add("true");
					matchList.add("false");
					addNeeded = false;
				}
			}
			if(addNeeded){
				matchList.add(commandParts[args.length - 1]);
			}
		}
		return matchList;
	}
}

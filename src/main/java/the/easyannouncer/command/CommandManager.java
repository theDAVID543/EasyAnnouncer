package the.easyannouncer.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import the.easyannouncer.command.commands.PreviewAnnounce;
import the.easyannouncer.command.commands.Reload;
import the.easyannouncer.command.commands.ToggleAnnounceGroup;
import the.easyannouncer.impl.Message;
import the.easyannouncer.impl.Pair;
import the.easyannouncer.util.MessageUtil;

import java.util.HashMap;
import java.util.Map;

public class CommandManager implements CommandExecutor{
	public CommandManager(){

	}

	public static final Map<String, SubCommand> subCommands = new HashMap<>(){{
		put("reload", new Reload());
		put("toggle {announceGroup} (boolean)", new ToggleAnnounceGroup());
		put("preview {announce}", new PreviewAnnounce());
	}};

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args){
		if(!(sender instanceof Player)){
			return false;
		}
		Player player = (Player) sender;

		Pair<SubCommand, Map<String, String>> matchedData = matchCommand(args, player);
		if(matchedData != null){
			matchedData.getKey().execute(player, matchedData.getValue());
			return true;
		}else{
			MessageUtil.parseConfigMessageAndSend(player, Message.COMMAND_NOT_FOUND);
		}

		return false;
	}

	private Pair<SubCommand, Map<String, String>> matchCommand(String[] args, Player player){
		for(Map.Entry<String, SubCommand> entry : subCommands.entrySet()){
			String[] commandParts = entry.getKey().split(" ");
			int needLength = commandParts.length;
			int mexLength = commandParts.length;
			for(String commandPart : commandParts){
				if(commandPart.startsWith("(") && commandPart.endsWith(")")){
					needLength--;
				}
			}
			if(args.length < needLength || args.length > mexLength){
				continue;
			}

			Map<String, String> parsedArgs = new HashMap<>();
			boolean matches = true;
			for(int i = 0; i < commandParts.length; i++){
				String commandPart = commandParts[i];
				if(commandPart.startsWith("{") && commandPart.endsWith("}")){
					String paramName = commandPart.substring(1, commandPart.length() - 1);
					parsedArgs.put(paramName, args[i]);
				}else if(commandPart.startsWith("(") && commandPart.endsWith(")")){
					String paramName = commandPart.substring(1, commandPart.length() - 1);
					if(args.length > i){
						parsedArgs.put(paramName, args[i]);
					}
				}else if(!commandPart.equals(args[i])){
					matches = false;
					break;
				}
			}

			if(matches){
				if(!entry.getValue().acceptArguments(player, parsedArgs) || !entry.getValue().hasPermission(player, parsedArgs)){
					return null;
				}
				return new Pair<>(entry.getValue(), parsedArgs);
			}
		}
		return null;
	}
}

package the.easyannouncer.command.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import the.easyannouncer.command.SubCommand;

import java.util.Map;

public class About implements SubCommand{
	@Override
	public String permission(){
		return "easyannouncer.command.about";
	}

	@Override
	public void execute(Player player, Map<String, String> parsedArgs){
		StringBuilder sb = new StringBuilder();
		sb.append("    <st><gradient:#f6ff47:#ff432e>                                             </gradient></st>");
		sb.append("<newline>");
		sb.append("             <gradient:#fbffa8:#bab8ff>EasyAnnouncer</gradient>");
		sb.append("<newline>");
		sb.append("    <st><gradient:#ff432e:#f6ff47>                                             </gradient></st>");
		Component component = MiniMessage.miniMessage().deserialize(sb.toString());
		player.sendMessage(component);
	}
}

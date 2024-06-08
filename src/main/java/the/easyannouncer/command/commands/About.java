package the.easyannouncer.command.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import the.easyannouncer.command.SubCommand;

import java.util.Map;

import static the.easyannouncer.Main.plugin;

public class About implements SubCommand{
	@Override
	public String permission(){
		return "easyannouncer.command.about";
	}

	@Override
	public void execute(Player player, Map<String, String> parsedArgs){
		StringBuilder sb = new StringBuilder();
		sb.append("    <st><gradient:#a6a6ff:#ff87d7>                                             </gradient></st>");
		sb.append("<newline>");
		sb.append("                 <gradient:#fbffa8:#bab8ff>EasyAnnouncer</gradient>");
		sb.append("<newline>");
		sb.append("     <yellow>Version: 1.0.1</yellow>  <aqua>Auther: <hover:show_text:\"<blue>Discord: thedavid54</blue>\">theDAVID54</hover></aqua>");
		sb.append("<newline>");
		sb.append("       <gold><click:open_url:\"https://www.spigotmc.org/resources/easyannouncer.117167/\"><hover:show_text:\"Click me!\">SpigotMC Page</hover></click></gold> | <blue><click:open_url:\"https://discord.gg/mrQSKTqmZK\"><hover:show_text:\"Click me!\">Discord Server</hover></click></blue>");
		sb.append("<newline>");
		sb.append("    <st><gradient:#ff87d7:#a6a6ff>                                             </gradient></st>");
		String string = sb.toString();
		string = string.replaceAll("%version%", plugin.getPluginMeta().getVersion());
		Component component = MiniMessage.miniMessage().deserialize(string);
		player.sendMessage(component);
	}
}

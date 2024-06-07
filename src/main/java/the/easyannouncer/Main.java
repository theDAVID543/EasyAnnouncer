package the.easyannouncer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import the.easyannouncer.command.CommandManager;
import the.easyannouncer.command.TabCompleteManager;
import the.easyannouncer.config.ConfigManager;
import the.easyannouncer.config.MessageConfigManager;
import the.easyannouncer.hooks.PlaceholderApiHook;
import the.easyannouncer.manager.PermissionManager;

public final class Main extends JavaPlugin{
	public static Main plugin;
	public static JavaPlugin instance;
	public CommandManager commandManager;
	public TabCompleteManager tabCompleteManager;

	@Override
	public void onEnable(){
		// Plugin startup logic
		plugin = this;
		instance = this;
		ConfigManager.createDefaultConfig();
		commandManager = new CommandManager();
		PermissionManager.reloadPermissions();
		Bukkit.getPluginCommand("easyannouncer").setExecutor(commandManager);
		tabCompleteManager = new TabCompleteManager();
		Bukkit.getPluginCommand("easyannouncer").setTabCompleter(tabCompleteManager);
		MessageConfigManager.reload();
		if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
			new PlaceholderApiHook().register();
		}
	}

	@Override
	public void onDisable(){
		// Plugin shutdown logic
	}
}

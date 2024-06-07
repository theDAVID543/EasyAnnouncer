package the.easyannouncer.config;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import the.easyannouncer.impl.AnnounceGroup;
import the.easyannouncer.manager.AnnounceGroupManager;
import the.easyannouncer.manager.AnnounceManager;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager{
	public static Config mainConfig;

	public static void reload(){
		AnnounceGroupManager.announceGroups.forEach((k, announceGroup) -> {
			announceGroup.cancelAnnounceTask();
		});
		AnnounceManager.announceConfigs.clear();
		AnnounceGroupManager.announceGroups.clear();
		createDefaultConfig();
	}

	public static void createDefaultConfig(){
		List<String> commentList;
		mainConfig = new Config("config.yml");
		if(!mainConfig.hasKey("Announces")){
			mainConfig.setObject("Announces", new ArrayList<>(){{
				add("example");
			}});
			commentList = new ArrayList<>(){{
				add("All configured Announcements");
				add("You can simply add a Announcement name here, and the file would appear in \"Announces\" after \"/ea reload\"");
			}};
			mainConfig.setComments("Announces", commentList);
		}
		for(String announceConfig : mainConfig.getStringList("Announces")){
			AnnounceManager.addAnnounceConfig(announceConfig, setupAnnounceConfig(announceConfig));
		}
		if(!mainConfig.hasKey("AnnounceGroups")){
			setupAnnounceGroup("exampleGroup");
			commentList = new ArrayList<>(){{
				add("This is an example announce group");
				add("You can add your announce group here");
				add("Players need \"easyannouncer.announcegroup.%AnnounceGroupName%\" permission to receive messages from the group");
			}};
			mainConfig.setComments("AnnounceGroups.exampleGroup", commentList);
			commentList = new ArrayList<>(){{
				add("This means files in the \"Announces\" folder without \".yml\"");
				add("Announcements would send by order if \"RandomSort\" is false");
			}};
			mainConfig.setComments("AnnounceGroups.exampleGroup.Announces", commentList);
			commentList = new ArrayList<>(){{
				add("The Interval(in ticks) between all announcements in this group");
			}};
			mainConfig.setComments("AnnounceGroups.exampleGroup.Interval", commentList);
			commentList = new ArrayList<>(){{
				add("Whether the announcements in this group should be sorted randomly");
			}};
			mainConfig.setComments("AnnounceGroups.exampleGroup.RandomSort", commentList);
		}else{
			for(String announceGroupName : mainConfig.getKeys("AnnounceGroups")){
				setupAnnounceGroup(announceGroupName);
			}
		}
	}

	public static void setupAnnounceGroup(String announceGroupName){
		if(!mainConfig.hasKey("AnnounceGroups." + announceGroupName + ".Announces")){
			mainConfig.setObject("AnnounceGroups." + announceGroupName + ".Announces", new ArrayList<>(){{
				add("example");
			}});
		}
		if(!mainConfig.hasKey("AnnounceGroups." + announceGroupName + ".Interval")){
			mainConfig.setObject("AnnounceGroups." + announceGroupName + ".Interval", 200);
		}
		if(!mainConfig.hasKey("AnnounceGroups." + announceGroupName + ".RandomSort")){
			mainConfig.setObject("AnnounceGroups." + announceGroupName + ".RandomSort", true);
		}
		List<String> groupAnnounces = mainConfig.getStringList("AnnounceGroups." + announceGroupName + ".Announces");
		AnnounceGroup announceGroup = new AnnounceGroup(
				announceGroupName,
				groupAnnounces,
				mainConfig.getInteger("AnnounceGroups." + announceGroupName + ".Interval"),
				mainConfig.getBoolean("AnnounceGroups." + announceGroupName + ".RandomSort")
		);
		for(String groupAnnounceName : groupAnnounces){
			if(!AnnounceManager.announceConfigs.containsKey(groupAnnounceName)){
				AnnounceManager.addAnnounceConfig(groupAnnounceName, setupAnnounceConfig(groupAnnounceName));
				List<String> originAnnounces = mainConfig.getStringList("Announces");
				originAnnounces.add(groupAnnounceName);
				mainConfig.setObject("Announces", originAnnounces);
			}
		}
		AnnounceGroupManager.addAnnounceGroup(announceGroup);
		if(Bukkit.getPluginManager().getPermission("easyannouncer.announcegroup." + announceGroupName) == null){
			Bukkit.getPluginManager().addPermission(new Permission("easyannouncer.announcegroup." + announceGroupName));
		}
	}

	public static Config setupAnnounceConfig(String configName){
		Config config = new Config("Announces/" + configName + ".yml");
		if(!config.hasKey("Message")){
			config.setObject("Message", new ArrayList<>(){{
				add("    <st><gradient:#828fff:#ff70fd>                                             </gradient></st>");
				add("              <gradient:#ff737a:#ffce63>Announce Example</gradient>");
				add("    <color:#adb6ff>This is an example of announcement</color>");
				add("    <color:#adb6ff>This is an example of announcement</color>");
				add("    <color:#adb6ff>This is an example of announcement</color>");
				add("    <st><gradient:#ff70fd:#828fff>                                             </gradient></st>");
			}});
		}
		return config;
	}
}

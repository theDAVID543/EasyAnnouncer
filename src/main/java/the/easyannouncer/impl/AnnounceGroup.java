package the.easyannouncer.impl;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import the.easyannouncer.manager.AnnounceManager;
import the.easyannouncer.task.AnnounceTask;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AnnounceGroup{
	public AnnounceGroup(@NotNull String name, @NotNull List<String> announceNames, int interval, boolean randomSort){
		this.name = name;
		this.announceNames = announceNames;
		this.interval = interval;
		this.randomSort = randomSort;
		announceTask = new AnnounceTask(this);
		announceTask.run();
	}

	AnnounceTask announceTask;
	public String name;
	List<String> announceNames;
	public int interval;
	boolean randomSort;
	Integer announceNow = 0;
	HashMap<UUID, Boolean> playerToggleReceive = new HashMap<>();

	public void toggleReceiveMessage(Player player){
		playerToggleReceive.put(player.getUniqueId(), !playerToggleReceive.getOrDefault(player.getUniqueId(), true));
	}

	public void toggleReceiveMessage(Player player, boolean receive){playerToggleReceive.put(player.getUniqueId(), receive);
	}

	public Boolean isReceiveMessage(Player player){
		return player.hasPermission("easyannouncer.announcegroup." + name) && playerToggleReceive.getOrDefault(player.getUniqueId(), true);
	}

	public void cancelAnnounceTask(){
		announceTask.bukkitRunnable.cancel();
	}

	public void announce(){
		if(announceNow == 0){
			sort();
		}
		for(Player player : Bukkit.getOnlinePlayers()){
			if(isReceiveMessage(player)){
				player.sendMessage(AnnounceManager.getAnnounceMessage(player, announceNames.get(announceNow)));
			}
		}
		announceNow++;
		if(announceNow >= announceNames.size()){
			announceNow = 0;
		}
	}

	public void sort(){
		if(randomSort){
			Collections.shuffle(announceNames);
		}
	}

	public List<String> getAnnounceNames(){
		if(randomSort){
			Collections.shuffle(announceNames);
		}
		return announceNames;
	}
}

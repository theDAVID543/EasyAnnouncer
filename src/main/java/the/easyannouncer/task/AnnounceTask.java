package the.easyannouncer.task;

import org.bukkit.scheduler.BukkitRunnable;
import the.easyannouncer.Main;
import the.easyannouncer.impl.AnnounceGroup;

public class AnnounceTask{
	public AnnounceTask(AnnounceGroup announceGroup){
		this.announceGroup = announceGroup;
	}

	public int interval;
	public AnnounceGroup announceGroup;
	public BukkitRunnable bukkitRunnable;

	public void run(){
		interval = announceGroup.interval;
		bukkitRunnable = new BukkitRunnable(){
			@Override
			public void run(){
				announceGroup.announce();
			}
		};
		bukkitRunnable.runTaskTimerAsynchronously(Main.instance, interval, interval);
	}
}

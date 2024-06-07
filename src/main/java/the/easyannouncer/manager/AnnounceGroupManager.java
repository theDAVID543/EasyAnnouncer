package the.easyannouncer.manager;

import the.easyannouncer.impl.AnnounceGroup;

import java.util.HashMap;
import java.util.Map;

public class AnnounceGroupManager{
	public static Map<String, AnnounceGroup> announceGroups = new HashMap<>();

	public static void addAnnounceGroup(AnnounceGroup announceGroup){
		announceGroups.put(announceGroup.name, announceGroup);
	}

	public static AnnounceGroup getAnnounceGroup(String groupName){
		return announceGroups.get(groupName);
	}
}

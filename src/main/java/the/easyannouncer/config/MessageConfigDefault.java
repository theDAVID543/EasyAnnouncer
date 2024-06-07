package the.easyannouncer.config;

import the.easyannouncer.impl.Message;

import java.util.EnumMap;

public class MessageConfigDefault{
	public static final EnumMap<Message, String> defaultMessages = new EnumMap<>(Message.class){{
		put(Message.RELOAD, "    <st><gradient:#f6ff47:#ff432e>                                             </gradient></st><newline><newline>          <gradient:#fbffa8:#bab8ff>EasyAnnouncer Reloaded</gradient><newline><newline>    <st><gradient:#ff432e:#f6ff47>                                             </gradient></st>");
		put(Message.PLAYER_TOGGLE_ANNOUNCE, "%prefix% <color:#d1d3ff>Receiving %AnnounceGroupName% messages: <color:#fcffd4>%easyannouncer_receiving_%AnnounceGroupName%%</color></color>");
		put(Message.PLAYER_TOGGLE_ANNOUNCE_WRONG_INPUT, "%prefix% <red>Wrong Input</red>");
		put(Message.COMMAND_NOT_FOUND, "%prefix% <red>Command not found!</red>");
	}};
}

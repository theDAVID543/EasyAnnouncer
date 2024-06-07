package the.easyannouncer.config;

import the.easyannouncer.impl.Message;

import java.util.EnumMap;

public class MessageConfigManager{
	public MessageConfigManager(){

	}

	public static Config messageConfig;
	public static final EnumMap<Message, String> configMessages = new EnumMap<>(Message.class);

	public static void reload(){
		messageConfig = setupMessageConfig();
	}

	public static String getMessage(Message message){
		return configMessages.get(message);
	}

	public static Config setupMessageConfig(){
		Config config = new Config("Messages.yml");
		MessageConfigDefault.defaultMessages.forEach((k, v) -> {
			if(!config.hasKey(k.toString())){
				config.setObject(k.toString(), v);
			}
			configMessages.put(k, config.getString(k.toString()));
		});
		return config;
	}
}

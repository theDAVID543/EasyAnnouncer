package the.easyannouncer.util;

import static the.easyannouncer.Main.plugin;

public class DebugOutput{
	final static Boolean debug = true;

	public static void sendDebugOutput(String message){
		if(debug){
			plugin.getLogger().info(message);
		}
	}
}

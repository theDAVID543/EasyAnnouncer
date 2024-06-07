package the.easyannouncer.manager;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import the.easyannouncer.command.CommandManager;
import the.easyannouncer.util.DebugOutput;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PermissionManager{
	final static Set<Permission> permissions = new HashSet<>();

	public static void reloadPermissions(){
		permissions.forEach(permission -> Bukkit.getPluginManager().removePermission(permission));
		permissions.clear();
		CommandManager.subCommands.forEach((k, subCommand) -> {
			PermissionManager.parsePermission(subCommand.permission()).forEach(permissionName -> {
				DebugOutput.sendDebugOutput(permissionName);
				if(Bukkit.getPluginManager().getPermission(permissionName) == null){
					Permission permission = new Permission(permissionName);
					Bukkit.getPluginManager().addPermission(permission);
					permissions.add(permission);
				}
			});
		});
	}

	public static Set<String> parsePermission(String permission){
		Set<StringBuilder> parsedPermissions = new HashSet<>();
		String[] parts = permission.split("\\.");
		parsedPermissions.add(new StringBuilder());
		for(String part : parts){
			if(part.startsWith("{") && part.endsWith("}")){
				if(Objects.equals(part, "{announceGroup}")){
					Set<StringBuilder> oldParsedPermissions = new HashSet<>(parsedPermissions);
					parsedPermissions.clear();
					for(StringBuilder sb : oldParsedPermissions){
						AnnounceGroupManager.announceGroups.forEach((k, announceGroup) -> {
							StringBuilder newPart = new StringBuilder(sb).append(announceGroup.name).append(".");
							parsedPermissions.add(newPart);
						});
					}
				}
			}else{
				for(StringBuilder sb : parsedPermissions){
					sb.append(part).append(".");
				}
			}
		}
		Set<String> result = new HashSet<>();
		for(StringBuilder parsedPermission : parsedPermissions){
			parsedPermission.deleteCharAt(parsedPermission.length() - 1);
			result.add(parsedPermission.toString());
		}
		return result;
	}
}

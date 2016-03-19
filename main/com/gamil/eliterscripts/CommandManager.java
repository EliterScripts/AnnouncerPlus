package com.gamil.eliterscripts;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import com.gmail.eliterscripts.command.MainCommand;

public class CommandManager {
	public CommandManager(){
		
	}
	
	public void RegisterAll(){
		CommandSpec MainCMD = CommandSpec.builder()
				.description(Text.of(MainPluginFile.getPluginName() + " main command"))
				.permission(MainPluginFile.getNodePrefix())
				.executor(new MainCommand())
				.build();
		Sponge.getCommandManager().register(MainPluginFile.getContainer(), MainCMD, "announcer", "acc", "announcerplus",
				"announce", "announcer", "announcements", "broadcaster", "announcer+");
	}
}

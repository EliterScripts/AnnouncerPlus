package com.gmail.eliterscripts;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import com.gmail.eliterscripts.command.AddCommand;
import com.gmail.eliterscripts.command.CreditsCommand;
import com.gmail.eliterscripts.command.HelpCommand;
import com.gmail.eliterscripts.command.MainCommand;

public class CommandManager {
	public CommandManager(){
		
	}
	
	CommandSpec CreditsCMD = CommandSpec.builder()
			.permission(MainPluginInterface.getNodePrefix())
			.description(Text.of("Shows the Credits Info of " + MainPluginInterface.getPluginName()))
			.executor(new CreditsCommand())
			.build();
	CommandSpec HelpCMD = CommandSpec.builder()
			.permission(MainPluginInterface.getNodePrefix())
			.description(Text.of("Shows the Help of " + MainPluginInterface.getPluginName()))
			.executor(new HelpCommand())
			.build();
	CommandSpec AddCMD = CommandSpec.builder()
			.permission(MainPluginInterface.getNodePrefix() + "add")
			.description(Text.of("Adds a message to the broadcaster"))
			.executor(new AddCommand())
			.build();
			
	
	public void RegisterAll(){
		CommandSpec MainCMD = CommandSpec.builder()
				.description(Text.of(MainPluginFile.getPluginName() + " main command"))
				.permission(MainPluginFile.getNodePrefix())
				.executor(new MainCommand())
				.child(CreditsCMD, "credits", "contibutor", "contributors", "developer", "developers")
				.build();
		Sponge.getCommandManager().register(MainPluginFile.getContainer(), MainCMD, "announcer", "acc", "announcerplus",
				"announce", "announcer", "announcements", "broadcaster", "announcer+");
	}
}

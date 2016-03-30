package com.gmail.eliterscripts;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import com.gmail.eliterscripts.command.AddCommand;
import com.gmail.eliterscripts.command.CreditsCommand;
import com.gmail.eliterscripts.command.HelpCommand;
import com.gmail.eliterscripts.command.MainCommand;

public class CommandManager {
	public CommandManager(){
		
	}
	
	static CommandSpec CreditsCMD = CommandSpec.builder()
			.permission(MainPluginFile.getNodePrefix())
			.description(Text.of("Shows the Credits Info of " + MainPluginFile.getPluginName()))
			.executor(new CreditsCommand())
			.build();
	CommandSpec HelpCMD = CommandSpec.builder()
			.permission(MainPluginFile.getNodePrefix())
			.description(Text.of("Shows the Help of " + MainPluginFile.getPluginName()))
			.executor(new HelpCommand())
			.build();
	static CommandSpec AddCMD = CommandSpec.builder()
			.permission(MainPluginFile.getNodePrefix() + "add")
			.description(Text.of("Adds a message to the broadcaster"))
			.executor(new AddCommand())
			.arguments(
					GenericArguments.onlyOne(GenericArguments.string(Text.of("message"))),
					GenericArguments.remainingJoinedStrings(Text.of("message"))
					)
			.build();
			
	
	public static void RegisterAll(){
		CommandSpec MainCMD = CommandSpec.builder()
				.description(Text.of(MainPluginFile.getPluginName() + " main command"))
				.permission(MainPluginFile.getNodePrefix())
				.executor(new MainCommand())
				.child(CreditsCMD, "credits", "contibutor", "contributors", "developer", "developers")
				.child(AddCMD, "add", "make", "+")
				.build();
		Sponge.getCommandManager().register(MainPluginFile.getContainer(), MainCMD, "announcer", "acc", "announcerplus",
				"announce", "announcer", "announcements", "broadcaster", "announcer+");
	}
}

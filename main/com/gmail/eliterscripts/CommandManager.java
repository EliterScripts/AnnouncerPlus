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
			
	
	public static void registerAll(){
		
		CommandSpec CreditsCMD = CommandSpec.builder()
				.permission(MainPluginFile.nodePrefix)
				.description(Text.of("Shows the Credits Info of " + MainPluginFile.pluginName))
				.executor(new CreditsCommand())
				.build();
		CommandSpec HelpCMD = CommandSpec.builder()
				.permission(MainPluginFile.nodePrefix)
				.description(Text.of("Shows the Help of " + MainPluginFile.pluginName))
				.executor(new HelpCommand())
				.build();
		CommandSpec AddCMD = CommandSpec.builder()
				.permission(MainPluginFile.nodePrefix + "add")
				.description(Text.of("Adds a message to the broadcaster"))
				.executor(new AddCommand())
				.arguments(
						GenericArguments.onlyOne(GenericArguments.string(Text.of("message"))),
						GenericArguments.remainingJoinedStrings(Text.of("message"))
						)
				.build();
		
		CommandSpec MainCMD = CommandSpec.builder()
				.description(Text.of(MainPluginFile.pluginName + " main command"))
				.permission(MainPluginFile.nodePrefix)
				.executor(new MainCommand())
				.child(CreditsCMD, "credits", "contibutor", "contributors", "developer", "developers")
				.child(AddCMD, "add", "make", "+")
				.child(HelpCMD, "help", "?", "instructions")
				.build();
		Sponge.getCommandManager().register(MainPluginFile.instance().getContainer(), MainCMD, "announcerplus", "announcer", "acc");
		MainPluginFile.logger.info("main command has been made!");
	}
}

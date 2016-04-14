package com.gmail.eliterscripts;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import com.gmail.eliterscripts.command.AddCommand;
import com.gmail.eliterscripts.command.CreditsCommand;
import com.gmail.eliterscripts.command.DeleteCommand;
import com.gmail.eliterscripts.command.HelpCommand;
import com.gmail.eliterscripts.command.ListCommand;
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
				.permission(MainPluginFile.nodePrefix + ".add")
				.description(Text.of("Adds a message to the broadcaster"))
				.executor(new AddCommand())
				.arguments(
						GenericArguments.remainingJoinedStrings(Text.of("message"))
						)
				.build();
		CommandSpec ListCMD = CommandSpec.builder()
				.permission(MainPluginFile.nodePrefix + ".list")
				.description(Text.of("Lists messages to be broadcasted"))
				.executor(new ListCommand())
				.arguments(
						GenericArguments.optional(GenericArguments.integer(Text.of("page number")))
						)
				.build();
		CommandSpec DeleteCMD = CommandSpec.builder()
				.permission( MainPluginFile.nodePrefix + ".delete" )
				.description(Text.of("Removes a message in the broadcast list"))
				.executor(new DeleteCommand())
				.arguments(
						GenericArguments.integer( Text.of("message index number") )
						)
				.build();
		
		CommandSpec MainCMD = CommandSpec.builder()
				.description(Text.of(MainPluginFile.pluginName + " main command"))
				.permission(MainPluginFile.nodePrefix)
				.executor(new MainCommand())
				.child(CreditsCMD, "credits", "author", "authors")
				.child(AddCMD, "add", "+")
				.child(HelpCMD, "help", "?")
				.child(ListCMD, "list", "messages", "ls")
				.child(DeleteCMD, "delete", "del", "remove", "rm", "-")
				.build();
		
		Sponge.getCommandManager().register(MainPluginFile.instance().getContainer(), MainCMD, "announcerplus", "announcer", "acc");
		MainPluginFile.instance().logger.info("main command has been made!");
	}
}

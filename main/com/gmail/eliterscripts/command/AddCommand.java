package com.gmail.eliterscripts.command;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import com.gmail.eliterscripts.ConfigManager;

public class AddCommand implements CommandExecutor{

	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		Optional<String> addingMessage = args.<String>getOne( Text.of("message") );
		int indexNumber = ConfigManager.addMessage(addingMessage);
		src.sendMessage(Text.of("Your message was added to the list, indexed under '" + indexNumber + "'."));
		
		return null;
	}

}

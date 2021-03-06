package com.gmail.eliterscripts.command;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.eliterscripts.ConfigManager;
import com.gmail.eliterscripts.MainPluginFile;

public class AddCommand implements CommandExecutor{

	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		Optional<String> addingMessage = args.<String>getOne( Text.of("message") );
			Optional<Integer> indexNumber = ConfigManager.addMessage(addingMessage);
			if(indexNumber.isPresent() ){
				Integer indexedNumber = indexNumber.get();
				src.sendMessage( Text.of( TextColors.GREEN,
						"Your message was added to the list, indexed under \"" + 
						String.valueOf((Integer) indexedNumber ) + "\"."
					) );
				
				return CommandResult.success();
			}else{
				src.sendMessage( MainPluginFile.commandError(
						"Sorry, your message wasn't added to the list.",
					21) );
				return CommandResult.empty();
			}
	}

}

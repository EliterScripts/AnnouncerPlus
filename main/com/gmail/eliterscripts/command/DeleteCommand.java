package com.gmail.eliterscripts.command;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

import com.gmail.eliterscripts.ConfigManager;
import com.gmail.eliterscripts.MainPluginFile;

public class DeleteCommand implements CommandExecutor{

	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		Optional<Integer> PreArg = args.getOne( Text.of("message index number") );
		Integer arg = null;
		if( PreArg.isPresent() ){
			arg = PreArg.get();
		}else{
			src.sendMessage( MainPluginFile.commandError(
					"Could not delete message since the argument isn't correct.", 22
					) );
			return CommandResult.empty();
		}
		
		Optional<Integer> PreCode = ConfigManager.deleteMessage( Optional.of(arg) );
		Integer Code;
		if( PreCode.isPresent() ){
			Code = PreCode.get();
		}else{
			src.sendMessage( MainPluginFile.commandError(
					"Could not delete message after attempting to.", 23) );
			return CommandResult.empty();
		}
		
		if(Code == 0){
			src.sendMessage( Text.of("Successfully deleted message indexed previously indexed under \""
					+ arg + "\"."
					) );
			return CommandResult.success();
		}else{
			src.sendMessage( MainPluginFile.commandError(
					"Could not delete message after attempting to.", 24) );
			return CommandResult.empty();
		}
	}

}

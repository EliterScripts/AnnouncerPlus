package com.gmail.eliterscripts.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
public class HelpCommand implements CommandExecutor{

	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		src.sendMessage(Text.of(MainCommand.main, MainCommand.reload, MainCommand.credits, 
				MainCommand.delete, MainCommand.help, MainCommand.list, MainCommand.main));
		
		return CommandResult.success();
	}

}

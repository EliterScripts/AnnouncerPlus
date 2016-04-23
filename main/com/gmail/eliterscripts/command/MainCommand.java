package com.gmail.eliterscripts.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.eliterscripts.ConfigManager;

public class MainCommand implements CommandExecutor {

	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		src.sendMessage(Text.of("In Development right now."));
		/*Text messageInterval = Text.of(TextColors.RED, "[unavailable]");
		Text messageListLength = Text.of(TextColors.RED, "[unavailable]");
		Text message*/
		
		Text messageInterval;
		Text messageListLength;
		Text messageOrder;
		
		if(ConfigManager.messageInterval.isPresent()){
			messageInterval = Text.of( ConfigManager.messageInterval.get() );
		}else{
			messageInterval = Text.of(TextColors.RED, "unavailable" );
		}
		
		if(ConfigManager.messageListLength.isPresent()){
			messageListLength = Text.of( ConfigManager.messageListLength.get() );
		}else{
			messageListLength = Text.of(TextColors.RED, "unavailable" );
		}
		
		if(ConfigManager.messageOrder.isPresent()){
			messageOrder = Text.of( ConfigManager.messageOrder.get() );
		}else{
			messageOrder = Text.of(TextColors.RED, "unavailable" );
		}
		
		
		src.sendMessage( Text.of(TextColors.DARK_PURPLE, "interval:", TextColors.RESET, messageInterval)  );
		src.sendMessage( Text.of(TextColors.DARK_PURPLE, "list length;", TextColors.RESET, messageListLength)  );
		src.sendMessage( Text.of(TextColors.DARK_PURPLE, "message order:", TextColors.RESET, messageOrder) );
		
		return CommandResult.success();
	}
	
}

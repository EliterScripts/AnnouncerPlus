package com.gmail.eliterscripts.command;

import java.util.ArrayList;
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

public class ListCommand implements CommandExecutor{

	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		Optional<ArrayList<Text>> PreMessages = ConfigManager.getMessages();
		Optional<Integer> pageNum = args.<Integer>getOne( Text.of("page number") );
		Optional<Integer> preLength = ConfigManager.messageListLength;
		Integer length = null;
		Integer ind = null;
		ArrayList<Text> messages = null;
		
		if( preLength.isPresent() ){
			length = preLength.get();
		}else{
			MainPluginFile.warner("error attempting to get max message list length. Try reloading the configuration.", 16);
			length = 5;
		}
		
		if(PreMessages.isPresent()){
			src.sendMessage(
					Text.of(TextColors.AQUA, MainPluginFile.pluginName + " broadcasting list:")
					);
			messages = PreMessages.get();
			if(pageNum.isPresent() ){
				ind = pageNum.get();
				if(ind < 0){
					ind = ind * (-1);
				}else if(ind == 0){
					ind = 1;
				}
				if(ind < 0){
					ind = 1;
				}else if( (int) Math.ceil( messages.size()/ (double) length) < ind){
					ind = (int) Math.ceil(messages.size()/ (double) length);
				}
					
			}else{
				ind = 1;
			}
					
		}else{
				MainPluginFile.warner("error attempting to get messages. Try reloading the configuration.", 17);
				src.sendMessage( MainPluginFile.commandError("error getting the messages.", 25) );
				return CommandResult.empty();
		}
			for(Integer i = (length*(ind-1)) + 1; i <= (length*ind); i = i + 1 ){
				if( messages.size() < i ){
					break;
				}
				src.sendMessage( Text.of("[#" + i + "]" + "[")
						.concat( messages.get(i - 1) ).concat(
								Text.of("]")
								)
					);
			}
			src.sendMessage(Text.of("(end of page " + ind + "/" + ((int) Math.ceil(messages.size()/ (double) length)) + ")"));
			return CommandResult.success();
	}

}

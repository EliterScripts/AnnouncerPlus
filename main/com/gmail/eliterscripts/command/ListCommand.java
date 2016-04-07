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
		if(PreMessages.isPresent()){
			src.sendMessage(
					Text.of(TextColors.AQUA, MainPluginFile.pluginName + " broadcasting list:")
					);
			ArrayList<Text> messages = PreMessages.get();
			Integer ind;
			if(pageNum.isPresent() ){
				ind = pageNum.get();
				if(ind < 0){
					ind = ind * (-1);
				}else if(ind == 0){
					ind = 0;
				}
				Optional<Integer> preLength = ConfigManager.messageListLength;
				if(preLength.isPresent()){
					Integer length = preLength.get();
					if(ind <= 0){
						ind = 1;
					}else if(Math.ceil(messages.size()/length) > ind){
						length = (int) Math.ceil(messages.size()/length);
					}
				}
					
				}
			}
			for(Text message : messages){
				ind = ind + 1;
				src.sendMessage( Text.of("[#" +
						messages.indexOf(message) + 1 + "]" + "[").concat(message).concat(
								Text.of("]")
								)
						);
			}
			src.send
		}
		
		return null;
	}

}

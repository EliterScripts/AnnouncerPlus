package com.gmail.eliterscripts.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import com.gmail.eliterscripts.MainPluginFile;

public class HelpCommand implements CommandExecutor{

	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
			src.sendMessage(Text.builder(MainPluginFile.pluginName )
	                .color(TextColors.GREEN).style(TextStyles.BOLD)
	                .append(
	                        Text.builder(" developed by ").color(TextColors.GREEN).build()
	                        )
	                .append(
	                                Text.builder(MainPluginFile.author).style(TextStyles.BOLD).style(TextStyles.ITALIC).build()
	                )
	                .build()
	        );
		return null;
	}

}

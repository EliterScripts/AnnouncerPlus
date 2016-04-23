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
			src.sendMessage(Text.of(
					TextColors.AQUA, MainPluginFile.pluginName, TextColors.RESET,
					", ", TextColors.LIGHT_PURPLE, "developed by:")
					);
			src.sendMessage(Text.of(
					TextColors.GOLD, MainPluginFile.author)
					);
					
		return CommandResult.success();
	}

}

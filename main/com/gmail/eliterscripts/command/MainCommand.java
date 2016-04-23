package com.gmail.eliterscripts.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.eliterscripts.MainPluginFile;

public class MainCommand implements CommandExecutor {
	
	public static final Text add = Text.of(
			TextColors.GREEN, " add:", "\n",
			TextColors.YELLOW, "aliases:", TextColors.AQUA, "+",
				"\n",
			TextColors.YELLOW, " description:", TextColors.AQUA,
				"Adds a message to the list of messages to broadcast.",
				"\n",
			TextColors.YELLOW, " parameters:", TextColors.AQUA,
				"message", TextColors.BLUE, " anything, but not nothing",
				"\n",
			TextColors.YELLOW, " example:", TextColors.AQUA,
				"/announcer add thank you, &4" + MainPluginFile.author + " for " +
					"making &e" + MainPluginFile.pluginName + ".",
				"\n"
		);
	public static final Text delete = Text.of(
			TextColors.GREEN, "delete:", "\n",
			TextColors.YELLOW, " aliases:", TextColors.AQUA, "del, remove, rm, -",
				"\n",
			TextColors.YELLOW, " description:", TextColors.AQUA,
				"Removes a message from the list of message to broadcast.",
				"\n",
			TextColors.YELLOW, " parameters:", TextColors.AQUA,
				"index", TextColors.BLUE, " an integer of an existing message's index number",
				"\n",
			TextColors.YELLOW, " example:", TextColors.AQUA,
				"/announcer delete 1",
				"\n"
		);
	public static final Text reload = Text.of(
			TextColors.GREEN, "reload:", "\n",
			TextColors.YELLOW, " aliases:", TextColors.AQUA, "load, rl, resync, sync",
				"\n",
			TextColors.YELLOW, " description:", TextColors.AQUA,
				"Reloads the plugin's configuration file.",
				"\n",
			TextColors.YELLOW, " parameters:", TextColors.RED,
				" NONE",
				"\n",
			TextColors.YELLOW, " example:", TextColors.AQUA,
				"/announcer reload",
				"\n"
			);
	public static final Text list = Text.of(
			TextColors.GREEN, "list:", "\n",
			TextColors.YELLOW, " aliases:", TextColors.AQUA, "messages, ls",
				"\n",
			TextColors.YELLOW, " description:", TextColors.AQUA,
				"Lists the messages in the list of messages to broadcast.",
				"\n",
			TextColors.YELLOW, " parameters:", TextColors.AQUA,
				"[page number]", TextColors.BLUE, " an integer of the page you would like to view.",
				"\n",
			TextColors.YELLOW, " example:", TextColors.AQUA,
				"/announcer list 2",
				"\n"
		);
	public static final Text help = Text.of(
			TextColors.GREEN, "help:", "\n",
			TextColors.YELLOW, " aliases:", TextColors.AQUA, "?",
				"\n",
			TextColors.YELLOW, " description:", TextColors.AQUA,
				"Gets the help page for this plugin.",
				"\n",
			TextColors.YELLOW, " parameters:", TextColors.AQUA,
				"[command]", TextColors.BLUE, " any command in this plugin.",
				"\n",
			TextColors.YELLOW, " example:", TextColors.AQUA,
				"/announcer help delete",
				"\n"
		);
	public static final Text credits = Text.of(
			TextColors.GREEN, "credits:", "\n",
			TextColors.YELLOW, " aliases:", TextColors.RED, "NONE",
				"\n",
			TextColors.YELLOW, " description:", TextColors.AQUA,
				"Gets the credits of this plugin.",
				"\n",
			TextColors.YELLOW, " parameters:", TextColors.RED,
				" NONE",
				"\n",
			TextColors.YELLOW, " example:", TextColors.AQUA,
				"/announcer credits",
				"\n"
		);
	public static final Text main = Text.of(
			TextColors.GREEN, "announcerplus:", "\n",
			TextColors.YELLOW, " aliases:", TextColors.AQUA, "announcer, acc",
				"\n",
			TextColors.YELLOW, " description:", TextColors.AQUA,
				"Main plugin's parent command for everything.",
				"\n",
			TextColors.YELLOW, " parameters:", TextColors.AQUA,
				" [command]", TextColors.BLUE, " any command in this plugin.",
				"\n",
			TextColors.YELLOW, " example:", TextColors.AQUA,
				"/announcer add thank you, &4" + MainPluginFile.author + " for " +
					"making &e" + MainPluginFile.pluginName + ".",
				"\n"
		);

	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		src.sendMessage(Text.of("In Development right now."));
		
		src.sendMessage(Text.of(MainCommand.main, MainCommand.reload, MainCommand.credits, 
				MainCommand.delete, MainCommand.help, MainCommand.list, MainCommand.main));
		
		return CommandResult.success();
	}
	
}

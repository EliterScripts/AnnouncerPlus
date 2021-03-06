package com.gmail.eliterscripts;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.server.ClientPingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.*;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import com.google.inject.Inject;

import java.nio.file.Path;
import java.util.Optional;

import org.slf4j.Logger;


@Plugin(id = "announcerplus", name = "AnnouncerPlus", version = "A0.0.1")
public class MainPluginFile {
	
	@Inject
	public PluginContainer container;
	
	@Inject
	public Logger logger;
	
	@Inject
	@DefaultConfig(sharedRoot = false)
	private Path ConfigPath;
	
	public static String pluginName;
	public static String author;
	public static String website;
	public static String nodePrefix;
	public static String version;
	public static boolean shareRoot;
	
	private static MainPluginFile instance;

	public MainPluginFile(){
		MainPluginFile.pluginName = "Announcer Plus";
		MainPluginFile.version = "A0.0";
		MainPluginFile.author = "Eliter Scripts";
		MainPluginFile.website = "https://google.com";
		MainPluginFile.nodePrefix = "announcerplus";
		instance = this;
	}
	
	public static void warner(String warnMessage, int code){
		instance().logger.warn( warnMessage + " [code " + code + "]");
	}
	
	public static void debuger(String trivialMessage, int code){
		instance().logger.debug( trivialMessage + "[code " + code + "]");
	}
	
	public static void inform(String informationMessage, int code){
		instance().logger.info( informationMessage + "[code " + code + "]");
	}
	
	public static Text commandError(String errorMessage, int code){
		return Text.of( TextColors.RED, TextStyles.BOLD, "COMMAND ERROR: " ).concat( 
				Text.of(TextColors.YELLOW, errorMessage) ).concat(
						Text.of(TextColors.AQUA, " [code " + code + "]")
						);
	}
	
	
	@Listener
	public void onServerStart(GameStartedServerEvent event){
		logger.info(pluginName + " v" + version + " by EliterScripts loaded.");
		
		ConfigManager.startup();
		
		logger.info("proccessed configuration file.");
		
		CommandManager.registerAll();		
		
		logger.info("Commands loaded.");
		
		BroadcastClock.makeSchedule();
		
		logger.info("Broadcast Clock loaded.");

	}
	
	public static Object reloadConfig(){
		new ConfigManager();
		ConfigManager.Messages.clear();
		ConfigManager.startup();
		return null;
	}
	
	@Listener
	public void onJoin (ClientConnectionEvent.Join event) {
		
	}
	
	public static MainPluginFile instance(){
		return instance;
	}
	
	public PluginContainer getContainer(){
		return container;
	}
	
	public static Path getPath(){
		return instance().ConfigPath;
	}
	
	
	@Listener
	public void lie (ClientPingServerEvent event){
		event.getResponse().setDescription(Text.of("Hey, does it work now?"));
		Optional<ClientPingServerEvent.Response.Players> stuff = event.getResponse().getPlayers();
		
		if(stuff.isPresent()){
			stuff.get().setMax((Math.round(stuff.get().getOnline()/100)*100) + 100);
		}
	}
	
	public void onCommand(SendCommandEvent event){
		
	}
}
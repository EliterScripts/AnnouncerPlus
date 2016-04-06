package com.gmail.eliterscripts;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.server.ClientPingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.*;

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
		instance().logger.warn( pluginName + ": " + warnMessage + " [code " + code + "]");
	}
	
	
	@Listener
	public void onServerStart(GameStartedServerEvent event){
		logger.info(pluginName + " v" + version + " by EliterScripts loaded.");
		
		new ConfigManager();
		ConfigManager.startup();
		
		CommandManager.registerAll();
	}
	
	@Listener
	public void onJoin(ClientConnectionEvent.Join event) {
		
		Player person = event.getTargetEntity();
		
		String person_name = person.getName();
		
		event.getTargetEntity().sendMessage(
				Text.of("Welcome ", person_name, " to the server!")
		);
		
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
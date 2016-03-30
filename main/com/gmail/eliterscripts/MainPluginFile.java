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
	public static PluginContainer container;
	
	@Inject
	public static Plugin pl;
	
	@Inject
	public static Logger logger;
	
	@Inject
	@DefaultConfig(sharedRoot = false)
	private static Path defaultConfig;
	
	public static String pluginName;
	public static String author;
	public static String website;
	public static Path configPath;
	public static String nodePrefix;

	public MainPluginFile(){
		MainPluginFile.pluginName = "Announcer Plus";
		MainPluginFile.author = "Eliter Scripts";
		MainPluginFile.website = "https://google.com";
		MainPluginFile.configPath = defaultConfig;
		MainPluginFile.nodePrefix = "announcerplus";
	}
	
	public static void warner(String warnMessage, int code){
		logger.warn( pluginName + ": " + warnMessage + " [code " + code + "]");
	}
	
	private Optional<String> getVersion(){
		try{
			return container.getVersion();
		} catch (Exception e){
			logger.warn("Could not get plugin version. ", 4);
			return Optional.of("x.x.x");
		}
	}
	
	
	@Listener
	public void onServerStart(GameStartedServerEvent event){
		logger.info(pluginName + " v" + getVersion() + " by EliterScripts loaded.");
		
		CommandManager.RegisterAll();
	}
	
	@Listener
	public void onJoin(ClientConnectionEvent.Join event) {
		
		Player person = event.getTargetEntity();
		
		String person_name = person.getName();
		
		event.getTargetEntity().sendMessage(
				Text.of("Welcome ", person_name, " to the server!")
		);
		
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
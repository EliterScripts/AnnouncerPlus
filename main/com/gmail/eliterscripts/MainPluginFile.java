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


@Plugin(id = "announcer_plus", name = "AnnouncerPlus", version = "A0.0")
public class MainPluginFile {
	
	public MainPluginFile(){
		
	}
	
	@Inject
	private static PluginContainer container;
	
	public static PluginContainer getContainer(){
		return container;
	}
	
	public static String getPluginName(){
		return "Announcer Plus";
	}
	
	public static String getAuthor(){
		return "EliterScripts";
	}
	
	public static String getWebsite(){
		return "http://google.com";
	}
	
	public static String getNodePrefix(){
		return "announcerplus";
	}
	
	@Inject
	private Logger logger;
	
	@Inject
	@DefaultConfig(sharedRoot = false)
	private static Path defaultConfig;
	
	
	
	public static Path getConfigPath(){
		return defaultConfig;
		
	}
	
	public Logger getLogger(){
		return logger;
	}
	
	@Listener
	public void onServerStart(GameStartedServerEvent event){
		logger.info(container.getName() + " v" + container.getVersion() + " by EliterScripts loaded.");
		
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
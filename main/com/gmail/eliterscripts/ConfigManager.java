package com.gmail.eliterscripts;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.spongepowered.api.text.Text;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ConfigManager {
	
	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	
	private ArrayList<Object> Messages;
	
	private ConfigManager(){
		
		Path path = MainPluginFile.getConfigPath();
		this.loader =
		HoconConfigurationLoader.builder().setPath(path).build();
	}
	
	private void setValues(){
		ConfigurationNode rootNode;
		rootNode = this.loader.createEmptyNode(ConfigurationOptions.defaults());
		rootNode.getNode("messages").setValue(this.loader.createEmptyNode(ConfigurationOptions.defaults()));
		
		try {
			loader.load().getNode("messages").setComment("The message list to be broadcasted")
			.setValue(this.loader.createEmptyNode(ConfigurationOptions.defaults()));
		} catch (IOException e) {
			e.printStackTrace();
			MainPluginFile.getLogger().warn(MainPluginFile.getPluginName() + " returned an error. [code 0]");
		}
		try {
			ArrayList<? extends CommentedConfigurationNode> getNodes =
					(ArrayList<? extends CommentedConfigurationNode>) loader.load().getNode("messages").getChildrenList();
			for( CommentedConfigurationNode node : getNodes ){
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void addMessage(Object message){
		if(message.getClass().equals(String.class)){
			Messages.add(Text.of(message));
		}else
			if(message.getClass().equals(Text.class)){
				Messages.add(message);
			}
		else{
			MainPluginFile.getLogger().warn(MainPluginFile.getPluginName() + " returned an error. [code 1]");
		}
	}
	
	public void addConfig(Object type, Object obj) {
		if(type == "message" && obj != null ){
			addMessage(obj);
		}
	}
	
	
}

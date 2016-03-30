package com.gmail.eliterscripts;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import org.spongepowered.api.text.Text;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ConfigManager {
	
	private static ConfigurationLoader<CommentedConfigurationNode> loader;
	
	private static ArrayList<Text> Messages;
	
	public ConfigManager(){
		
		Path path = MainPluginFile.getConfigPath();
		loader =
		HoconConfigurationLoader.builder().setPath(path).build();
	}
	
	private void setValues(){
		ConfigurationNode rootNode;
		rootNode = loader.createEmptyNode(ConfigurationOptions.defaults());
		rootNode.getNode("messages").setValue(loader.createEmptyNode(ConfigurationOptions.defaults()));
		
		try {
			loader.load().getNode("messages").setComment("The message list to be broadcasted")
			.setValue(loader.createEmptyNode(ConfigurationOptions.defaults()));
		} catch (IOException e) {
			e.printStackTrace();
			MainPluginFile.getLogger().warn(MainPluginFile.getPluginName() + " returned an error.", 0);
		}
	}
	
	public void loadValues(){
		try {
			ArrayList<? extends CommentedConfigurationNode> getNodes =
					(ArrayList<? extends CommentedConfigurationNode>) loader.load().getNode("messages").getChildrenList();
			for( CommentedConfigurationNode node : getNodes ){
				addMessage( node.getValue() );
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MainPluginFile.warner("error attempting to get messages children from config.", 2);
		}
		
	}
	
	private static void addToConfig(Text message){
		try{
			loader.load().getNode("messages").getNode( Integer.toString( Messages.size() + 1) )
			.setValue(message)
			;
		} catch (IOException e){
			MainPluginFile.warner("error attempting to add message to config.", 3);
		}
	}
	
	public static Integer addMessage(Object message){
		if(message.getClass().equals(String.class)){
			Messages.add(Text.of(message));
			addToConfig(Text.of(message));
		}else
			if(message.getClass().equals(Text.class)){
				Messages.add((Text) message);
				addToConfig((Text) message);
			}
		else{
			MainPluginFile.warner("error attempting to add message.", 1);
		}
		return Messages.indexOf( message );
	}
	
	public static ArrayList<Text> getMessages(){
		return Messages;
	}
	
	
}

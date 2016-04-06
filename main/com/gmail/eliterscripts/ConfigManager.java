package com.gmail.eliterscripts;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import com.google.common.reflect.TypeToken;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ConfigManager {
	
	private static ConfigurationLoader<CommentedConfigurationNode> loader;
	
	private static ArrayList<Text> Messages = new ArrayList<Text>();
	
	private static final ConfigManager instance = new ConfigManager();
	private CommentedConfigurationNode root;
	
	public ConfigManager(){
		
		Path path = MainPluginFile.getPath();
		loader =
		HoconConfigurationLoader.builder().setPath(path).build();
	}
	
	public static ConfigManager instance(){
		return instance;
	}
	
	public static void startup(){
		instance();
		instance().load();
		instance().setValues();
		instance().save();
	}
	
	private void setValues(){
		if( instance().root.getNode("messages") == null ){
			try {
				root.getNode("messages").setComment("Messages that will be broadcasted.").setValue(
						TypeToken.of(Text.class), Text.of(MainPluginFile.pluginName)
						);
			} catch (ObjectMappingException e) {
				e.printStackTrace();
				MainPluginFile.warner("error attempting to generate default message in config.", 8);
			}
		}else{
			sort( root.getNode("messages") );
		}
	}
	
	private void sort(CommentedConfigurationNode node){
		for(CommentedConfigurationNode value : node.getChildrenList()){
			if( value.getNode("message").getValue().equals(String.class) ){
				Messages.add(TextSerializers.FORMATTING_CODE.deserialize( value.getNode("message").getString() ));
			}else if( value.getNode("message").getValue().equals(Text.class) ){
				Messages.add((Text) value.getNode("message").getValue(Text.class));
			}else{
				MainPluginFile.warner("error attempting to read a message in config.", 7);
			}
			
			
		}
	}
	
	private void load(){
		try{
			this.root = loader.load();
		} catch (IOException e){
			e.printStackTrace();
			MainPluginFile.warner("error attempting to load config.", 6);
		}
	}
	
	private void save(){
		try {
			loader.save(root);
		} catch (IOException e) {
			e.printStackTrace();
			MainPluginFile.warner("error attempting to save config.", 9);
		}
	}
	
	public static Integer addMessage(Text message){
			Messages.add( message );
			instance();
			instance().load();
			instance().root.getNode(
				String.valueOf( 
						Messages.indexOf( message )
					)
				).setValue(
						message
						);
			instance().save();
		return Messages.indexOf( message );
	}
	
	public static Integer addMessage(Optional<String> message){
		return addMessage( 
				(Text) TextSerializers.FORMATTING_CODE.deserialize( (Optional<String>) message ) 
				);
	}
	
	public static ArrayList<Text> getMessages(){
		return Messages;
	}
	
	
}

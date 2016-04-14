package com.gmail.eliterscripts;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;

import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ConfigManager {
	
	private static ConfigurationLoader<CommentedConfigurationNode> loader;
	
	public static ArrayList<Text> Messages = new ArrayList<Text>();
	
	public static Optional<Integer> messageListLength;
	
	private static final ConfigManager instance = new ConfigManager();
	private CommentedConfigurationNode root;
	
	private final String nodeName = MainPluginFile.instance().container.getId();
	
	@Inject
	@DefaultConfig(sharedRoot = false)
	private Path path;
	
	public ConfigManager(){
		
		Path path = MainPluginFile.getPath();
		loader =
		HoconConfigurationLoader.builder().setPath(path).build();
		messageListLength = Optional.empty();
	}
	
	public static ConfigManager instance(){
		return instance;
	}
	
	public static void startup(){
		instance().load();
		instance().setValues();
		instance().save();
	}
	
	private void setValues(){
		
		if( root.getNode(nodeName, "messages") == null ){
			MainPluginFile.warner("null, ", -2);
			try {
				root.getNode(nodeName, "messages")
				.setComment("Messages that will be broadcasted. Messages must be a positive integer.");
				
				root.getNode(nodeName, "messages", "example", "message").setValue(
						TypeToken.of(Text.class), Text.of( MainPluginFile.pluginName + " developed by"
							+ MainPluginFile.author	)
						);
			} catch (ObjectMappingException e) {
				e.printStackTrace();
				MainPluginFile.warner("error attempting to generate default message in config.", 8);
			}
		}else{
			MainPluginFile.warner("filled", -3);
			sort( root.getNode(nodeName, "messages") );
		}
		if( instance().root.getNode(nodeName, "settings") == null ){
			try {
				root.getNode(nodeName, "settings").setComment("Settings for " + MainPluginFile.pluginName);
				
				root.getNode("list length").setComment(
						"This sets how long the list should be sent to the user at once")
				.setValue(TypeToken.of(Integer.class), 5);
			} catch (ObjectMappingException e) {
				e.printStackTrace();
				MainPluginFile.warner("error attempting to generate default list length in config.", 12);
			}
		}else{
			messageListLength = Optional.of( root.getNode(nodeName, "settings", "list length").getInt() );
			if( messageListLength.isPresent() ){
				Integer length = messageListLength.get();
				if(length < 0 ){
					MainPluginFile.warner("fixing list length. It's value is below zero.", 13);
					length = Math.abs(length);
					messageListLength = Optional.of(length);
					try {
						root.getNode(nodeName, "settings", "list length").setComment(
								"This sets how long the list should be sent to the user at once")
						.setValue(TypeToken.of(Integer.class), length);
					} catch (ObjectMappingException e) {
						e.printStackTrace();
						MainPluginFile.warner("error attempting to fix list length node in config.", 14);
					}
				}else if(length == 0){
					MainPluginFile.warner("fixing list length. It's value is zero.", 15);
					length = 5;
					messageListLength = Optional.of(length);
					try {
						root.getNode(nodeName, "settings", "list length").setComment(
								"This sets how long the list should be sent to the user at once")
						.setValue(TypeToken.of(Integer.class), length);
					} catch (ObjectMappingException e) {
						e.printStackTrace();
						MainPluginFile.warner("error attempting to fix list length node in config.", 15);
					}
				}
			}
		}
	}
	
	private void sort(CommentedConfigurationNode node){
		Map<Object, ? extends CommentedConfigurationNode> chilMap = node.getChildrenMap();
		
		for( CommentedConfigurationNode value : chilMap.values() ){
			MainPluginFile.warner("hey! " + value.getPath(), -1);
			if( value.getNode("message").getValue().getClass() == String.class ){
				Messages.add(TextSerializers.FORMATTING_CODE.deserialize( value.getNode(nodeName, "message").getString() ));
			}else if( value.getNode("message").getValue().getClass() == Text.class ){
				Messages.add((Text) value.getNode(nodeName, "message").getValue(Text.class));
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
	
	public static Optional<Integer> addMessage(Text message){
			Messages.add( message );
			instance();
			instance().load();
			try {
				instance().root.getNode(
					String.valueOf( 
							Messages.indexOf( message ) + 1
						)
					).getNode("message").setValue(
							TypeToken.of(Text.class), message
							);
			} catch (ObjectMappingException e) {
				e.printStackTrace();
				MainPluginFile.warner("error attempting to set value of message in config.", 11);
				return Optional.empty();
			}
			instance().save();
		return Optional.of(Messages.indexOf( message ));
	}
	
	public static Optional<Integer> addMessage(Optional<String> message){
		if(message.isPresent()){
			String Message = message.get();
			return addMessage( 
					(Text) TextSerializers.FORMATTING_CODE.deserialize( Message ) 
					);
		}else{
			return Optional.empty();
		}
	}
	
	public static Optional<ArrayList<Text>> getMessages(){
		return Optional.of(Messages);
	}
	
	
}

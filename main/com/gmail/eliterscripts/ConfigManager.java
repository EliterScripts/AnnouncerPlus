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

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ConfigManager {
	
	private static ConfigurationLoader<CommentedConfigurationNode> loader;
	
	public static ArrayList<Text> Messages = new ArrayList<Text>();
	
	public static Optional<Integer> messageListLength;
	public static Optional<Integer> messageInterval;
	public static Optional<String> messageOrder;
	public static Optional<Text> messagePrefix;
	
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
		
		if( root.getNode(nodeName, "messages").isVirtual() ){
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
			sort( root.getNode(nodeName, "messages") );
		}
		if( root.getNode(nodeName, "settings").isVirtual() ){
			try {
				root.getNode(nodeName, "settings").setComment("Settings for " + MainPluginFile.pluginName);
				
				root.getNode(nodeName, "settings", "interval").setComment(
						"Number of seconds between each broadcast")
					.setValue( TypeToken.of( Integer.class ) , 60);
				messageInterval = Optional.of(60);
				
				root.getNode(nodeName, "settings", "order").setComment(
						"how the messages will be pulled, to be broadcasted.")
					.setValue(TypeToken.of( String.class ), "sequential");
				messageOrder = Optional.of("s");
				
				root.getNode(nodeName, "settings", "list length").setComment(
						"This sets how long the list should be sent to the user at once")
					.setValue(TypeToken.of(Integer.class), 5);
				messageListLength = Optional.of(5);
				
				root.getNode(nodeName, "settings", "prefix").setComment(
						"This sets the prefix, before each broadcasted message."
						)
					.setValue(TypeToken.of(Text.class),
						Text.of("[" + MainPluginFile.pluginName + "]")
							);
				
			} catch (ObjectMappingException e) {
				e.printStackTrace();
				MainPluginFile.warner("error attempting to generate defaults in the config.", 12);
			}
		}else{
			
			if( !( root.getNode(nodeName, "settings", "interval").isVirtual() ) ){
				Optional<Integer> messagesInterval = Optional.of( root.getNode( nodeName, "settings", "interval").getInt() );
				Integer msgInt = null;
			
				if( messagesInterval.isPresent() ){
					msgInt = messagesInterval.get();
					
					if(msgInt == 0){
						MainPluginFile.warner("fixing interval. Its value is zero.", 29);
						try{
							root.getNode(nodeName, "settings", "interval").setValue(TypeToken.of( Integer.class ), 60);
						}catch (ObjectMappingException e) {
							e.printStackTrace();
							MainPluginFile.warner("error attempting to fix interval node in config.", 30);
						}
						msgInt = 60;
					}
					if(msgInt < 0 ){
						MainPluginFile.warner("fixing interval. Its value is below zero.", 28);
						msgInt = (int) Math.abs(msgInt);
						try{
							root.getNode(nodeName, "settings", "interval").setValue(TypeToken.of( Integer.class ), msgInt );
						}catch (ObjectMappingException e){
							e.printStackTrace();
							MainPluginFile.warner("error attempting to fix interval ndoe in config.", 31);
						}
						
					}
					messageInterval = Optional.of( msgInt );
					
				}else{
					MainPluginFile.warner("setting interval value. It is not there.", 32);
					try{
						root.getNode(nodeName, "settings", "interval").setComment(
								"Number of seconds between each broadcast")
							.setValue( TypeToken.of( Integer.class ) , 60 );
					}catch (ObjectMappingException e){
						e.printStackTrace();
						MainPluginFile.warner("error attempting to set interval value in the config.", 33);
					}
					messageInterval = Optional.of(60);
				}
			}
			
			//messageOrder = ConfigSorter.order(root, nodeName);
			
			if( !( root.getNode(nodeName, "settings", "order").isVirtual() ) ){
			
				Optional<String> preOrderValue = Optional.of( root.getNode(nodeName, "settings", "order").getString() );
			
				if( preOrderValue.isPresent() ){
					try{
						root.getNode(nodeName, "settings", "order").setComment(
								"how the messages will be pulled, to be broadcasted.")
							.setValue(TypeToken.of(String.class),"sequential");
					} catch (ObjectMappingException e){
						e.printStackTrace();
						MainPluginFile.warner("error attempting to set settings value in the config.", 33);
					}
					messageOrder = Optional.of("s");
				}else{
					Optional<String> preConfValue = Optional.of( root.getNode(nodeName, "setting", "order").getString() );
					if(preConfValue.isPresent()){
						String postValue = preConfValue.get();
						
						String StrManip = new String( postValue );
						StrManip = StrManip.toLowerCase();
						if( StrManip != postValue ){
							try{
								root.getNode(nodeName, "settings", "order").setComment(
										"how the messages will be pulled, to be broadcasted.")
									.setValue(TypeToken.of(String.class), StrManip );
							} catch(ObjectMappingException e){
								MainPluginFile.debuger("trivial error lower casing \"order\".", 35);
							}
						}else if(( StrManip == "sequential") || (StrManip == "seq") || (StrManip == "s") ){
							postValue = "s";
						}else if((StrManip == "random") || (StrManip == "rand") || (StrManip == "r") ){
							postValue = "r";
						}else{
							MainPluginFile.warner("setting \"order\" to \"sequential\", by default.", 37);
							try{
								root.getNode(nodeName, "settings", "order").setComment(
										"how the messages will be pulled, to be broadcasted.")
									.setValue(TypeToken.of(String.class), "sequential" );
								postValue = "s";
							} catch(ObjectMappingException e){
								MainPluginFile.warner("trivial error changing node \"order\" in the config.", 38);
								postValue = "s";
							}
						}
					messageOrder = Optional.of( postValue );
					postValue = null;
					}
				}
			}
			
			if( !( root.getNode(nodeName, "settings", "list length").isVirtual() ) ){
			
				messageListLength = Optional.of( root.getNode(nodeName, "settings", "list length").getInt() );
				if( messageListLength.isPresent() ){
					
					
					Integer length = messageListLength.get();
					if(length < 0 ){
						MainPluginFile.warner("fixing list length. Its value is below zero.", 13);
						length = Math.abs(length);
						try {
							root.getNode(nodeName, "settings", "list length").setComment(
									"This sets how long the list should be sent to the user at once")
							.setValue(TypeToken.of(Integer.class), length);
						} catch (ObjectMappingException e) {
							e.printStackTrace();
							MainPluginFile.warner("error attempting to fix list length node in config.", 14);
						}
					}else if(length == 0){
						MainPluginFile.warner("fixing list length. Its value is zero.", 15);
						length = 5;
						try {
							root.getNode(nodeName, "settings", "list length").setComment(
									"This sets how long the list should be sent to the user at once")
							.setValue(TypeToken.of(Integer.class), length);
						} catch (ObjectMappingException e) {
							e.printStackTrace();
							MainPluginFile.warner("error attempting to fix list length node in config.", 15);
						}
					}
					messageListLength = Optional.of(length);
				}else{
					MainPluginFile.warner("setting list length value. It is not there.", 32);
					try{
						root.getNode(nodeName, "settings", "list length").setComment(
								"This sets how long the list should be sent to the user at once")
							.setValue( TypeToken.of( Integer.class ) , 5 );
					}catch (ObjectMappingException e){
						e.printStackTrace();
						MainPluginFile.warner("error attempting to set interval value in the config.", 33);
					}
					messageListLength = Optional.of(5);
				}
			}
			
			if( !( root.getNode(nodeName, "settings", "prefix").isVirtual() ) ){
				try {
					Optional<Text> preMessagePrefix = null;		
					Object prefixType = root.getNode(nodeName, "settings", "prefix").getValue().getClass();
					if(prefixType == String.class){
						preMessagePrefix = Optional.of(
								TextSerializers.FORMATTING_CODE.deserializeUnchecked(
										(String) root.getNode(nodeName, "settings", "prefix").getString()
										)
								);
					}else if(prefixType == Text.class){
						preMessagePrefix = Optional.of(
									(Text) root.getNode(nodeName, "settings", "prefix").getValue(
											TypeToken.of(Text.class)
										)
								);
					}else{
						MainPluginFile.debuger("could not load prefix variable in the config - it wan't"
								+ " an acceptable value. Setting to default.", 41);
					}
					
					if(preMessagePrefix.isPresent()){
						messagePrefix = preMessagePrefix;
					}else{
						root.getNode(nodeName, "settings", "prefix").setComment(
								"This sets the prefix, before each broadcasted message."
								)
							.setValue(TypeToken.of(Text.class),
								Text.of("[" + MainPluginFile.pluginName + "]")
									);
						messagePrefix = Optional.of((Text) Text.of("[" + MainPluginFile.pluginName + "]") );
					}
				} catch (ObjectMappingException e) {
					e.printStackTrace();
					MainPluginFile.debuger("could not get \"prefix\" from config file.", 42);
				}				
			}else{
				MainPluginFile.debuger("attempting to set \"prefix\" on the config file.", 44);
				try{
					root.getNode(nodeName, "settings", "prefix").setComment(
							"This sets the prefix, before each broadcasted message."
							)
						.setValue(TypeToken.of(Text.class),
							Text.of("[" + MainPluginFile.pluginName + "]")
								);
				} catch (ObjectMappingException e) {
					e.printStackTrace();
					MainPluginFile.debuger("could not set \"prefix\" on the config file.", 43);
				}
				messagePrefix = Optional.of((Text) Text.of("[" + MainPluginFile.pluginName + "]") );
			}
			
		}
	}
	
	private void sort(CommentedConfigurationNode node){
		Map<Object, ? extends CommentedConfigurationNode> chilMap = node.getChildrenMap();
		
		if( chilMap.size() == 0 ){
			MainPluginFile.inform("found 0 messages in configuration file.", 18);
		}else{
			MainPluginFile.inform("found " + chilMap.size() + " messages in configuration file.", 19);
			
			Integer indexer = 0;
			for( CommentedConfigurationNode value : chilMap.values() ){
				
				try{
					Integer.valueOf( (String) value.getKey() );
					
					if( value.getNode("message").getValue().getClass() == String.class ){
						Messages.add(TextSerializers.FORMATTING_CODE.deserialize( value.getNode("message").getString() ));
					}else if( value.getNode("message").getValue().getClass() == Text.class ){
						Messages.add((Text) value.getNode("message").getValue(Text.class));
					}else{
						MainPluginFile.warner("error attempting to read a message in config.", 7);
					}
					
					if(indexer != Integer.valueOf( (String) value.getKey()) ){
						Object nodeValue = root.getNode( nodeName, "messages", value.getKey() ).getValue();
						root.getNode( nodeName, "messages").removeChild( value.getKey() );
						root.getNode( nodeName, "messages", indexer).setValue(nodeValue);
					}
					
				}catch( NumberFormatException e ){
					String preVal = new String( (String) value.getKey() );
					String val;
					if( preVal.length() > 22 ){
						val = preVal.substring(0, 20) + "...";
					}else{
						val = preVal;
					}
					MainPluginFile.warner("message indexed under \"" + val + "\" is not an integer. This message will be " +
							"ignored.", 20);
				}
				indexer = indexer + 1;
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
	
	public static Optional<Integer> deleteMessage(Optional<Integer> deleteIndex){
		Integer delInd;
		if( deleteIndex.isPresent() ){
			delInd = deleteIndex.get();
			
			instance().load();
			instance().setValues();
			
			instance().root.getNode(
					instance().root.getNode(instance().nodeName, "messages")
						.removeChild( String.valueOf(delInd) )
						);
			
			instance().setValues();
			
			instance().save();
		
			Messages.remove( delInd - 1 );
			return Optional.of(0);
		}else{
			return Optional.empty();
		}
	}

	public static Optional<Integer> addMessage(Text message){
			instance();
			instance().load();
			try {
				instance().root.getNode(
						instance().nodeName, "messages", String.valueOf( 
							Messages.size() + 1
							)
					).getNode("message").setValue(
							TypeToken.of(Text.class), message
							);
				instance().save();
				Messages.add( message );
				return Optional.of( Messages.size() );
			} catch (ObjectMappingException e) {
				e.printStackTrace();
				MainPluginFile.warner("error attempting to set value of message in config.", 11);
				return Optional.empty();
			}
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

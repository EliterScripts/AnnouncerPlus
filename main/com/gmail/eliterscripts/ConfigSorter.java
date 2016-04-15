/*package com.gmail.eliterscripts;

import java.util.Optional;

import com.google.common.reflect.TypeToken;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ConfigSorter extends ConfigManager{
	public ConfigSorter(){
		
	}
	public static Optional<String> order(CommentedConfigurationNode root, String nodeName){
		//CommentedConfigurationNode orderConfValue = root.getNode(nodeName, "settings", "order");
		
		if( root.getNode(nodeName, "setting", "order") != null ){
			Optional<String> preConfValue;
			try {
				preConfValue = Optional.of( 
						root.getNode(nodeName, "settings", "order")
						.getValue( TypeToken.of( String.class ) ) );
			} catch (ObjectMappingException e) {
				e.printStackTrace();
				MainPluginFile.warner("unable to get \"order\", after node has been declared. (That's weird!)", 39);
				preConfValue = Optional.of("sequential");
			}
			String postValue = null;
			if( preConfValue.isPresent() )
				postValue = preConfValue.get();
				
				String StrManip = new String( postValue );
				StrManip = StrManip.toLowerCase();
				if( StrManip != postValue ){
					try{
						root.getNode(nodeName, "settings", "order").setComment(
								"how the messages will be pulled, to be broadcasted.")
							.setValue(TypeToken.of(String.class), StrManip );
						postValue = null;
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
						postValue = "r";
					} catch(ObjectMappingException e){
						MainPluginFile.debuger("trivial error changing node \"order\" in the config.", 38);
					}
				}
				return Optional.of( postValue );
				
				
		}else{
			try{
				root.getNode(nodeName, "settings", "order").setComment(
						"how the messages will be pulled, to be broadcasted.")
					.setValue(TypeToken.of(String.class),"sequential");
			} catch (ObjectMappingException e){
				e.printStackTrace();
				MainPluginFile.warner("error attempting to set settings value in the config.", 33);
			}
			return Optional.of( "s" );
		}
		
	}
}
*/
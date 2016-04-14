package com.gmail.eliterscripts;

import java.util.Optional;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ConfigSorter extends ConfigManager{
	public ConfigSorter(){
		
	}
	public static Optional<String> order(CommentedConfigurationNode root, String nodeName){
		Optional<String> OrderConfValue = root.getNode(nodeName, "settings", "order"); 
		String order = null;
		if( OrderConfValue.isPresent() ){
			order = OrderConfValue.get();
		}else{
			try{
				root.getNode(nodeName, "settings", "order").setComment(
						"how the messages will be pulled, to be broadcasted.")
					.setValue("sequential");
			} catch (ObjectMappingException e){
				e.printStackTrace();
				MainPluginFile.warner("error attempting to set settings value in the config.", 33);
			}
		}
	}
}

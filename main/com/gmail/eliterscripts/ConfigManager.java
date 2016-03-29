package com.gmail.eliterscripts;

import java.nio.file.Path;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ConfigManager {
	
	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	
	private ConfigManager(){
		
		Path path = MainPluginFile.getConfigPath();
		this.loader =
		HoconConfigurationLoader.builder().setPath(path).build();
	}
	private void setValues(){
		ConfigurationNode rootNode = loader.createEmptyNode(ConfigurationOptions.defaults());
		
	}
}

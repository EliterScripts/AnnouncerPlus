package com.gmail.eliterscripts;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.channel.MessageReceiver;

public class BroadcastChannel implements MessageChannel {
	
	private static BroadcastChannel instance;
	
	public BroadcastChannel(){
		instance = this;
	}

	public Optional<Text> transformMessage(Object sender, MessageReceiver recipient, Text original) {
		Text text = original;
		Optional<Text> prePrefix = ConfigManager.messagePrefix;
		if(prePrefix.isPresent()){
			text = Text.of(prePrefix.get(), text);
		}else{
			text = Text.of("[" + MainPluginFile.pluginName + "]", text);
		}
		return Optional.of(text);
	}

	public Collection<MessageReceiver> getMembers() {
		return Collections.emptyList();
	}

}

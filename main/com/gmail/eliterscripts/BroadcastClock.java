package com.gmail.eliterscripts;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;

public class BroadcastClock {
	
	private static Integer messageNumber;
	
	public BroadcastClock(){
		if(messageNumber == null){
			messageNumber = 0;
		}
	}
	
	public static void makeSchedule(){
		Scheduler scheduler = Sponge.getScheduler();
		Task.Builder taskBuilder = scheduler.createTaskBuilder();
		
		Optional<Integer> preInt = ConfigManager.messageInterval;
		Integer interval = null;
		if( preInt.isPresent() ){
			interval = preInt.get();
		}else{
			interval = 60;
		}
		
		taskBuilder.execute(new Runnable() {
			public void run() {
				Optional<String> pre = ConfigManager.messageOrder;
				String post = null;
				Optional<ArrayList<Text>> preMessageList = ConfigManager.getMessages();
				ArrayList<Text> postMessageList;
				if( preMessageList.isPresent() ){
					postMessageList = preMessageList.get();
					
					if( pre.isPresent() ){
						post = pre.get();
					}else{
						post = "s";
					}
					
					if(postMessageList.size() > 0){
						if(post == "s"){
							BroadcastChannel.instance().send( postMessageList.get( messageNumber ) );
						}else if(post == "r"){
							messageNumber = ThreadLocalRandom.current().nextInt(0, postMessageList.size() + 1);
							MessageChannel.TO_PLAYERS.send( postMessageList.get( messageNumber ) );
						}
					}
					
				}
			}
		}).interval(interval, TimeUnit.SECONDS).name(
				MainPluginFile.instance().container.getId() + "-broadcaster").submit(
						MainPluginFile.instance().container);
		
	}
}

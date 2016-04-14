package com.gmail.eliterscripts;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;

public class BroadcastManager {
	
	public void makeSchedule(){
		Scheduler scheduler = Sponge.getScheduler();
		Task.Builder taskBuilder = scheduler.createTaskBuilder();
		
		taskBuilder.execute(new Runnable() {
			public void run() {
				MessageChannel.TO_PLAYERS.send(  );
			}
		});
		
	}
}

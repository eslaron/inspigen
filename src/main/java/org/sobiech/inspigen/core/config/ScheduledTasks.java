package org.sobiech.inspigen.core.config;

import org.sobiech.inspigen.core.models.entities.Settings;
import org.sobiech.inspigen.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class ScheduledTasks {

	@Autowired
	Settings settings;
	
	@Autowired
	UserService userService;
	
	final private String deleteNotActivatedUsersCron = "0 0 0 */2 * ?";
	
    @Scheduled(cron=deleteNotActivatedUsersCron)
    public void deleteNotActivatedUsers() {
    	//userService.deleteNotActivatedUsers();
    }
}
package org.sobiech.inspigen.config;

import org.sobiech.inspigen.model.Settings;
import org.sobiech.inspigen.service.UserService;
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
    	userService.deleteNotActivatedUsers();
    }
}
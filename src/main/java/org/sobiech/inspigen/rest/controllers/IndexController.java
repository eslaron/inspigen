package org.sobiech.inspigen.rest.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/")
public class IndexController {

	// INDEX
	
	@RequestMapping
    public String getIndexPage() {
        return "index";
    }

	// Partiale - ADMIN
	
	@RequestMapping("/partials/admin/navbar")
    public String getAdminNavbarPartial() {
        return "partials/admin/navbar";
	}
	
	@RequestMapping("/partials/admin/sidebar")
    public String getAdminSidebarPartial() {
        return "partials/admin/sidebar";
	}
	
	@RequestMapping("/partials/admin/dashboard")
    public String getAdminDashboardPartial() {
        return "partials/admin/dashboard";
	}
	
	@RequestMapping("/partials/admin/users")
    public String getAdminUsersPartial() {
        return "partials/admin/users";
	}
	
	@RequestMapping("/partials/admin/editUser")
    public String getEditUserPartial() {
        return "partials/admin/editUser";
	}
	
	@RequestMapping("/partials/admin/addUser")
    public String getAddUserPartial() {
        return "partials/admin/addUser";
	}
		
	@RequestMapping("/partials/admin/groups")
    public String getGroupsPartialPage() {
        return "partials/admin/groups";
    }	
	
	@RequestMapping("/partials/admin/settings")
    public String getSettingsPartialPage() {
        return "partials/admin/settings";
    }	
		
	//Partiale - COMMON
	
	@RequestMapping("/partials/common/addEvent")
    public String getAddEventPartialPage() {
        return "partials/common/addEvent";
    }
	
	@RequestMapping("/partials/common/events")
    public String getEventsPartialPage() {
        return "partials/common/events";
    }
	
	@RequestMapping("/partials/common/addSchool")
    public String getAddSchoolPartialPage() {
        return "partials/common/addSchool";
    }
	
	@RequestMapping("/partials/common/schools")
    public String getSchoolsPartialPage() {
        return "partials/common/schools";
    }
	
	@RequestMapping("/partials/common/userDetails")
    public String getUserDetailsPartialPage() {
        return "partials/common/userDetails";
    }
	
	// Partiale - USER
	
	@RequestMapping("/partials/user/navbar")
    public String getUserNavbarPartial() {
        return "partials/user/navbar";
	}
	
	@RequestMapping("/partials/user/sidebar")
    public String getUserSidebarPartial() {
        return "partials/user/sidebar";
	}
	
	@RequestMapping("/partials/user/dashboard")
    public String getUserDashboardPartial() {
        return "partials/user/dashboard";
	}
	
	// Partiale - OTHER
	
	@RequestMapping("/partials/navbar")
	public String getNavbarPartial() {
	    return "partials/navbar";
	}
    
    @RequestMapping("/partials/signup")
    public String getSignupPartialPage() {
        return "partials/signup";
    }
    
	@RequestMapping("/partials/login")
    public String getLoginPartialPage() {
        return "partials/login";
    }

	@RequestMapping("/partials/resetPassword")
	public String getForgotPartialPage() {
		return "partials/resetPassword";
	}
	
	@RequestMapping("/partials/newPassword")
	public String getNewPartialPage() {
		return "partials/newPassword";
	}
}

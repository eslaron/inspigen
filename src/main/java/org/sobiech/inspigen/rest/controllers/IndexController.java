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
	
	@RequestMapping("/partials/common/persons")
    public String getPersonsPartialPage() {
        return "partials/common/persons";
    }
	
	@RequestMapping("/partials/common/addPerson")
    public String getAddPersonPartialPage() {
        return "partials/common/addPerson";
    }	
	
	@RequestMapping("/partials/common/editPerson")
    public String getEditPersonPartialPage() {
        return "partials/common/editPerson";
    }
	
	@RequestMapping("/partials/common/addresses")
    public String getAddressesPartialPage() {
        return "partials/common/addresses";
    }
	
	@RequestMapping("/partials/common/addAddress")
    public String getAddAddressPartialPage() {
        return "partials/common/addAddress";
    }	
	
	@RequestMapping("/partials/common/editAddress")
    public String getEditAddressPartialPage() {
        return "partials/common/editAddress";
    }
	
	@RequestMapping("/partials/common/events")
    public String getEventsPartialPage() {
        return "partials/common/events";
    }
	
	@RequestMapping("/partials/common/addEvent")
    public String getAddEventPartialPage() {
        return "partials/common/addEvent";
    }
	
	@RequestMapping("/partials/common/editEvent")
    public String getEditEventPartialPage() {
        return "partials/common/editEvent";
    }
	
	@RequestMapping("/partials/common/locations")
    public String getLocationsPartialPage() {
        return "partials/common/locations";
    }
	
	@RequestMapping("/partials/common/addLocation")
    public String getAddLocationPartialPage() {
        return "partials/common/addLocation";
    }
	
	@RequestMapping("/partials/common/editLocation")
    public String getEditLocationPartialPage() {
        return "partials/common/editLocation";
    }
	
	@RequestMapping("/partials/common/locationDetails")
    public String getLocationDetailsPartialPage() {
        return "partials/common/locationDetails";
    }
	
	@RequestMapping("/partials/common/eventDetails")
    public String getEventDetailsPartialPage() {
        return "partials/common/eventDetails";
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

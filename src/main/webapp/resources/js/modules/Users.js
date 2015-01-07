var Users = angular.module('inspigen.users', ['ui.router', 'restangular','ngTable'])

.config(['$stateProvider', function ($stateProvider) {
	
	$stateProvider
	
	.state('user', {
        url: '/user',
   	 abstract: true,
   	 template: '<div ui-view></div>', 
   	 data: {
            permissions: {
            	only: ['user','admin','moderator']
            }
        }
   })
   
   .state('user.admin', {
     title: 'Panel wolontariusza',
     abstract: false,
     url: '/admin',
     views: {
         'navbar@': {
       	  templateUrl: 'partials/admin/navbar.html' 
         },
         'sidebar@': {
       	  templateUrl: 'partials/admin/sidebar.html'
         },
         'content@': {
       	  templateUrl: 'partials/admin/dashboard.html',
       	  controller: 'UsersController'
         },
       },
       data: {
           permissions: {
             only: ['admin']
           }
       },
       resolve: {
    	   settings: ['Settings','Context', function(Settings, Context) {  
	      		return  Settings.loadSettingsFromJson()
	    	    .then(function(newlyLoadedSettings){
	    	    	Context.all.settings = Settings.getAllSettings();
	    	    });
    	   }],
    	   users: ['User','Context', function(User, Context) {  
	      		return  User.loadUsersFromJson()
	    	    .then(function(newlyLoadedUsers){
	    	    	Context.all.users = User.getAllUsers();
	    	    });
    	   }],   
    	   persons: ['Person','Context', function(Person, Context) {  
	      		return  Person.loadPersonsFromJson()
	    	    .then(function(newlyLoadedPersons){
	    	    	Context.all.persons = Person.getAllPersons();
	    	    });
    	   }],
    	   address: ['Address','Context', function(Address, Context) {  
	      		return  Address.loadAddressesFromJson()
	    	    .then(function(newlyLoadedAddresses){
	    	    	Context.all.addresses = Address.getAllAddresses();
	    	    });
    	   }],
    	   events: ['Event','Context', function(Event, Context) {  
	      		return  Event.loadEventsFromJson()
	    	    .then(function(newlyLoadedEvents){
	    	    	Context.all.events = Event.getAllEvents();
	    	    });
	   	   }],
	   	   participants: ['Participant','Context', function(Participant, Context) {  
		      		return  Participant.loadParticipantsFromJson()
		    	    .then(function(newlyLoadedParticipants){
		    	    	Context.all.participants = Participant.getAllParticipants();
		    	    });
	   	   }],
	   	   locations: ['Location','Context', function(Location, Context) {  
	      		return  Location.loadLocationsFromJson()
	    	    .then(function(newlyLoadedLocations){
	    	    	Context.all.locations = Location.getAllLocations();
	    	    });
	   	   }],
   	   }
   })
   
      .state('user.admin.users', {
     title: 'Panel wolontariusza',
     abstract: false,
     url: '/users',
     views: {
         'navbar@': {
       	  templateUrl: 'partials/admin/navbar.html' 
         },
         'sidebar@': {
       	  templateUrl: 'partials/admin/sidebar.html'
         },
         'content@': {
       	  templateUrl: 'partials/admin/users.html',
       	  controller: 'UsersController' 
         },
       },
       data: {
           permissions: {
             only: ['admin']
           }
       }
   })
   
   .state('user.admin.users.add', {
	     title: 'Panel wolontariusza',
	     abstract: false,
	     url: '/add',
	     views: {
	         'navbar@': {
	       	  templateUrl: 'partials/admin/navbar.html' 
	         },
	         'sidebar@': {
	       	  templateUrl: 'partials/admin/sidebar.html'
	         },
	         'content@': {
	       	  templateUrl: 'partials/admin/addUser.html',
	       	  controller: 'UsersController'      
	         }
	       },
	       data: {
	           permissions: {
	             only: ['admin']
	           }
	       }
	   })
   
     .state('user.admin.users.edit', {
     title: 'Panel wolontariusza',
     abstract: false,
     url: '/:id/edit',
     views: {
         'navbar@': {
       	  templateUrl: 'partials/admin/navbar.html' 
         },
         'sidebar@': {
       	  templateUrl: 'partials/admin/sidebar.html'
         },
         'content@': {
       	  templateUrl: 'partials/admin/editUser.html',
       	  controller: function($stateParams, $scope, User) {
              $scope.user.id = $stateParams.id;
              $scope.user = User.getUserById($stateParams.id);
              $scope.isCollapsed = true;
          } 
         },
       },
       data: {
           permissions: {
             only: ['admin']
           }
       }
   })
   
   .state('user.admin.users.details', {
     title: 'Panel wolontariusza',
     abstract: false,
     url: '/:id/details',
     views: {
         'navbar@': {
       	  templateUrl: 'partials/admin/navbar.html' 
         },
         'sidebar@': {
       	  templateUrl: 'partials/admin/sidebar.html'
         },
         'content@': {
       	  templateUrl: 'partials/common/userDetails.html',
       	  controller: function($stateParams, $scope, User, Person, Address) {
              $scope.user.id = $stateParams.id;
              $scope.user = User.getUserById($stateParams.id);
              $scope.persons = Person.getAllPersons();
              $scope.addresses = Address.getAllAddresses();
                     
              for(var i = $scope.persons.length - 1; i >= 0; i--) {
  			    if($scope.persons[i].user_id == $stateParams.id) {
  			       $scope.person = $scope.persons[i];
  			    }
  			}
              for(var i = $scope.addresses.length - 1; i >= 0; i--) {
  			    if($scope.addresses[i].user_id == $stateParams.id 
  			    		&& $scope.addresses[i].registeredAddress == true) {
  			       $scope.address = $scope.addresses[i];
  		   }
              }
              
              for(var i = $scope.addresses.length - 1; i >= 0; i--) {
    			    if($scope.addresses[i].user_id == $stateParams.id 
    			    		&& $scope.addresses[i].mailAddress == true
    			    			&& $scope.addresses[i].registeredAddress == false) {
    			       $scope.mailAddress = $scope.addresses[i];
    		   }
                }
              
              $scope.isCollapsed = true;
          } 
         },
       },
       data: {
           permissions: {
             only: ['admin']
           }
       }
   })
   
   .state('user.admin.groups', {
     title: 'Grupy użytkowników',
     abstract: false,
     url: '/groups',
     views: {
         'navbar@': {
       	  templateUrl: 'partials/admin/navbar.html' 
         },
         'sidebar@': {
       	  templateUrl: 'partials/admin/sidebar.html'
         },
         'content@': {
       	  templateUrl: 'partials/admin/groups.html',
       	  controller: 'UsersController'
         
         },
       },
       data: {
           permissions: {
             only: ['admin']
           }
       }
   })
   
      .state('user.admin.settings', {
     title: 'Ustawienia',
     abstract: false,
     url: '/settings',
     views: {
         'navbar@': {
       	  templateUrl: 'partials/admin/navbar.html' 
         },
         'sidebar@': {
       	  templateUrl: 'partials/admin/sidebar.html'
         },
         'content@': {
       	  templateUrl: 'partials/admin/settings.html',
       	  controller: 'UsersController' 
         },
       },
       data: {
           permissions: {
             only: ['admin']
           }
       }
   })
   
   .state('user.member', {
     title: 'Panel wolontariusza',
     abstract: false,
     url: '/dashboard',
     views: {
         'navbar@': {
       	  templateUrl: 'partials/user/navbar.html' 
         },
         'sidebar@': {
       	  templateUrl: 'partials/user/sidebar.html'
         },
         'content@': {
       	  templateUrl: 'partials/user/dashboard.html' 
         },
       },
       data: {
           permissions: {
             only: ['user']
           }
       }
   })
   .state('user.moderator', {
     title: 'Panel koordynatora',
     abstract: false,
     url: '/mod',
     views: {
         'navbar': {
       	  templateUrl: 'partials/mod/navbar.html' 
         },
         'sidebar': {
       	  templateUrl: 'partials/mod/sidebar.html'
         },
         'content': {
       	  templateUrl: 'partials/mod/dashboard.html' 
         },
       },
      data: {
           permissions: {
             only: ['moderator']
           }
      }
   })
   
}
           
]);

//KONTROLERY

Users.controller('UsersController', ['$rootScope', '$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 'Address','Settings', 'Context', 'Restangular',
                                     function($rootScope, $scope, $state, $stateParams, $filter, ngTableParams, User, Person, Address,Settings, Context, Restangular) {
	
  $scope.all = Context.all;
  $scope.active = Context.active;
  $scope.activate = Context.activate;
  
  $scope.isCollapsed = true;	
  
  var data = $scope.all.users;
  
  $scope.settings = $scope.all.settings[0];
   
  $scope.username = $scope.user.username;
  $scope.password = $scope.user.password;
  $scope.email = $scope.user.email;
  
  $scope.roles = [{key: "Wolontariusz"},
                  {key: "Koordynator"},
                  {key: "Administrator"}];
  
  $scope.selectedRole = {key: ""}
  $scope.selectedRole.key = $scope.user.role;
  
  $scope.optionalEnabled = false;
    
  if($scope.user.enabled == "Tak")
	  $scope.enabled = true;
  if($scope.user.enabled == "Nie")
	  $scope.enabled = false;
  if($scope.user.locked == "Tak")
	  $scope.locked = true;
  if($scope.user.locked == "Nie")
	  $scope.locked = false;
  
  $scope.editedUser = $scope.user.username;
  
  $scope.duplicateUsername = false;
  $scope.duplicateEmail = false;
  $scope.hideMessage = true;
  $scope.message = '';
  
  $scope.addToGroup = "";
  $scope.selectedUsers = [];
  
  $scope.loggedUser = User.getLoggedUserByUsername($rootScope.loggedUsername);

  $scope.changeSelection = function(user) {
	  
	  if(user.$selected == true)
		  $scope.selectedUsers.push(user.id);
	  	
	  if(user.$selected == false) {	  
		  for(var i = $scope.selectedUsers.length - 1; i >= 0; i--) {	
			    if($scope.selectedUsers[i] == user.id)
			    	$scope.selectedUsers.splice(i, 1);	  
		  }
	  }
  }
  
  $scope.assignToGroup = function() {
	  
	  var assignGroup = Restangular.one('users');
	  $scope.userBatch = {id:'', role:''};
	  
	  for(var i = 0; i < $scope.selectedUsers.length; i++) {	
		  assignGroup.id = $scope.selectedUsers[i];
		  assignGroup.role = $scope.addToGroup;
		  assignGroup.put().then(function(response){
			  
			  $scope.user.role = $scope.addToGroup;
			  $scope.tableParams.reload();
		  });
	  }
  }
  
  $scope.findDuplicate = function(type, value) { 
		  for(var i = data.length - 1; i >= 0; i--) {
			  if(type == "username") {
			    if(data[i].username.toLowerCase() == value) {
			    	$scope.duplicateUsername = true;
			    }
			  }
			  if(type == "email") {
				    if(data[i].email.toLowerCase() == value) {
				    	$scope.duplicateEmail = true;
				    }
			  }
			}
  }
  
  $scope.addUser = function(add) {
	  
	  	 $scope.hideMessage = true;
	  
	  	 var username = $scope.add.username.toLowerCase();
		 var email = $scope.add.email.toLowerCase();
 
		 $scope.duplicateUsername = false;
		 $scope.duplicateEmail = false; 
		 $scope.addUser_form.username.$setPristine();
		 $scope.addUser_form.email.$setPristine(); 

		 $scope.findDuplicate("username", username);
		 $scope.findDuplicate("email", email);
		 
		  if($scope.duplicateUsername == false 
				  	&& $scope.duplicateEmail == false) {
		 		  
			  var Add = Restangular.all('users');
			  	
			  if( $scope.optionalEnabled == true)
				  $scope.add.enabled = "Tak";
			  
			  if( $scope.optionalEnabled == false) 
				  $scope.add.enabled = "Nie";
			  		  
			  Add.post($scope.add).then(function(response){
				  $scope.duplicateUsername = false;
				  $scope.duplicateEmail = false;
				  $scope.messageStyle = "alert alert-success";
				  $scope.hideMessage = false;
				  $scope.message = "Użytkownik został dodany";			

				  return  User.loadUsersFromJson()
		    	    .then(function(newlyLoadedUsers){
		    	    	Context.all.users = User.getAllUsers();
		    	    });
			  }, function(error) {
				  $scope.error = error.data;
				  
					if($scope.error.description == "duplicateUser") {
						$scope.duplicateUsername = true;
						$scope.addUser_form.username.$setPristine();
					}
					if($scope.error.description  == "duplicateEmail") {
						$scope.duplicateEmail = true; 
						$scope.addUser_form.email.$setPristine();
					}
					if($scope.error.description  == "duplicateUser&Email") {
						$scope.duplicateUsername = true;
						$scope.duplicateEmail = true; 
						$scope.addUser_form.username.$setPristine();
						$scope.addUser_form.email.$setPristine();
					}
			  });
		  }
	  }
  
  $scope.editUser = function(user) {
	 
	 $scope.hideMessage = true; 
	  
	 var newUsername = $scope.username.toLowerCase(); 
	 var oldUsername = $scope.user.username.toLowerCase(); 
	 var newEmail = $scope.email.toLowerCase();
	 var oldEmail = $scope.user.email.toLowerCase();
	 
	 $scope.duplicateUsername = false;
	 $scope.duplicateEmail = false; 
	 $scope.editUser_form.username.$setPristine();
	 $scope.editUser_form.email.$setPristine(); 
 
	 if(newUsername != oldUsername) {
		  $scope.findDuplicate("username", newUsername);
	  }
	  
	  if(newEmail != oldEmail) {
		  $scope.findDuplicate("email", newEmail);
	  }
	   
	  if($scope.duplicateUsername == false 
			  	&& $scope.duplicateEmail == false) {
	  
		  var Edit = Restangular.one('users');
		  
		  if($scope.enabled == true)
			  $scope.user.enabled = "Tak";
		  if($scope.enabled == false)
			  $scope.user.enabled = "Nie";
		  if($scope.locked == true)
			  $scope.user.locked = "Tak";
		  if($scope.locked == false)
			  $scope.user.locked = "Nie";
			
		  Edit.id = $scope.user.id;
		  Edit.username = $scope.username;
		  Edit.password = $scope.password;
		  Edit.email = $scope.email;
		  Edit.role = $scope.selectedRole.key;
		  Edit.enabled = $scope.user.enabled;
		  Edit.locked = $scope.user.locked;
		  
		  Edit.put().then(function(response){
			  $scope.duplicateUsername = false;
			  $scope.duplicateEmail = false;
			  $scope.messageStyle = "alert alert-success";
			  $scope.hideMessage = false;
			  $scope.message = "Użytkownik został zmodyfikowany";	
			  
			  $scope.user.username = $scope.username; 
			  $scope.user.password = $scope.password; 
			  $scope.user.email =  $scope.email;
			  
			  $scope.user.role = $scope.selectedRole.key;
			  			  
		  });
	  }
  }

  $scope.deleteUser = function(id) {
	  
	  var Delete = Restangular.one('users');
	  
	  Delete.id = id;
	  
	  Delete.remove().then(function(response){
		  $scope.messageStyle = "alert alert-success";
		  $scope.hideMessage = false;
		  $scope.message = "Użytkownik został usunięty";
		  
		  for(var i = data.length - 1; i >= 0; i--) {
			    if(data[i].id == id) {
			       data.splice(i, 1);
			    }
			}
		  
		  $scope.tableParams.reload();
	  });
  }
  
  $scope.editSettings = function(settings) {
	  
	  var EditSettings = Restangular.one('settings');
	  
	  EditSettings.id = 0;
	  EditSettings.maxLoginAttempts = $scope.settings.maxLoginAttempts;
	  EditSettings.accountLockTime = $scope.settings.accountLockTime;
	  EditSettings.linkExpirationTime = $scope.settings.linkExpirationTime;
	  EditSettings.inactiveAccountsDeletionTime = $scope.settings.inactiveAccountsDeletionTime;
	
	  EditSettings.put().then(function(response){
		
		  $scope.messageStyle = "alert alert-success";
		  $scope.hideMessage = false;
		  $scope.message = "Zapisane";			  			  
	  });
  }
    
  $scope.tableParams = new ngTableParams({
        page: 1,            // show first page
        count: 10,          // count per page
        sorting: {
            id: 'asc'     // initial sorting
        }
    }, {
        total: data.length, // length of data
        getData: function($defer, params) {
        	        	
        	var orderedData = params.sorting() ? $filter('orderBy')(data, params.orderBy()) : data;
        	var filteredData = params.filter() ? $filter('filter')(orderedData, params.filter()) : orderedData; 
        	
        	params.total(filteredData.length); // set total for recalc pagination
        	
            $defer.resolve(filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count()));          
        }
   });
	
}]);
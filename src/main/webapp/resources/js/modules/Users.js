//Moduł obsługujący użytkowników
var Users = angular.module('inspigen.users', ['ui.router', 'restangular','ngTable'])

//Konfiguracja
.config(['$stateProvider', function ($stateProvider) {
	
	//Routing stanów(widoków)
	
	$stateProvider
	
	.state('app', {
   	 abstract: true,
   	 template: '<div ui-view></div>', 
   	 data: {
            permissions: {
            	only: ['admin','moderator','user']
            }
        },
        resolve: {
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
 	   	   locations: ['Location','Context', function(Location, Context) {  
 	      		return  Location.loadLocationsFromJson()
 	    	    .then(function(newlyLoadedLocations){
 	    	    	Context.all.locations = Location.getAllLocations();
 	    	    });
 	   	   }],
       }
   })
   
      
     .state('app.admin', {
        url: '/admin',
   	 	abstract: false,
   	 	views: {
         'navbar@': {
       	  templateUrl: 'partials/admin/navbar.html' 
         },
         'sidebar@': {
       	  templateUrl: 'partials/admin/sidebar.html'
         }
   	 	},
   	 	data: {
            permissions: {
            	only: ['admin']
            }
        },
     })
	
	
	.state('app.admin.users', {
        url: '/users',
   	 abstract: true,
   	 template: '<div ui-view></div>', 
   	 data: {
            permissions: {
            	only: ['admin','moderator','user']
            }
        },
   })
      
      .state('app.admin.users.list', {
     title: 'Użytkownicy',
     abstract: false,
     url: '/list',
     views: {
         'content@': {
       	  templateUrl: 'partials/common/users.html',
       	  controller: 'UsersController' 
         },
       },
       data: {
           permissions: {
        	  only: ['admin','moderator']
           }
       }
   })
   
   .state('app.admin.users.add', {
	     title: 'Dodaj użytkownika',
	     abstract: false,
	     url: '/add',
	     views: {
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
   
     .state('app.admin.users.edit', {
     title: 'Edytuj użytkownika',
     abstract: false,
     url: '/:id/edit',
     views: {
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
   
   .state('app.admin.users.details', {
     title: 'Profil użytkownika',
     abstract: false,
     url: '/:id/details',
     views: {
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
   
   .state('app.admin.users.groups', {
     title: 'Grupy użytkowników',
     abstract: false,
     url: '/groups',
     views: {
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
   
      .state('app.admin.dashboard', {
     title: 'Panel administratora',
     abstract: false,
     url: '/dashboard',
     views: {
         'content@': {
       	  templateUrl: 'partials/admin/dashboard.html',
         },
       },
       data: {
           permissions: {
             only: ['admin']
           }
       }
    })
    
     .state('app.member', {
        url: '/member',
   	 	abstract: false,
   	 	views: {
         'navbar@': {
       	  templateUrl: 'partials/user/navbar.html' 
         },
         'sidebar@': {
       	  templateUrl: 'partials/user/sidebar.html'
         }
   	 	},
   	 	data: {
            permissions: {
            	only: ['user']
            }
        },
     })
   
   .state('app.member.dashboard', {
     title: 'Panel wolontariusza',
     abstract: false,
     url: '/dashboard',
     views: {
         'content@': {
       	  templateUrl: 'partials/user/dashboard.html', 
       	  controller: 'UsersController' 
         },
       },
       data: {
           permissions: {
             only: ['user']
           }
       }
   })
   
     .state('app.member.editUser', {
     title: 'Edytuj użytkownika',
     abstract: false,
     url: '/users/:id/edit',
     views: {
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
        	   only: ['user']
           }
       }
   })
   
   .state('app.member.userDetails', {
    title: 'Użytkownicy',
    abstract: false,
    url: '/users/:id/details',
    views: {
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
       	  only: ['user']
          }
      }
  })
   
   	.state('app.moderator', {
        url: '/mod',
   	 abstract: false,
     views: {
         'navbar@': {
       	  templateUrl: 'partials/mod/navbar.html' 
         },
         'sidebar@': {
       	  templateUrl: 'partials/mod/sidebar.html'
         }
       },
   	 data: {
            permissions: {
            	only: ['moderator']
            }
        },
     })
   
     .state('app.moderator.dashboard', {
     title: 'Panel koordynatora',
     abstract: false,
     url: '/dashboard',
     views: {
         'content@': {
       	  templateUrl: 'partials/mod/dashboard.html', 
       	  controller: 'UsersController' 
         },
       },
       data: {
           permissions: {
             only: ['moderator']
           }
       }
   }) 
     
    .state('app.moderator.userList', {
    title: 'Użytkownicy',
    abstract: false,
    url: '/users/list',
    views: {
        'content@': {
      	  templateUrl: 'partials/common/users.html',
      	  controller: 'UsersController' 
        },
      },
      data: {
          permissions: {
       	  only: ['moderator']
          }
      }
  })
   
  .state('app.moderator.editUser', {
     title: 'Edytuj użytkownika',
     abstract: false,
     url: '/users/:id/edit',
     views: {
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
        	   only: ['moderator']
           }
       }
   })
  
  .state('app.moderator.userDetails', {
    title: 'Użytkownicy',
    abstract: false,
    url: '/users/:id/details',
    views: {
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
       	  only: ['moderator']
          }
      }
  })
}

           
]);

//KONTROLERY

//Kontroler użytkowników
Users.controller('UsersController', ['$rootScope', '$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 'Address', 'Context', 'Restangular',
                                     function($rootScope, $scope, $state, $stateParams, $filter, ngTableParams, User, Person, Address, Context, Restangular) {
	
  $scope.all = Context.all;
  $scope.active = Context.active;
  $scope.activate = Context.activate;
  
  $scope.isCollapsed = true;	
  
  //Zmienna globalna zawierająca listę wszystkich użytkowników
  var data = $scope.all.users;

  $scope.username = $scope.user.username;
  $scope.password = $scope.user.password;
  $scope.email = $scope.user.email;
  
  //Role użytkowników
  $scope.roles = [{key: "ROLE_USER"},
                  {key: "ROLE_MOD"},
                  {key: "ROLE_ADMIN"}];
  
  $scope.selectedRole = {key: ""}
  $scope.selectedRole.key = $scope.user.role;
  
  $scope.optionalEnabled = false;
  
  //Translacja statusów użytkownika
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
  
  //Pobranie informacji o zalogowanym użytkowniku
  $scope.loggedUser = User.getLoggedUserByUsername($rootScope.loggedUsername);

  $scope.loggedUserId = 0;

  //Funkcja sterująca wybieraniem wielu użytkowników w sekcji "Grupy"
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
  
  //Funkcja przypisująca do grupy użytkowników
  $scope.assignToGroup = function() {
	  
	  var assignGroup = Restangular.one('users');
	  $scope.userBatch = {id:'', role:''};
	  
	  for(var i = 0; i < $scope.selectedUsers.length; i++) {	
		  assignGroup.id = $scope.selectedUsers[i];
		  assignGroup.role = $scope.addToGroup;
		  assignGroup.enabled = true;
		  assignGroup.put().then(function(response){
			  return  User.loadUsersFromJson()
	    	    .then(function(newlyLoadedUsers){
	    	    	Context.all.users = User.getAllUsers();
	    	    	$scope.tableParams.reload();
	    	    });			  
		  });
	  }
  }
  
  //Funkcja szukająca duplikatów
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
  
  //Funkcja dodajaca nowego użytkownika
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
			  	
			  /*if( $scope.optionalEnabled == true)
				  $scope.add.enabled = "Tak";
			  
			  if( $scope.optionalEnabled == false) 
				  $scope.add.enabled = "Nie";*/
			  $scope.add.enabled = $scope.optionalEnabled;

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
				  console.log("ERROR: ",$scope.error);
				  
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
  
  //Funkcja aktualizująca użytkownika
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

  //Funkcja usuwająca użytkownika
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
    
  //Potwierdzenie usunięcia użytkownika
  
  $scope.cid = 0;
  
	$scope.getConfirmDeleteId = function(id) {

		$scope.cid = id;
	}
  
	//Funkcja obsługująca tabelę z użytkownikami
	  $scope.tableParams = new ngTableParams({
	        page: 1,            
	        count: 10,          
	        sorting: {
	            id: 'asc'     
	        }
	    }, {
	        total: data.length, // length of data
	        getData: function($defer, params) {
	        		        	        	
	        	var orderedData = params.sorting() ? $filter('orderBy')(data, params.orderBy()) : data;
	        	var filteredData = params.filter() ? $filter('filter')(orderedData, params.filter()) : orderedData; 
	        	
	        	params.total(filteredData.length); 
	        	
	            $defer.resolve(filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count()));          
	        }
	   });	
}]);
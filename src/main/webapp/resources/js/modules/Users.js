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
    	   users: ['User','Context', function(User, Context) {  
	      		return  User.loadUsersFromJson()
	    	    .then(function(newlyLoadedUsers){
	    	    	Context.all.users = User.getAllUsers();
	    	    });
    	   }]
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

Users.controller('UsersController', ['$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Context', 'Restangular',
                                     function($scope, $state, $stateParams, $filter, ngTableParams, User, Context, Restangular) {
	
  $scope.all = Context.all;
  $scope.active = Context.active;
  $scope.activate = Context.activate;
  
  var data = $scope.all.users;
   
  $scope.username = $scope.user.username;
  $scope.password = $scope.user.password;
  $scope.email = $scope.user.email;
  
  $scope.roles = [{key: "Wolontariusz", value:"ROLE_USER"},
                  {key: "Koordynator", value:"ROLE_MOD"},
                  {key: "Administrator", value:"ROLE_ADMIN"}];
  
  $scope.selectedRole = {key: "", value:""}
  $scope.selectedRole.value = $scope.user.role;
  
  $scope.enabled = $scope.user.enabled;
  $scope.locked = $scope.user.locked;
  
  $scope.editedUser = $scope.user.username;
  $scope.hideMessage = true;
  $scope.message = '';
  $scope.duplicateUsername = false;
  $scope.duplicateEmail = false;
  
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
			  		  
			  Add.post($scope.add).then(function(response){
				  $scope.duplicateUsername = false;
				  $scope.duplicateEmail = false;
				  $scope.messageStyle = "alert alert-success";
				  $scope.hideMessage = false;
				  $scope.message = "Użytkownik został dodany";			
				  //data.push($scope.add);
				  return  User.loadUsersFromJson()
		    	    .then(function(newlyLoadedUsers){
		    	    	Context.all.users = User.getAllUsers();
		    	    });
			  });
		  }
	  }
  
  $scope.editUser = function(user) {
	 
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
			
		  Edit.id = $scope.user.id;
		  Edit.username = $scope.username;
		  Edit.password = $scope.password;
		  Edit.email = $scope.email;
		  Edit.role = $scope.selectedRole.value;
		  Edit.enabled = $scope.enabled;
		  Edit.locked = $scope.locked;
		  
		  Edit.put().then(function(response){
			  $scope.duplicateUsername = false;
			  $scope.duplicateEmail = false;
			  $scope.messageStyle = "alert alert-success";
			  $scope.hideMessage = false;
			  $scope.message = "Użytkownik został zmodyfikowany";	
			  
			  $scope.user.username = $scope.username; 
			  $scope.user.password = $scope.password; 
			  $scope.user.email =  $scope.email;
			  $scope.user.role = $scope.selectedRole.value;
			  $scope.user.enabled = $scope.enabled;
			  $scope.user.locked = $scope.locked;  
		  });
	  }
  }

  $scope.deleteUser = function(id) {
	  
	  var Edit = Restangular.one('users');
	  
	  Edit.id = id;
	  
	  Edit.remove().then(function(response){
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
    
  $scope.tableParams = new ngTableParams({
        page: 1,            // show first page
        count: 10,          // count per page
        sorting: {
            id: 'asc'     // initial sorting
        } 
    }, {
    	groupBy: 'role',
        total: data.length, // length of data
        getData: function($defer, params) {
        	var orderedData = params.sorting() ? $filter('orderBy')(data, params.orderBy()) : data;
            $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count())); 
           
        }
   });
	
	$scope.isCollapsed = true;	
}]);
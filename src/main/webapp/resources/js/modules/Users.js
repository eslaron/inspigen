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
              $scope.id = $stateParams.id;
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

Users.controller('UsersController', ['$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Context',
                                     function($scope, $state, $stateParams, $filter, ngTableParams, User, Context) {
	
  $scope.all = Context.all;
  $scope.active = Context.active;
  $scope.activate = Context.activate;
  
  var data = $scope.all.users;
  	    
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


		
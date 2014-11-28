var Users = angular.module('inspigen.users', ['ui.router', 'restangular'])

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
       	  templateUrl: 'partials/admin/dashboard.html' 
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

Users.controller('UsersController', function($scope, $state, $stateParams, Restangular, $filter, ngTableParams, users,  utils) {
	
	$scope.users = users;
	
	$scope.isCollapsed = true;
	
	$scope.limit = 10;
	$scope.entitySize = 0;
	$scope.currentPage = 1;
	$scope.selectedPage = 0;
	
	//$scope.User = Restangular.all('users');            
	$scope.UserPage = Restangular.one('users?page='+$scope.currentPage+'&size='+$scope.limit);
	$scope.UserFirstPage = Restangular.one('users/firstPage?size='+$scope.limit);
	$scope.UserLastPage = Restangular.one('users/lastPage?size='+$scope.limit);
	$scope.UserEntitySize = Restangular.one('users/entitySize');
	
	$scope.userList = [{id:"", username:"", password:"", email:"", role: "", enabled:"", accountNonLocked:"", accountNonExpired:"", credentialsNonExpired:"",
		passwordToken:"", activationToken:"", passwordTokenExpiration:"", activationTokenExpiration:"", failedLogins: "", lastLoginAttempt:""}];
	
	$scope.pageCount = function() {
		return  Math.round(($scope.entitySize / $scope.limit))+1;
	}
	
	if($state.current.name == 'user.admin.users') {
		
		$scope.UserEntitySize.get().
		then(function(response){
			$scope.entitySize = response;
				$scope.UserFirstPage.getList()
				.then(function(response) {
					$scope.userList = response;
				});
		});
		
		$scope.tableParams = new ngTableParams({ 
			count: 0, // hides pager
	        sorting: {
	            name: 'desc'     
	        }
	    }, {
	    	counts: [],
	    	 
	        getData: function($defer, params) {
	        	
	        var orderedData = params.sorting() ? $scope.userList = $filter('orderBy')($scope.userList, params.orderBy()) :  $scope.userList;
	        
	        $defer.resolve(orderedData);
	        }
	    });		
	}
		
	
	$scope.getPage = function() {
		$scope.currentPage = $scope.selectedPage;
		Restangular.one('users?page='+$scope.currentPage+'&size='+$scope.limit).getList()
		.then(function(response) {
			$scope.userList = response;
	});			
			$scope.tableParams = new ngTableParams({ 
				count: 0, // hides pager
		        sorting: {
		            name: 'desc'     
		        }
		    }, {
		    	counts: [],
		    	 
		        getData: function($defer, params) {
		        	
		        var orderedData = params.sorting() ? $scope.userList = $filter('orderBy')($scope.userList, params.orderBy()) :  $scope.userList;
		        
		        $defer.resolve(orderedData);
		        }
		    });		
	};
	
	$scope.edit = {id:"", username:"", password:"", email:"", role: "", enabled:"", accountNonLocked:"", accountNonExpired:"", credentialsNonExpired:"",
		passwordToken:"", activationToken:"", passwordTokenExpiration:"", activationTokenExpiration:"", failedLogins: "", lastLoginAttempt:""};
	
    $scope.editUser =  function(uid) {   
    	
    	$scope.item = utils.findById($scope.users, uid);
    	$scope.edit = $filter('filter')($scope.userList, function (d) {return d.id === uid})[0];
    	$state.go('user.admin.users.edit', $scope.item);
    }    
});
		
'use strict';

/**
 * RailwayStationController
 * @constructor
 */
App.controller('UserController', function($scope, $http) {
	
	
		$scope.user = {username:"", password:"", email:"", enabled:""};
		
		$http.get('username')
		  .success(function(response) {
			  $scope.username = response;	  
		});
		
		/*$scope.addUser = function()	{
	
			$http.post('addUser', $scope.user).success(function(result) {
				  alert($scope.user.enabled);
        	});	
		}*/
	   
		$scope.role="";
		
	 	$scope.userRoleList = [//kolejność ma znaczenie
		                       { role: "ROLE_ADMIN", 	description: "Administrator" }, 
		                       { role: "ROLE_COORDINATOR",	description: "Koordynator" },
		                       { role: "ROLE_USER", 	description: "Wolontariusz" }
		                      ];
	 
		$scope.getRoleIndex = function(role){
			if (role=="ROLE_ADMIN") return 0;
			if (role=="ROLE_RECRUITER") return 1;
			if (role=="ROLE_USER") return 2;
		}
		
		$scope.reset = function() {
			$scope.user.username = '';
			$scope.user.password = '';
			$scope.user.email = '';
			$scope.user.firstName = '';
			$scope.user.lastName = '';
			$scope.user.pesel = '';
			$scope.user.phoneNumber = '';
		};
});
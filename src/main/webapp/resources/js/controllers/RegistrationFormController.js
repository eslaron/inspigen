'use strict';

/**
 * RailwayStationController
 * @constructor
 */
App.controller('RegistrationFormController', function($scope, $http) {
	
	$scope.user = {username:"", password:"", email:""};
		
	$scope.userNameUnique = true;
	$scope.emailUnique = true;
	$scope.timeoutMessage = '';
	
	$scope.registerUser = function() {
		
		$scope.user.username = $scope.signup.username;
		$scope.user.password = $scope.signup.password;
		$scope.user.email = $scope.signup.email;
		
		$http.post('addUser', $scope.user, {timeout: 100}).success(function(response) {
			
			alert(response);
			
			if(response == "duplicateUser")
				$scope.userNameUnique = false;
			
			if(response == "duplicateEmail")
				$scope.emailUnique = false;
			
			if(response == "duplicateUser&duplicateEmail") {
				$scope.userNameUnique = false;
				$scope.emailUnique = false;
			}
				
		}).error(function() {
			$scope.timeoutMessage = "Serwer zbyt długo nie odpowiada. Spróbuj za parę minut."
		});				 
	}
});
  

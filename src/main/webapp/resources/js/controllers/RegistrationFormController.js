'use strict';

/**
 * RailwayStationController
 * @constructor
 */
App.controller('RegistrationFormController', function($scope, $http) {
	
	$scope.user = {username:"", password:"", email:""};
	
	$scope.registerUser = function() {
		
		$scope.user.username = $scope.signup.username;
		$scope.user.password = $scope.signup.password;
		$scope.user.email = $scope.signup.email;
		
		alert($scope.user.username);
		alert($scope.user.password);
		alert($scope.user.email);
			
		$http.post('addUser', $scope.user).success(function() {});
	
	}		  
});
  

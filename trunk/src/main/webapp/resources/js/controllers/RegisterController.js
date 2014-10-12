'use strict';

/**
 * RailwayStationController
 * @constructor
 */
App.controller('RegisterController', function($scope, $http, $location) {
	
	$scope.user = {username:"", password:"", email:""};
		
	$scope.userNameUnique = true;
	$scope.emailUnique = true;
	$scope.hideMessage = true;
	$scope.activationMessage = 'Email z linkiem aktywującym konto został wysłany nad twoj adres.';
	
	$scope.registerUser = function() {
		
		$scope.user.username = $scope.signup.username;
		$scope.user.password = $scope.signup.password;
		$scope.user.email = $scope.signup.email;
		
		$http.post('addUser', $scope.user).success(function(response) {
			
			if(response.message == "duplicateUser")
				$scope.userNameUnique = false;
				$scope.signup_form.username.$setPristine();
			
			if(response.message  == "duplicateEmail")
				$scope.emailUnique = false;
				$scope.signup_form.email.$setPristine();
			
			if(response.message  == "duplicateUser&duplicateEmail") {
				$scope.userNameUnique = false;
				$scope.emailUnique = false;
				$scope.signup_form.username.$setPristine();
				$scope.signup_form.email.$setPristine();
			}
		
			if(response.message  == "activationLinkSent") {	
				$scope.userNameUnique = true;
				$scope.emailUnique = true;
				$scope.hideMessage = false;
				$scope.resetRegisterForm();
			}					
		}).error(function(){});
	}
	
	$scope.resetRegisterForm = function() {
		
		$scope.signup.username = '';
		$scope.signup.password = '';
		$scope.signup.confirmPassword = '';
		$scope.signup.email = '';
		$scope.signup.confirmEmail = '';
		
		$scope.signup_form.$setPristine();
	} 
	
	$scope.$on('$viewContentLoaded', function() {
		$scope.signup_form.$setPristine();
	});
});
  

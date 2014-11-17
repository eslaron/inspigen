'use strict';

/**
 * RailwayStationController
 * @constructor
 */
App.controller('RegisterController', function($scope, $http, $location, $resource, Restangular) {

	var User = Restangular.all('users');
	var Role = Restangular.all('roles');

	$scope.user = {username:"", password:"", email:""};
		
	$scope.userNameUnique = true;
	$scope.emailUnique = true;
	$scope.hideMessage = true;
	$scope.activationMessage = 'Email z linkiem aktywującym konto został wysłany nad twoj adres.';
	
	$scope.registerUser = function() {
		$scope.user.username = $scope.signup.username;
		$scope.user.password = $scope.signup.password;
		$scope.user.email = $scope.signup.email;
		
		User.post($scope.user)
			.then(function(response){
				$scope.message = response.message;
				
				Role.post($scope.user)
					.then(function(response){
							$scope.message = response.message;
							$scope.messageStyle = "alert alert-success";
							$scope.hideMessage = false;
							$scope.resetRegisterForm();					
					});
		});
	};
	
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
  

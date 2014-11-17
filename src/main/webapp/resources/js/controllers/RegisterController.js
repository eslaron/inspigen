'use strict';

/**
 * RailwayStationController
 * @constructor
 */
App.controller('RegisterController', function($scope, $http, $location, $resource, Restangular, $q) {

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
				alert(response.description);
				if(response.message == "Create Success") {
					Role.post($scope.user)
						.then(function(response){		
								if(response.message == "Create Success") {
									$scope.messageStyle = "alert alert-success";
									$scope.hideMessage = false;
									$scope.resetRegisterForm();
								}							
					});
				}
		},
		function(error){
			$scope.error = error.data;

			if($scope.error.description == "duplicateUser") {
				$scope.userNameUnique = false;
				$scope.signup_form.username.$setPristine();
			}
			if($scope.error.description  == "duplicateEmail") {
				$scope.emailUnique = false;
				$scope.signup_form.email.$setPristine();
			}
			if($scope.error.description  == "duplicateUser&Email") {
				$scope.userNameUnique = false;
				$scope.emailUnique = false;
				$scope.signup_form.username.$setPristine();
				$scope.signup_form.email.$setPristine();
			}
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
'use strict';

/**
 * RailwayStationController
 * @constructor
 */
App.controller('RegisterController', function($scope, $http, $location, $resource, Restangular) {

	Restangular.setBaseUrl('api/v1/');
	
	var User = Restangular.all('users');
	var Role = Restangular.all('roles');
	var Account = Restangular.all('accounts');
	
	$scope.user = {username:"", password:"", email:""};
		
	$scope.userNameUnique = true;
	$scope.emailUnique = true;
	$scope.hideMessage = true;
	$scope.activationMessage = 'Email z linkiem aktywującym konto został wysłany nad twoj adres.';
	
	$scope.registerUser = function() {
		$scope.user.username = $scope.signup.username;
		$scope.user.password = $scope.signup.password;
		$scope.user.email = $scope.signup.email;
		
		
		
		/*User.post($scope.user)
			.then(function(response){
				$scope.message = response.message;
				
				Role.post($scope.user)
					.then(function(response){
							$scope.message = response.message;
							$scope.messageStyle = "alert alert-success";
							$scope.hideMessage = false;
							$scope.resetRegisterForm();					
					});
		});*/
	};
	
	
	/*$http.get("register/activationMessage")
		.success(function(resps){
			if(resps.message =="accountActivated") {
				$scope.messageStyle = "alert alert-success";
				$scope.hideMessage = false;
				$scope.activationMessage = "Konto zostało aktywowane. Możesz się zalogować.";
			}
			
			if(resps.message =="alreadyActivated") {
				$scope.messageStyle = "alert alert-danger";
				$scope.hideMessage = false;
				$scope.activationMessage = "Konto jest już aktywne.";
			}
			
			if(resps.message == "activationLinkExpired") {
				$scope.messageStyle = "alert alert-danger";
				$scope.hideMessage = false;
				$scope.activationMessage = "Link aktywacyjny wygasł.";
			}
			
			if(resps.message == "invalidActivationLink") {
				$scope.messageStyle = "alert alert-danger";
				$scope.hideMessage = false;
				$scope.activationMessage = "Nieprawidłowy link aktywacyjny.";
			}
		});	*/
	
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
  

'use strict';

/**
 * RailwayStationController
 * @constructor
 */
App.controller('RegisterController', function($scope, $http, $window, $location) {
	
	$scope.user = {username:"", password:"", email:""};
		
	$scope.userNameUnique = true;
	$scope.emailUnique = true;
	
	$scope.registerUser = function() {
		
		$scope.user.username = $scope.signup.username;
		$scope.user.password = $scope.signup.password;
		$scope.user.email = $scope.signup.email;
		
		$http.post('addUser', $scope.user).success(function(response) {
			
			if(response == "duplicateUser")
				$scope.userNameUnique = false;
			
			if(response == "duplicateEmail")
				$scope.emailUnique = false;
			
			if(response == "duplicateUser&duplicateEmail") {
				$scope.userNameUnique = false;
				$scope.emailUnique = false;
			}
		
			if(response == "userAdded") {			
		
				$http.post('sendActivationLink', $scope.user.email)
				.then(function() {		
					   $http.get('index')
						.success(function(resp){
							$scope.index = resp;
				})
				.then(function() {
					
					 $http.get('accountActivationMessage')
						.success(function(resp) {
						
							  $scope.response = resp;
							  
							  if($scope.response == "activationLinkSent") {
									
									$scope.alertStyle = "alert alert-success";
									$scope.activationMessage = 'Email z linkiem aktywującym konto został wysłany nad twoj adres.';
									$scope.hideMessage = false;
									$scope.response='';		
							  }		
						});	

				})
			
				})
			}		
		}).error(function() {});
	}
});
  

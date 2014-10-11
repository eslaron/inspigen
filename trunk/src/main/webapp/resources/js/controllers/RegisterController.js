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
			
			if(response.message  == "duplicateEmail")
				$scope.emailUnique = false;
			
			if(response.message  == "duplicateUser&duplicateEmail") {
				$scope.userNameUnique = false;
				$scope.emailUnique = false;
			}
		
			if(response.message  == "userAdded") {	
				$scope.userNameUnique = true;
				$scope.emailUnique = true;
				
				$http.post('sendActivationLink', $scope.user.email)
				.success(function(resp) {		
					$scope.message =  resp.message;		
				})
				.then(function(){
					 if($scope.message  == "activationLinkSent") {			
							$scope.hideMessage = false;
					  }	
				});
			}		
		}).error(function() {});
	}
	

});
  

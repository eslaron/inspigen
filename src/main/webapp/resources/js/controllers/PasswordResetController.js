'use strict';

/**
 * RailwayStationController
 * @constructor
 */
App.controller('PasswordResetController', function($scope, $http) {
	
		
		$http.get('resetLinkError')
		  .success(function(response) {

			  if(response == "resetLinkExpired") 
				  $scope.errorMessage = "Link resetujący hasło wygasł.";
			  if(response == "invalidResetLink") 
				  $scope.errorMessage = "Nieprawidłowy link resetujący.";	
		});
		
		$http.get('forgotPasswordError')
		  .success(function(response) {
		
			  if(response == "emailNotRegistered") 
				  $scope.emailErrorMessage = "Nie znaleziono użytkownika z podanym adresem email.";	
		});		   
});
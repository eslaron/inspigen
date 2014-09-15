'use strict';

/**
 * RailwayStationController
 * @constructor
 */
App.controller('PasswordResetController', function($scope, $http) {

	
		$http.get('resetLinkError')
		.success(function(resp) {
		  
			if (resp == "resetLinkExpired")
				$scope.errorMessage = "Link resetujący wygasł.";
			else if(resp == "invalidResetLink")
				$scope.errorMessage = "Nieprawidłowy link resetujący.";
		});
	
		$http.get('forgotPassowordMessage')
		  .success(function(resp) {
	  
			  $scope.response = resp;
			  
			  if($scope.response == "resetLinkSent") {
					
					$scope.alertStyle = "alert alert-success";
					$scope.message = "Email z linkiem resetującym został wysłany na twój adres."
					$scope.hideMessage = false;
					$scope.hideInput = true;
					$scope.response='';
							
			  } else if ($scope.response == "emailNotRegistered") {
					
					$scope.alertStyle = "alert alert-danger";
					$scope.message = "Nie istnieje użytkownik z podanym adresem email."
					$scope.hideMessage = false;
					$scope.response='';
			  }	
		});		
});
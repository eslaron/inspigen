'use strict';

/**
 * RailwayStationController
 * @constructor
 */
App.controller('PasswordResetController', function($scope, $http, $timeout, $window) {

	
    $scope.seconds = 5;
    $scope.redirectResetLinkError = "Przekierowanie nastąpi za "+$scope.seconds+" sekund.";
    
    $http.get('index')
	.success(function(resp){
		$scope.index = resp;
	});
    
		$scope.setTimeout = function() {
			
			$timeout(function() {

				 $window.location.href = $scope.index;
				 
			    }, $scope.seconds*1000);
		}
	
		$http.get('resetLinkError')
		.success(function(resp) {
		  
			if (resp == "resetLinkExpired") {
				$scope.errorMessage = "Link resetujący wygasł.";
				$scope.setTimeout();
			}
				
			else if (resp == "invalidResetLink") {
				$scope.errorMessage = "Nieprawidłowy link resetujący.";
				$scope.setTimeout();
			}		 
		});
	
		
		$http.get('forgotPassowordMessage')
		  .success(function(resp) {
	  
			  $scope.response = resp;
			  
			  if($scope.response == "resetLinkSent") {
					
					$scope.alertStyle = "alert alert-success";
					$scope.message = "Email z linkiem resetującym został wysłany na twój adres."
					$scope.redirectMessage = "Przekierowanie nastąpi za "+$scope.seconds+" sekund.";
					$scope.hideMessage = false;
					$scope.hideInput = true;
					$scope.response='';		
					$scope.setTimeout();
									
			  } else if ($scope.response == "emailNotRegistered") {
					
					$scope.alertStyle = "alert alert-danger";
					$scope.message = "Nie istnieje użytkownik z podanym adresem email."
					$scope.hideMessage = false;
					$scope.response='';
			  }	
		});		
});
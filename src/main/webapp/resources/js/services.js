'use strict';

/* Services */

var routeForUnauthorizedAccess = '/SomeAngularRouteForUnauthorizedAccess';

var AppServices = angular.module('AngularSpringApp.services', ['restangular','ngResource','ngCookies','ui.router'])

.factory('LoginService', function($resource, Restangular) {

	return $resource(':action', {},
			{
				authenticate: {
					method: 'POST',
					params: {'action' : 'authenticate'},
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				}
			}
		);
});

/*.factory('ActivationService', function(Restangular) {
	
	return {
		
		message: function() {
			
			alert("works");
		},
		
		activateAccount: function(Restangular, token) {
			
		 return Restangular.all('inspigen/api/v1/accounts', $stateParams.token).put().then(function(response){
				
				alert(response); 
			},
			
			function(error){  
				
				alert(error);
			});
		 
		}
	}	
})*/
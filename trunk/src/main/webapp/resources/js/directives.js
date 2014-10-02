'use strict';

/* Directives */

var AppDirectives = angular.module('AngularSpringApp.directives', []);

AppDirectives.directive('appVersion', ['version', function (version) {
    return function (scope, elm, attrs) {
        elm.text(version);
    };
}])

.directive('ngMatch', function() {
return {
	require: 'ngModel',
	link : 
			function(scope, element, attrs, ngModel) {
				ngModel.$parsers.push(function(value) {
					ngModel.$setValidity('match', value == scope.$eval(attrs.ngMatch));
				return value;
				});
    		}
   		}	
})

.directive('ngUnique', function ($http, $timeout) {  
   return {   
	   		require: 'ngModel',
	   		link: function(scope, elem, attrs, ngModel) { 	  
	   			ngModel.$parsers.push(function(value) {		
	   				if(value != null) {		
	   					ngModel.loading = true;
	   					scope.loadingStyle="neutral-message";
	   					scope.loadingMessage ="Sprawdzam...";
	   					$http.post('isUnique', value, {timeout: 30000})
		        		  .success(function(response){
		            		  ngModel.$setValidity('unique', value != response);   	
		            		  ngModel.loading = false;
		        		  }).error(function() {
		        			  scope.loadingStyle="error-message";
		        			  scope.loadingMessage = "Serwer zbyt długo nie odpowiada. Spróbuj za parę minut."
		        		  });
	   				}
	   				return value;
	   			})	    	  
	   		}     
	      }
});



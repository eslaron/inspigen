'use strict';

/**
 * RailwayStationController
 * @constructor
 */
App.controller('RegistrationFormController', function($scope, $http) {
	
		  
  })
  
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
 .directive('ngUnique', function ($http){ 
   return {
      require: 'ngModel',
      link: function(scope, elem, attrs, ngModel) {
  
	          ngModel.$parsers.push(function(value) {
	     
	        	  if(value != null) {
	        		  $http.get('findUser/' + value)
	        		  .success(function(response){
	            		  ngModel.$setValidity('unique', value != scope.$eval(attrs.ngUnique)); 
	        		  })
	          	   }  
	        		  return value;
	          })
    	  }       
      }
});
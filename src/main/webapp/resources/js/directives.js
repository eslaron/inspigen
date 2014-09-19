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

.directive('ngUnique', function ($http, $timeout){ 
	 
	 var checking = null;
	 
   return {
      require: 'ngModel',
      link: function(scope, elem, attrs, ngModel) {
  
	          ngModel.$parsers.push(function(value) {
	     
	        	  if(value != null) {
	        		  
	        		  if (!checking) {
	        			  checking = $timeout(function() {
			        		  $http.post('isUnique', value)
			        		  .success(function(response){
			            		  ngModel.$setValidity('unique', value != response); 
			            		  checking = null;
			        		  }).error(function() {
			        			  checking = null;
			        		  });
	        			 }, 250);
	        		  }
	          	   }  
	        		  return value;
	          })
    	  }       
      }
});
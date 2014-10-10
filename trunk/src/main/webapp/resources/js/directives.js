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
						scope.$watch(attrs.ngMatch, function() {	
							ngModel.$setValidity('match', value == scope.$eval(attrs.ngMatch));
						});
						return value;
				});
    		}
	}	
});
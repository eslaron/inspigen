angular.module('inspigen.directives', [])


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
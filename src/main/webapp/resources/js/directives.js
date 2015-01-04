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
})

.directive('fileUpload', function() {

	  return {
	    restrict: 'A',
	    scope: {
	      file: '='
	    },
	    link: function(scope, element) {
	      var input = element[0];

	      function _handleUpload(){
	        var reader = new FileReader();
	        reader.readAsDataURL(this.files[0]);
	        reader.onload = function(ev){
	          var file = ev.target.result;
	          scope.file = file;
	        }
	      }
	      input.addEventListener('change', _handleUpload)
	    }
	  }
});

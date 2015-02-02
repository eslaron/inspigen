//Moduł zawierający dyrektywy rozrzerzające standardowy HTML
angular.module('inspigen.directives', [])

//Konfiguracja
.config(function($compileProvider){
    $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|data|blob|ftp|mailto|chrome-extension):/);
})
 
//Dyrektywa sprawdzająca zgodność dwóch pól tekstowych
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

//Dyrektywa walidująca numer PESEL
.directive('ngPesel', function() {
	
	/*
	 * @author: Aleksander Kawęczyński(algorytm) - http://blog.aleksander.kaweczynski.pl/walidacja-numerow-pesel-nip-regon-w-javascript-i-php/
	 * @author: Sebastian Sobiech (dyrektywa dla AngularJS)
	 * 
	 * */
	
return {
	require: 'ngModel',
	link : 
			function(scope, element, attrs, ngModel) {
			ngModel.$parsers.push(function(value) {	
				
				var valid = false;
				var reg = /^[0-9]{11}$/;
				
			        if(reg.test(value) == false) {
			        	valid = false;
			        }
			        else
			        {
			            var digit = (""+value).split("");
			            var check = (1*parseInt(digit[0]) + 3*parseInt(digit[1]) + 7*parseInt(digit[2]) 
			            		+ 9*parseInt(digit[3]) + 1*parseInt(digit[4]) + 3*parseInt(digit[5]) + 
			            		7*parseInt(digit[6]) + 9*parseInt(digit[7]) + 1*parseInt(digit[8]) + 
			            		3*parseInt(digit[9]))%10;
			            if(check==0) check = 10;
			            check = 10 - check;
			            if(parseInt(digit[10])==check)
			            valid = true;
			            else
			            valid = false;
			        } 
			        ngModel.$setValidity('pesel', valid == true);   
			        
				return value;
			});
		}
	}
})

//Dyrektywa do uploadu plików
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
})

//Dyrektywa do uploadu plików graficznych
.directive('imageUploadr',function($timeout){
    
    return {
      restrict: 'E',
      scope: {
        file: '=',
        filename: '=',
        filetype: '='
      },
      template: '<input type="file"/>',
      link: function(scope,element){
        
        var el = element[0].children[0];
        
        el.addEventListener('change',function(e){
          var file = e.target.files[0];
          
          var reader = new FileReader();
          
          reader.onload = function(img) {
            
            $timeout(function(){
              scope.file = img.target.result;
              scope.filename = file.name;
              scope.filetype = file.type;
            
            },0)
          }
          
          reader.readAsDataURL(file);
        })
        
      }
    }   
  })

//Dyrektywa do pobierania plików graficznych
.directive('imageDownloadr',function(){
    
    return {
      restrict: 'A',
      scope: {
        filename: '='
      },
      link: function(scope,element){
        var el = angular.element(element);

        scope.$watch('filename',function(filename){
          el.attr('download',filename)
        })
      }
    }
  });

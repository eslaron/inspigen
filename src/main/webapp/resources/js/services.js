angular.module('inspigen.services', ['ngResource'])

//Fabryka nadająca odpowiedni nagłówek żądaniu zalogowania się użytkownika
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
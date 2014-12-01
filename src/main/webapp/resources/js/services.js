angular.module('inspigen.services', ['ngResource'])

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
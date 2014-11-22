'use strict';

/* Services */

var routeForUnauthorizedAccess = '/SomeAngularRouteForUnauthorizedAccess';

var AppServices = angular.module('AngularSpringApp.services', ['restangular','ngResource','ui.router'])

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

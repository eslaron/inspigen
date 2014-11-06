'use strict';

/* Services */

var routeForUnauthorizedAccess = '/SomeAngularRouteForUnauthorizedAccess';

var AppServices = angular.module('AngularSpringApp.services', ['ngResource','ngCookies','ui.router'])

.factory('LoginService', function($resource) {

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
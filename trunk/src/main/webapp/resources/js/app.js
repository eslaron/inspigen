'use strict';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', ['ngRoute','AngularSpringApp.filters', 'AngularSpringApp.services', 'AngularSpringApp.directives']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider','$locationProvider', function ($routeProvider, $locationProvider) {
    
	$routeProvider.when('/', {
        templateUrl: 'partials/login',
    });

    $routeProvider.when('/signup', {
        templateUrl: 'partials/signup',
    });
    
    $routeProvider.when('/forgotPassword', {
        templateUrl: 'partials/forgotPassword',
    });
    
    $routeProvider.otherwise({redirectTo: '/'});
    
   $locationProvider.html5Mode(true);
}]);

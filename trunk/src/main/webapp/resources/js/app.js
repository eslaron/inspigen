'use strict';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', ['ngRoute','AngularSpringApp.filters', 'AngularSpringApp.services', 'AngularSpringApp.directives']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider','$locationProvider', function ($routeProvider, $locationProvider) {
    
	$routeProvider.when('/', {
        templateUrl: 'partials/login',
    });

    $routeProvider.when('/trains', {
        templateUrl: 'trains/layout',
    });
    
    $routeProvider.when('/railwaystations', {
        templateUrl: 'railwaystations/layout',
    });
    $routeProvider.otherwise({redirectTo: '/cars'});
    
    //$locationProvider.html5Mode(true);
}]);

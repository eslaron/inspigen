'use strict';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', ['ngRoute','AngularSpringApp.filters', 'AngularSpringApp.services', 'AngularSpringApp.directives']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider','$locationProvider', function ($routeProvider, $locationProvider) {
 	
	$routeProvider.when('/', {
        templateUrl: 'partials/login.html',
    })

    .when('/signup', {
        templateUrl: 'partials/signup.html',
    })
    
   .when('/login',   {
        templateUrl: 'partials/login.html',
    })
    
    .when('/home', {
        templateUrl: 'partials/home.html',
    })
    
    .when('/forgotPassword', {
        templateUrl: 'partials/forgotPassword.html',
    })
    
    .when('/:token', {
    	redirectTo: '/login',
    })
       
    //.otherwise({redirectTo: '/login'});
    
   $locationProvider.html5Mode(true);
}]);

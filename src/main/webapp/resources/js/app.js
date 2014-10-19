'use strict';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', ['ui.router','AngularSpringApp.filters', 'AngularSpringApp.services', 'AngularSpringApp.directives'])

.run(['$rootScope', '$state', '$stateParams', function ($rootScope,   $state,   $stateParams) {

		    $rootScope.$state = $state;
		    $rootScope.$stateParams = $stateParams;
}])

.config(['$stateProvider', '$urlRouterProvider','$locationProvider', function ($stateProvider, $urlRouterProvider, $locationProvider) {

	  //Usunięcie # z linków
	  $locationProvider.html5Mode(true);
	  
      // Use $urlRouterProvider to configure any redirects (when) and invalid urls (otherwise).
      $urlRouterProvider
               
        // If the url is ever invalid, e.g. '/asdf', then redirect to '/' aka the home state
        .otherwise('/login');

      // Use $stateProvider to configure your states.
      $stateProvider

        .state("login", {
          url: "/login",
          title: 'Zaloguj się',
          views: {
              'navbar': {
            	  templateUrl: 'partials/navbar.html' 
              },
              'content': {
            	  templateUrl: 'partials/login.html' 
              },
            }      
        })

        .state('register', {
          url: '/signup',
          title: 'Rejestracja',
          views: {
              'navbar': {
            	  templateUrl: 'partials/navbar.html' 
              },
              'content': {
            	  templateUrl: 'partials/signup.html' 
              },
            }
        })
        
        .state('activateAccount', {
	        url: "/activateAccount/:token",
	        title: 'Zaloguj się',
	          views: {
	              'navbar': {
	            	  templateUrl: 'partials/navbar.html' 
	              },
	              'content': {
	            	  templateUrl: 'partials/login.html' 
	              },
	            }  
        })
        
        .state('resetPassword', {
          url: '/forgotPassword',
          title: 'Resetuj hasło',
          views: {
              'navbar': {
            	  templateUrl: 'partials/navbar.html' 
              },
              'content': {
            	  templateUrl: 'partials/forgotPassword.html' 
              },
            }
        })
        
        .state('admin', {
          url: '/admin',
          title: 'Administracja',
          views: {
              'navbar': {
            	  templateUrl: 'partials/admin/navbar.html' 
              },
              'sidebar': {
            	  templateUrl: 'partials/admin/sidebar.html'
              },
              'content': {
            	  templateUrl: 'partials/admin/dashboard.html' 
              },
            }
        })
    }
  ]
);
		
'use strict';

var xAuthTokenHeaderName = 'x-auth-token';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', ['ui.router','ngCookies','AngularSpringApp.filters', 'AngularSpringApp.services', 'AngularSpringApp.directives'])

.run(['$http','$rootScope', '$state', '$stateParams', '$location', '$cookieStore','LoginService' ,function (
		$http, $rootScope, $state, $stateParams, $location, $cookieStore, LoginService) {

		    $rootScope.$state = $state;
		    $rootScope.$stateParams = $stateParams;
		    
		    /* Reset error when a new view is loaded */
			$rootScope.$on('$viewContentLoaded', function() {
				delete $rootScope.error;
			});

			$rootScope.hasRole = function(role) {

				if ($rootScope.user === undefined) { 
					return false;
				}

				if ($rootScope.user.roles[role] === undefined) {
					return false;
				}
				return $rootScope.user.roles[role];
			};

			$rootScope.logout = function() {
				delete $rootScope.user;
				delete $http.defaults.headers.common[xAuthTokenHeaderName];
				$cookieStore.remove('user');
				$location.path("/login");
			};

			 /* Try getting valid user from cookie or go to login page */
			var originalPath = $location.path();
			$location.path("/login");
			var user = $cookieStore.get('user');
			if (user !== undefined) {
				$rootScope.user = user;
				$http.defaults.headers.common[xAuthTokenHeaderName] = user.token;

				$location.path(originalPath);
			}
}])


// PONIŻEJ JEST COŚ ŹLE

.config(['$stateProvider', '$urlRouterProvider','$locationProvider', 
         function ( $stateProvider, $urlRouterProvider, $locationProvider, $rootScope, $q, $location) {
	
	  //Usunięcie # z linków
	  $locationProvider.html5Mode(true);

	  
      // Use $urlRouterProvider to configure any redirects (when) and invalid urls (otherwise).
      $urlRouterProvider
               
        // If the url is ever invalid, e.g. '/asdf', then redirect to '/' aka the home state
        .otherwise('/');

      // Use $stateProvider to configure your states.
      $stateProvider

      .state("index", {
          url: "/",
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
          url: '/register',
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
            },
        })
    }
  ]
);
		
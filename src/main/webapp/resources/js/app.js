'use strict';

var xAuthTokenHeaderName = 'x-auth-token';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', ['ui.router', 'ngCookies', 'permission', 'restangular', 'AngularSpringApp.filters', 'AngularSpringApp.services', 'AngularSpringApp.directives'])

.run(function($http, $rootScope, $state, $stateParams, $cookieStore, Permission) {

		    $rootScope.$state = $state;
		    $rootScope.$stateParams = $stateParams;
		       
		    /* Reset error when a new view is loaded */
			$rootScope.$on('$viewContentLoaded', function() {
				delete $rootScope.error;
			});

			Permission.defineRole('anonymous', function (stateParams) {
		        if ($rootScope.hasRole("ROLE_ADMIN") == false 
		        		|| $rootScope.hasRole("ROLE_MOD") == false 
		        			|| $rootScope.hasRole("ROLE_MOD") == false) {
		          return true;
		        }
		        return false;
		      });
			
			Permission.defineRole('admin', function (stateParams) {
		        if ($rootScope.hasRole("ROLE_ADMIN") == true) {
		          return true;
		        }
		        return false;
		      });
			
			Permission.defineRole('moderator', function (stateParams) {
		        if ($rootScope.hasRole("ROLE_MOD") == true) {
		          return true;
		        }
		        return false;
		      });
			
			Permission.defineRole('user', function (stateParams) {
		        if ($rootScope.hasRole("ROLE_USER") == true) {
		          return true;
		        }
		        return false;
		      });
			
			$rootScope.hasRole = function(role) {
					if ($rootScope.user === undefined) return false;
					if ($rootScope.user.role === undefined) return false;
				return true;
			};

			$rootScope.logout = function() {
				delete $rootScope.user;
				delete $http.defaults.headers.common[xAuthTokenHeaderName];
				$cookieStore.remove('user');
				$state.go('login');
			};

			 /* Try getting valid user from cookie or go to login page */
			var user = $cookieStore.get('user');

			if (user !== undefined) {
				$rootScope.user = user;
				$http.defaults.headers.common[xAuthTokenHeaderName] = user.token;
				$state.go('index');
			}
})

.config(['RestangularProvider', '$stateProvider', '$urlRouterProvider', 
         function (RestangularProvider, $stateProvider, $urlRouterProvider) {
   
	  RestangularProvider.setBaseUrl('api/v1');
      
      $urlRouterProvider.otherwise('/');
      
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
            }, 
          data: {
	          permissions: {
	        	  only: ['anonymous']
	          }
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
            },
            data: {
                permissions: {
                	only: ['anonymous']
                }
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
            },
            data: {
                permissions: {
                	only: ['anonymous']
                }
            }
        })
        
        .state('activateAccount', {
	        url: "/activateAccount/{token}",
	        title: 'Zaloguj się',
	        views: {
	              'navbar': {
	            	  templateUrl: 'partials/navbar.html' 
	              },
	              'content': {
	            	  templateUrl: 'partials/login.html',
	            	  controller: function($scope, activation) {
	            		  if (activation == "activationLinkExpired") {
	            			  $scope.activationMessage = "Link aktywacyjny wygasł.";
	            			  $scope.messageStyle = "alert alert-info";
							  $scope.hideMessage = false;
	            		  }
	            		  if (activation == "alreadyActivated") {
	            			  $scope.activationMessage = "Konto jest już aktywne. Zaloguj się.";
	            			  $scope.messageStyle = "alert alert-info";
							  $scope.hideMessage = false;
	            		  }
	            		  if (activation =="accountActivated") {
	            			  $scope.activationMessage = "Konto zostało aktywowane. Zaloguj się.";
	            			  $scope.messageStyle = "alert alert-success";
							  $scope.hideMessage = false;	  
	            		  }
	            		  
	            		  if (activation =="invalidActivationLink") {
	            			  $scope.activationMessage = "Nieprawidłowy link aktywacyjny.";
	            			  $scope.messageStyle = "alert alert-danger";
							  $scope.hideMessage = false;	  
	            		  }
	  	  	        }
	              },	              
	        },
	        data: {
	        	permissions: {
	        		only: ['anonymous']
	            }
	        },
	        resolve: {
	        	activation: function(Restangular, $stateParams) {
	        		var User = Restangular.one('accounts');
	        		User.activationToken = $stateParams.token;
	        		return User.put().then(function(response){
	        			return response.message;
	        		});
	        	}
	        }  
        })
        
        .state('forgotPassword', {
          url: '/forgotPassword',
          title: 'Resetuj hasło',
          views: {
              'navbar': {
            	  templateUrl: 'partials/navbar.html' 
              },
              'content': {
            	  templateUrl: 'partials/forgotPassword.html' 
              },
            },
            data: {
                permissions: {
                  only: ['anonymous']
                }
            }
        })
        
        .state('user', {
             url: '/user',
        	 abstract: true,
        	 template: '<div ui-view></div>', 
        	 data: {
                 permissions: {
                 	only: ['user','admin','moderator']
                 }
             }
        })
        
        .state('user.admin', {
          title: 'Panel wolontariusza',
          abstract: false,
          url: '/admin',
          views: {
              'navbar@': {
            	  templateUrl: 'partials/admin/navbar.html' 
              },
              'sidebar@': {
            	  templateUrl: 'partials/admin/sidebar.html'
              },
              'content@': {
            	  templateUrl: 'partials/admin/dashboard.html' 
              },
            },
            data: {
                permissions: {
                  only: ['admin']
                }
            }
        })
        
        .state('user.member', {
          title: 'Panel wolontariusza',
          abstract: false,
          url: '/dashboard',
          views: {
              'navbar@': {
            	  templateUrl: 'partials/user/navbar.html' 
              },
              'sidebar@': {
            	  templateUrl: 'partials/user/sidebar.html'
              },
              'content@': {
            	  templateUrl: 'partials/user/dashboard.html' 
              },
            },
            data: {
                permissions: {
                  only: ['user']
                }
            }
        })
              
        .state('user.moderator', {
          title: 'Panel koordynatora',
          abstract: false,
          url: '/mod',
          views: {
              'navbar': {
            	  templateUrl: 'partials/mod/navbar.html' 
              },
              'sidebar': {
            	  templateUrl: 'partials/mod/sidebar.html'
              },
              'content': {
            	  templateUrl: 'partials/mod/dashboard.html' 
              },
            },
           data: {
                permissions: {
                  only: ['moderator']
                }
           }
        });
    }
  ]
);
		
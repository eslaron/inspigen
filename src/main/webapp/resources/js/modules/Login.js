var xAuthTokenHeaderName = 'x-auth-token';

var Login = angular.module('inspigen.login', ['ui.router', 'LocalStorageModule', 'permission'])

.run(function($http, $rootScope, $state, Permission, localStorageService) {

			Permission.defineRole('anonymous', function (stateParams) {
		        if ($rootScope.hasRole("ROLE_ADMIN") == false 
		        		|| $rootScope.hasRole("ROLE_MOD") == false 
		        			|| $rootScope.hasRole("ROLE_USER") == false) {
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
					if ($rootScope.user == undefined) return false;
					if ($rootScope.user.role == role) return true;
					else return false;
			};

			$rootScope.logout = function() {
				delete $rootScope.user;
				delete $http.defaults.headers.common[xAuthTokenHeaderName];
				localStorageService.cookie.remove('user');
				$state.go('login');
			};

			 /* Try getting valid user from cookie or go to login page */
			var user = localStorageService.cookie.get('user');
			
			if (user != null) {
				$rootScope.user = user;
				$rootScope.loggedUsername = user.username;
				
				$http.defaults.headers.common[xAuthTokenHeaderName] = user.token;

				  /* if ($rootScope.hasRole("ROLE_ADMIN") == true)
					   	$state.go('user.admin');

				   else if ($rootScope.hasRole("ROLE_MOD") == true) 
						$state.go('user.moderator');
  
				   else if ($rootScope.hasRole("ROLE_USER") == true) 
					   	$state.go('user.member');		*/	
			}
})

.config(['$stateProvider', 'localStorageServiceProvider',
         function ($stateProvider, localStorageServiceProvider) {

	
	 localStorageServiceProvider
	    .setPrefix('inspigen')
	    .setStorageType('sessionStorage')
	    .setNotify(true, true)
	    .setStorageCookie(5, '/');
	 
      $stateProvider
      
      .state("login", {
          url: "/login",
          title: 'Zaloguj się',
          views: {
              'navbar': {
            	  templateUrl: 'partials/navbar.html' 
              },
              'content': {
            	  templateUrl: 'partials/login.html',
            	  controller: 'LoginController'
              }
            },
            data: {
                permissions: {
                	only: ['anonymous']
                }
            }
        });          
    }
  ]
);
		

Login.controller('LoginController', function($scope, $rootScope, $state, $http, LoginService, localStorageService) {
	
	  $scope.hideMessage = true;
		$scope.loginMessage = 'Niepoprawny login lub hasło';
		
			$scope.login = function() {
				LoginService.authenticate($.param({username: $scope.username, password: $scope.password}), function(user) {
					$rootScope.user = user;
					$http.defaults.headers.common[ xAuthTokenHeaderName ] = user.token;
					localStorageService.cookie.set('user', user);	
	
					$rootScope.loggedUsername = localStorageService.cookie.get('user').username;
					
		      	if (user.role == 'ROLE_ADMIN')
	        		$state.go('users.admin');

	        	if (user.role == 'ROLE_MOD')
	        		$state.go('users.moderator');

	        	if (user.role == 'ROLE_USER')
	        		$state.go('users.member');			
				},
					function(error) {
						$scope.error = error.data;
						
						if($scope.error.description == "invalidCredentials") {
							$scope.messageStyle = "alert alert-info";
							$scope.hideMessage = false;
						};
						
						if($scope.error.description == "accountLocked") {
							$scope.messageStyle = "alert alert-danger";
							$scope.hideMessage = false;
							$scope.loginMessage = 'Konto zablokowane. Spróbuj zalogować się za 15 minut.';
						};
					}	
				);
			};
});
//Nazwa nagłówka
var xAuthTokenHeaderName = 'x-auth-token';

//Moduł obsługujący logowanie użytkownika i zabezpieczenia
var Login = angular.module('inspigen.login', ['ui.router', 'LocalStorageModule', 'permission'])

//Podczas działania aplikacji wykonywane są operacje
.run(function($http, $rootScope, $state, Permission, localStorageService) {

			//Definicje ról użytkowników
	
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
			
			//Globalna funkcja sprawdzająca czy zalogowany uzytkownik posiada daną role
			$rootScope.hasRole = function(role) {
					if ($rootScope.user == undefined) return false;
					if ($rootScope.user.role == role) return true;
					else return false;
			};

			//Globalna funkcja wylogowywująca użytkownika
			$rootScope.logout = function() {
				delete $rootScope.user;
				delete $http.defaults.headers.common[xAuthTokenHeaderName];
				localStorageService.cookie.remove('user');
				$state.go('login');
			};

			//Pobranie zawartości ciasteczka z tokenem autoryzującym
			var user = localStorageService.cookie.get('user');
			
			//Jeśli ciasteczko nie jest puste, to pobierz informacje o zalogowanym użytkowniku
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

//Konfiguracja
.config(['$stateProvider', 'localStorageServiceProvider',
         function ($stateProvider, localStorageServiceProvider) {

	 //Parametry pliku cookie przechowywane w pamięci przeglądarki
	 localStorageServiceProvider
	    .setPrefix('inspigen')
	    .setStorageType('sessionStorage')
	    .setNotify(true, true)
	    .setStorageCookie(5, '/');
	 
	 //Routing stanów (widoków)
	 
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
		
//KONTROLERY

//Kontroler logowania
Login.controller('LoginController', function($scope, $rootScope, $state, $http, LoginService, localStorageService) {
	
	  $scope.hideMessage = true;
		$scope.loginMessage = 'Niepoprawny login lub hasło';
		
			//Funkcja autentykująca użytkownika
			$scope.login = function() {
				LoginService.authenticate($.param({username: $scope.username, password: $scope.password}), function(user) {
					$rootScope.user = user;
					$http.defaults.headers.common[ xAuthTokenHeaderName ] = user.token;
					localStorageService.cookie.set('user', user);	
	
					//Pobierz nazwę zalogowanego użytkownika z pliku Cookie
					$rootScope.loggedUsername = localStorageService.cookie.get('user').username;
				
				//Przekieruj do odpowiedni paneli użytkownika według roli zawartej w pliku cookie	
		      	if (user.role == 'ROLE_ADMIN')
	        		$state.go('app.admin.dashboard');

	        	if (user.role == 'ROLE_MOD')
	        		$state.go('app.moderator.dashboard');

	        	if (user.role == 'ROLE_USER')
	        		$state.go('app.member.dashboard');			
				},
					function(error) {
						$scope.error = error.data;
						
						//Obsługa błędnej próby logowania
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
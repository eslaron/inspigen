'use strict';

App.controller('LoginController', function($scope, $rootScope, $state, $http, LoginService, localStorageService) {
	
	$scope.hideMessage = true;
	$scope.loginMessage = 'Niepoprawny login lub hasło';
	
		$scope.login = function() {
			LoginService.authenticate($.param({username: $scope.username, password: $scope.password}), function(user) {
				$rootScope.user = user;
				$http.defaults.headers.common[ xAuthTokenHeaderName ] = user.token;
				localStorageService.cookie.set('user', user);	
				
	      	if (user.role == 'ROLE_ADMIN')
        		$state.go('user.admin');

        	if (user.role == 'ROLE_MOD')
        		$state.go('user.moderator');

        	if (user.role == 'ROLE_USER')
        		$state.go('user.member');			
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
  

'use strict';

App.controller('LoginController', function($scope, $rootScope, $location, $state, $http, $cookieStore, LoginService) {
	
		$scope.login = function() {
			LoginService.authenticate($.param({username: $scope.username, password: $scope.password}), function(user) {
				$rootScope.user = user;
				$http.defaults.headers.common[ xAuthTokenHeaderName ] = user.token;
				$cookieStore.put('user', user);
			
	      	if (user.role == 'ROLE_ADMIN')
        		$state.go('user.admin');

        	if (user.role == 'ROLE_MOD')
        		$state.go('user.moderator');

        	if (user.role == 'ROLE_USER')
        		$state.go('user.member');			
			});
		};
});
  

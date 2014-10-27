'use strict';

App.controller('LoginController', function($scope, $rootScope, $location, $http, $cookieStore, LoginService) {
	
		$scope.login = function() {
			LoginService.authenticate($.param({username: $scope.username, password: $scope.password}), function(user) {
				$rootScope.user = user;
				$http.defaults.headers.common[ xAuthTokenHeaderName ] = user.token;
				$cookieStore.put('user', user);
				
				if (user.roles.ROLE_ADMIN = true)
				$location.path("/admin");
			});
		};
});
  

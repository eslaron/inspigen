App.controller('UsersController', function($scope, $timeout, $state, $stateParams, Restangular) {

	$scope.Users = Restangular.all('users');
	$scope.passwordReset = Restangular.one('users/passwordReset');

	$scope.userNameUnique = true;
	$scope.emailUnique = true;
	$scope.emailFound = true;
	$scope.hideMessage = true;
	$scope.activationMessage = 'Email z linkiem aktywującym konto został wysłany nad twoj email.';
	$scope.resetMessage = 'Email z linkiem resetującym hasło został wysłany nad twoj email.';
	
	$scope.user = {username:"", password:"", email:""};
	
	$scope.registerUser = function() {
		$scope.user.username = $scope.signup.username;
		$scope.user.password = $scope.signup.password;
		$scope.user.email = $scope.signup.email;
		
		$scope.Users.post($scope.user)
			.then(function(response){
				if(response.message == "Create Success") {		
					$scope.messageStyle = "alert alert-success";
					$scope.hideMessage = false;
					$scope.resetRegisterForm();
				}
		},
		function(error){
			$scope.error = error.data;

			if($scope.error.description == "duplicateUser") {
				$scope.userNameUnique = false;
				$scope.signup_form.username.$setPristine();
			}
			if($scope.error.description  == "duplicateEmail") {
				$scope.emailUnique = false;
				$scope.signup_form.email.$setPristine();
			}
			if($scope.error.description  == "duplicateUser&Email") {
				$scope.userNameUnique = false;
				$scope.emailUnique = false;
				$scope.signup_form.username.$setPristine();
				$scope.signup_form.email.$setPristine();
			}
		});
	}
	
	$scope.sendResetPasswordEmail = function() { 
		$scope.Users.get($scope.reset.email)
			.then(function(response) {			
				if(response.message == "resetLinkSent") {
					$scope.messageStyle = "alert alert-success";
					$scope.hideMessage = false;
					$scope.resetMessage = 'Email z linkiem resetującym hasło został wysłany nad twoj email.';
					
					$timeout(function () {
						$state.go('login');
			          }, 10000);
				}
			},
				function(error) {
					$scope.error = error.data;
					
					if ($scope.error.message == "emailNotRegistered") {
						$scope.resetMessage = "Nie istnieje użytkownik z podanym adresem email.";
						$scope.messageStyle = "alert alert-info";
						$scope.hideMessage = false;
					}
				}	
			);
	}
	
	$scope.resetPassword = function() {
		
		$scope.passwordReset.password = $scope.reset.password;
		$scope.passwordReset.passwordToken = $stateParams.token;
		
		$scope.passwordReset.put().then(function(response){
				if(response.message = "passwordChanged") {
					$scope.resetMessage = "Hasło zostało zmienione.";
					$scope.messageStyle = "alert alert-success";
					$scope.hideMessage = false;
				}
				
				if(response.message = "resetLinkExpired") {
					$scope.resetMessage = "Link resetujący hasło wygasł.";
					$scope.messageStyle = "alert alert-info";
					$scope.hideMessage = false;
				}
			},
			function(error){
				$scope.error = error.data;
				
				if ($scope.error.message == "invalidResetLink") {
					$scope.resetMessage = "Nieprawidłowy link resetujący";
					$scope.messageStyle = "alert alert-danger";
					$scope.hideMessage = false;
				}
			}
		);
	}
	
	$scope.resetRegisterForm = function() {
		
		$scope.signup.username = '';
		$scope.signup.password = '';
		$scope.signup.confirmPassword = '';
		$scope.signup.email = '';
		$scope.signup.confirmEmail = '';
		
		$scope.signup_form.$setPristine();
	} 
	
	$scope.$on('$viewContentLoaded', function() {
		
		$scope.signup_form.$setPristine();
	});
});
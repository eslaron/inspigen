App.controller('UsersController', function($scope, Restangular) {

	$scope.Users = Restangular.all('users');

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
		$scope.Users.get($scope.reset.email);
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
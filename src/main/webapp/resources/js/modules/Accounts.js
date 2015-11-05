//Moduł obsługujący konta użytkowników
var Accounts = angular.module('inspigen.accounts', ['ui.router', 'restangular'])

//Konfiguracja
.config(['$stateProvider', function ($stateProvider) {
   
	//Routing stanów (widoków)
	
      $stateProvider
      
        .state('register', {
          url: '/register',
          title: 'Rejestracja',
          views: {
              'navbar': {
            	  templateUrl: 'partials/navbar.html' 
              },
              'content': {
            	  templateUrl: 'partials/signup.html',
            	  controller: 'AccountsController'        	
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
	            	  controller:  'AccountActivationController'       
	              },
	        },
	        data: {
	        	permissions: {
	        		only: ['anonymous']
	            }
	        }   
        })
        
        .state('resetPassword', {
          url: '/resetPassword',
          title: 'Resetuj hasło',
          views: {
              'navbar': {
            	  templateUrl: 'partials/navbar.html' 
              },
              'content': {
            	  templateUrl: 'partials/resetPassword.html',
            	  controller: 'AccountsController'
              },
            },
            data: {
                permissions: {
                  only: ['anonymous']
                }
            }
        })
        
          .state('newPassword', {
          url: '/newPassword/{token}',
          title: 'Resetuj hasło',
          views: {
              'navbar': {
            	  templateUrl: 'partials/navbar.html' 
              },
              'content': {
            	  templateUrl: 'partials/newPassword.html',
            	  controller: 'AccountsController'
              },
            },
            data: {
                permissions: {
                  only: ['anonymous']
                }
            }
        })
    }
  ]
);

//KONTROLERY

//Kontroler aktywacji użytkowników
Accounts.controller('AccountActivationController', function($scope, $stateParams, Restangular) {
	
	var Account = Restangular.one('accounts/accountActivation');
	
	Account.activationToken = $stateParams.token;
	
	//Ządanie PUT aktywujące konto nowo zarejestrowanego użytkownika
	Account.put().then(function(response){
	
		  if (response.message == "activationLinkExpired") {
			  $scope.activationMessage = "Link aktywacyjny wygasł.";
			  $scope.messageStyle = "alert alert-info";
			  $scope.hideMessage = false;
		  }
		  if (response.message == "alreadyActivated") {
			  $scope.activationMessage = "Konto jest już aktywne. Zaloguj się.";
			  $scope.messageStyle = "alert alert-info";
			  $scope.hideMessage = false;
		  }
		  if (response.message =="accountActivated") {
			  $scope.activationMessage = "Konto zostało aktywowane. Zaloguj się.";
			  $scope.messageStyle = "alert alert-success";
			  $scope.hideMessage = false;	  
		  }
		  
		  if (response.message =="invalidActivationLink") {
			  $scope.activationMessage = "Nieprawidłowy link aktywacyjny.";
			  $scope.messageStyle = "alert alert-danger";
			  $scope.hideMessage = false;	  
		  }
	});
});

//Kontroler kont
Accounts.controller('AccountsController', function($scope, $timeout, $state, $stateParams, Restangular) {

	$scope.Accounts = Restangular.all('accounts');
	$scope.passwordResetEmailCheck = Restangular.all('accounts/passwordReset');
	$scope.passwordReset = Restangular.one('accounts/passwordReset');

	$scope.userNameUnique = true;
	$scope.emailUnique = true;
	$scope.emailFound = true;
	$scope.hideMessage = true;
	$scope.activationMessage = 'Email z linkiem aktywującym konto został wysłany nad twoj email.';
	$scope.resetMessage = 'Email z linkiem resetującym hasło został wysłany nad twoj email.';
	
	$scope.user = {username:"", password:"", email:""};
	
	//Funkcja rejestrująca użytkownika
	$scope.registerUser = function() {
		
		$scope.hideMessage = true;
		
		$scope.user.username = $scope.signup.username;
		$scope.user.password = $scope.signup.password;
		$scope.user.email = $scope.signup.email;
		
		$scope.Accounts.post($scope.user)
			.then(function(response){
				if(response.message == "Create Success") {		
					$scope.messageStyle = "alert alert-success";
					$scope.hideMessage = false;
					$scope.userNameUnique = true;
					$scope.emailUnique = true;
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
	
	//Funkcja wysyłająca email resetujący hasło
	$scope.sendResetPasswordEmail = function() { 
		console.log("Checking");
		$scope.hideMessage = true;
		
		$scope.passwordResetEmailCheck.post($scope.reset.email)
			.then(function(response) {			
				if(response.message == "resetLinkSent") {
					$scope.messageStyle = "alert alert-success";
					$scope.hideMessage = false;
					$scope.resetMessage = 'Email z linkiem resetującym hasło został wysłany nad twoj email.';
					$scope.reset.email = "";
					$scope.passwordReset_form.email.$setPristine();
					
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
						$scope.reset.email = "";
						$scope.passwordReset_form.email.$setPristine();
					}
				}	
			);
	}
	
	//Funkcja resetująca hasło
	$scope.resetPassword = function() {
		
		$scope.passwordReset.password = $scope.reset.password;
		$scope.passwordReset.passwordToken = $stateParams.token;
		
		$scope.passwordReset.put().then(function(response) {
			
				if(response.message == "resetLinkExpired") {
					$scope.resetMessage = "Link resetujący hasło wygasł.";
					$scope.messageStyle = "alert alert-info";
					$scope.hideMessage = false;
					$scope.reset.password = "";
					$scope.reset.confirmPassword = "";
					$scope.newPassword_form.password.$setPristine();
					$scope.newPassword_form.confirmPassword.$setPristine();
				}
			
				if(response.message == "passwordChanged") {
					$scope.resetMessage = "Hasło zostało zmienione.";
					$scope.messageStyle = "alert alert-success";
					$scope.hideMessage = false;
					$scope.reset.password = "";
					$scope.reset.confirmPassword = "";
					$scope.newPassword_form.password.$setPristine();
					$scope.newPassword_form.confirmPassword.$setPristine();
					
					$timeout(function () {
						$state.go('login');
			          }, 10000);
				}
				
			},
			function(error){
				$scope.error = error.data;
				
				if ($scope.error.message == "invalidResetLink") {
					$scope.resetMessage = "Nieprawidłowy link resetujący";
					$scope.messageStyle = "alert alert-danger";
					$scope.hideMessage = false;
					$scope.reset.password = "";
					$scope.reset.confirmPassword = "";
					$scope.newPassword_form.password.$setPristine();
					$scope.newPassword_form.confirmPassword.$setPristine();
				}
			}
		);
	}
	
	//Funkcja czyszcząca formularz rejestracji
	$scope.resetRegisterForm = function() {
		
		$scope.signup.username = '';
		$scope.signup.password = '';
		$scope.signup.confirmPassword = '';
		$scope.signup.email = '';
		$scope.signup.confirmEmail = '';
		
		$scope.signup_form.$setPristine();
	} 
});
		
//Moduł obsługujący ustawienia
var Settings = angular.module('inspigen.settings', ['ui.router', 'restangular','ngTable'])

//Konfiguracja
.config(['$stateProvider', function ($stateProvider) {
		
		//Routing stanów(widoków)
	
		$stateProvider
			    
		      .state('app.admin.settings', {
		     title: 'Ustawienia',
		     abstract: false,
		     url: '/settings',
		     views: {
		         'navbar@': {
		       	  templateUrl: 'partials/admin/navbar.html' 
		         },
		         'sidebar@': {
		       	  templateUrl: 'partials/admin/sidebar.html'
		         },
		         'content@': {
		       	  templateUrl: 'partials/admin/settings.html',
		       	  controller: 'UsersController' 
		         },
		       },
		       data: {
		           permissions: {
		             only: ['admin']
		           }
		       }
		   })  	   	
	    }	           
]);

//KONTROLERY

//Kontroler modułu ustawień
Settings.controller('SettingsController', ['$rootScope', '$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 'Address','Settings', 'Context', 'Restangular',
	                                     function($rootScope, $scope, $state, $stateParams, $filter, ngTableParams, User, Person, Address,Settings, Context, Restangular) {
		
	  $scope.all = Context.all;
	  $scope.active = Context.active;
	  $scope.activate = Context.activate;
	  
	  $scope.isCollapsed = true;	
	  
	  $scope.settings = $scope.all.settings[0];
	  
	  //Funkcja aktualizująca ustawienia aplikacji
	  $scope.editSettings = function(settings) {
		  
		  var EditSettings = Restangular.one('settings');
		  
		  EditSettings.id = 1;
		  EditSettings.maxLoginAttempts = $scope.settings.maxLoginAttempts;
		  EditSettings.accountLockTime = $scope.settings.accountLockTime;
		  EditSettings.linkExpirationTime = $scope.settings.linkExpirationTime;
		  EditSettings.emailAddress = $scope.settings.emailAddress;
		  EditSettings.emailHost = $scope.settings.emailHost;
		  EditSettings.emailPort = $scope.settings.emailPort;
		  EditSettings.emailUsername = $scope.settings.emailUsername;
		  EditSettings.emailPassword = $scope.settings.emailPassword;
		
		  EditSettings.put().then(function(response){
			
			  $scope.messageStyle = "alert alert-success";
			  $scope.hideMessage = false;
			  $scope.message = "Ustawienia zostały zmienione";			  			  
		  });
	  } 
}]);
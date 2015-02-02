//Główny moduł skupiający wszystkie pomniejsze moduły
angular.module('inspigen', ['ui.router', 'ui.bootstrap','mgcrea.ngStrap', 'permission', 'ngResource', 'base64',
                            'inspigen.login', 'inspigen.accounts', 'inspigen.users', 'inspigen.persons',
                            'inspigen.addresses', 'inspigen.events', 'inspigen.participants', 'inspigen.locations', 
                            'inspigen.attachments', 'inspigen.settings', 'inspigen.services', 'inspigen.directives', 
                            'inspigen.filters', 'angular-loading-bar'])

 //Operacje wykonywane podczas działania aplikacji
.run(function($rootScope, $state, $stateParams) {

		    $rootScope.$state = $state;
		    $rootScope.$stateParams = $stateParams;
		       
		    /* Zresetuj komunikat błędu kiedy nowy widok jest załadowany */
			$rootScope.$on('$viewContentLoaded', function() {
				delete $rootScope.error;
			});
})

//Konfiguracja
.config(['RestangularProvider', '$stateProvider', '$urlRouterProvider', 'cfpLoadingBarProvider',
         function (RestangularProvider, $stateProvider, $urlRouterProvider, cfpLoadingBarProvider) {

	  //Wyłączenie spinner w pasku ładowania
	  cfpLoadingBarProvider.includeSpinner = false;
	
	  //Bazowe URI(URL) dla REST API
	  RestangularProvider.setBaseUrl('api/v1');
      
	  //Przekierowanie w przypadku błędnej ścieżki
      $urlRouterProvider.otherwise('/');
         
      //Routing stanów (widoków)
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
      });
	}
  ]
);
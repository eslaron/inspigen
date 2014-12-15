angular.module('inspigen', ['ui.router', 'ui.bootstrap', 'permission', 'ngResource','ngTasty',
                            'inspigen.login', 'inspigen.accounts', 'inspigen.users', 'inspigen.services', 
                            'inspigen.directives', 'inspigen.filters','angular-loading-bar', 'checklist-model'])

.run(function($rootScope, $state, $stateParams) {

		    $rootScope.$state = $state;
		    $rootScope.$stateParams = $stateParams;
		       
		    /* Reset error when a new view is loaded */
			$rootScope.$on('$viewContentLoaded', function() {
				delete $rootScope.error;
			});
})

.config(['RestangularProvider', '$stateProvider', '$urlRouterProvider', 'cfpLoadingBarProvider',
         function (RestangularProvider, $stateProvider, $urlRouterProvider, cfpLoadingBarProvider) {

	  cfpLoadingBarProvider.includeSpinner = false;
	
	  RestangularProvider.setBaseUrl('api/v1');
      
      $urlRouterProvider.otherwise('/');
      
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
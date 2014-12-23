var Participants = angular.module('inspigen.participants', ['ui.router', 'restangular','ngTable'])

.config(['$stateProvider', function ($stateProvider) {
	
	$stateProvider
	
	.state('user.admin.participants', {
		     title: 'Wydarzenia',
		     abstract: false,
		     url: '/events',
		     views: {
		         'navbar@': {
		       	  templateUrl: 'partials/admin/navbar.html' 
		         },
		         'sidebar@': {
		       	  templateUrl: 'partials/admin/sidebar.html'
		         },
		         'content@': {
		       	  templateUrl: 'partials/admin/participants.html',
		       	  controller: 'EventsController'      
		         }
		       },
		       data: {
		           permissions: {
		             only: ['admin']
		           }
		       },
		       resolve: {
		    	 
		   	   }
		   }) 
}
           
]);

//KONTROLERY

Users.controller('EventsController', ['$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'Participant', 'Restangular',
                                     function($scope, $state, $stateParams, $filter, ngTableParams, Participant, Context, Restangular) {
	
  $scope.all = Context.all;
  $scope.active = Context.active;
  $scope.activate = Context.activate;
  
  var data = $scope.all.events;

}]);
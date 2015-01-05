var Addresses = angular.module('inspigen.addresses', ['ui.router', 'restangular','ngTable'])

.config(['$stateProvider', function ($stateProvider) {
	
	$stateProvider
	
	.state('user.admin.addresses', {
		     title: 'Szkoły/Uczelnie',
		     abstract: false,
		     url: '/addresses',
		     views: {
		         'navbar@': {
		       	  templateUrl: 'partials/admin/navbar.html' 
		         },
		         'sidebar@': {
		       	  templateUrl: 'partials/admin/sidebar.html'
		         },
		         'content@': {
		       	  templateUrl: 'partials/common/addresses.html',
		       	  controller: 'AddressesController'      
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
		   
		 .state('user.admin.addresses.add', {
	     title: 'Dodaj szkołę/uczelnię',
	     abstract: false,
	     url: '/:id/add',
	     views: {
	         'navbar@': {
	       	  templateUrl: 'partials/admin/navbar.html' 
	         },
	         'sidebar@': {
	       	  templateUrl: 'partials/admin/sidebar.html'
	         },
	         'content@': {
	       	  templateUrl: 'partials/common/addAddress.html',
	       	  controller: function($stateParams, $scope, Address) {
	       		  $scope.address = {user_id:""};
	              $scope.address.user_id = $stateParams.id;
	          }      
	         }
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

Addresses.controller('AddressesController', ['$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 'Address', 'Event', 'Participant','Location', 'Context', 'Restangular',
                                     function($scope, $state, $stateParams, $filter, ngTableParams, User, Person, Address, Event, Participant, Location, Context, Restangular) {
	
  $scope.all = Context.all;
  $scope.active = Context.active;
  $scope.activate = Context.activate;

 
}]);
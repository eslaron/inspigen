var Addresses = angular.module('inspigen.addresses', ['ui.router', 'restangular','ngTable'])

.config(['$stateProvider', function ($stateProvider) {
	
	$stateProvider
	
	.state('user.admin.addresses', {
		     title: 'Adresy',
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
	     title: 'Dodaj adres',
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
	   
	    .state('user.admin.addresses.edit', {
	     title: 'Edytuj adres',
	     abstract: false,
	     url: '/:id/edit',
	     views: {
	         'navbar@': {
	       	  templateUrl: 'partials/admin/navbar.html' 
	         },
	         'sidebar@': {
	       	  templateUrl: 'partials/admin/sidebar.html'
	         },
	         'content@': {
	       	  templateUrl: 'partials/common/editAddress.html',
	       	  controller: function($stateParams, $scope, Address) {
	       		  $scope.address = {};
	              $scope.address.id = $stateParams.id;
	              $scope.address = Address.getAddressById($stateParams.id);
	              $scope.isCollapsed = true;
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

Persons.controller('AddressesController', ['$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 'Address', 'Event', 'Participant','Location', 'Context', 'Restangular', 
                                     function($scope, $state, $stateParams, $filter, ngTableParams, User, Person, Address, Event, Participant, Location, Context, Restangular) {
	
  $scope.all = Context.all;
  $scope.active = Context.active;
  $scope.activate = Context.activate;
  
  $scope.registeredAddress = true;
  $scope.mailAddress = false;
  
  var AllAddresses = Restangular.all('addresses');
  var OneAddress = Restangular.one('addresses');
  
  $scope.addAddress = function(address) {

	  $scope.address.registeredAddress = $scope.registeredAddress;
	  $scope.address.mailAddress = $scope.mailAddress;
	  
	  		AllAddresses.post($scope.address).then(function(response) {
	  			  Address.loadAddressesFromJson();
			  }, function(error) {
				  $scope.error = error.data; 					
			  });		  
  }
  
  $scope.editAddress = function(address) {
	  
		OneAddress.id = $scope.address.id;
		OneAddress.firstName = $scope.address.firstName;
		OneAddress.lastName = $scope.address.lastName;
		OneAddress.pesel = $scope.address.pesel;
		OneAddress.phoneNumber = $scope.address.phoneNumber;
		OneAddress.user_id = $scope.address.user_id;
	 
		OneAddress.put().then(function(response) {
			Address.loadAddressesFromJson();
		  }, function(error) {
			  $scope.error = error.data; 					
		  });		  
  }
  
}]);
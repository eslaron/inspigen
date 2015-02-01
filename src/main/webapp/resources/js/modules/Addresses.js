var Addresses = angular.module('inspigen.addresses', ['ui.router', 'restangular','ngTable'])

.config(['$stateProvider', function ($stateProvider) {
	
	$stateProvider
	
	.state('app.admin.addresses', {
		     title: 'Adresy',
		     abstract: false,
		     url: '/addresses',
		     views: {
		         'navbar@': {
		       	  templateUrl: 'partials/admin/navbar.html' 
		         },
		         'sidebar@': {
		       	  templateUrl: 'partials/admin/sidebar.html'
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
		   
		 .state('app.admin.addresses.add', {
	     title: 'Dodaj adres',
	     abstract: false,
	     url: '/:id/add',
	     views: {
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
	   
	    .state('app.admin.addresses.edit', {
	     title: 'Edytuj adres',
	     abstract: false,
	     url: '/:id/edit',
	     views: {
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
	   
	   .state('app.moderator.addresses', {
		     title: 'Adresy',
		     abstract: false,
		     url: '/addresses',
		     views: {
		         'navbar@': {
		       	  templateUrl: 'partials/admin/navbar.html' 
		         },
		         'sidebar@': {
		       	  templateUrl: 'partials/admin/sidebar.html'
		         }
		       },
		       data: {
		           permissions: {
		             only: ['moderator']
		           }
		       },
		       resolve: {

		   	   }
		   }) 
		   
		 .state('app.moderator.addAddress', {
	     title: 'Dodaj adres',
	     abstract: false,
	     url: '/addresses/:id/add',
	     views: {
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
	             only: ['moderator']
	           }
	       }
	   })
	   
	    .state('app.moderator.editAddress', {
	     title: 'Edytuj adres',
	     abstract: false,
	     url: '/addresses/:id/edit',
	     views: {
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
	             only: ['moderator']
	           }
	       }
	   })
	   
	   .state('app.member.addresses', {
		     title: 'Adresy',
		     abstract: false,
		     url: '/addresses',
		     views: {
		         'navbar@': {
		       	  templateUrl: 'partials/admin/navbar.html' 
		         },
		         'sidebar@': {
		       	  templateUrl: 'partials/admin/sidebar.html'
		         }
		       },
		       data: {
		           permissions: {
		             only: ['user']
		           }
		       },
		       resolve: {

		   	   }
		   }) 
		   
		 .state('app.member.addAddress', {
	     title: 'Dodaj adres',
	     abstract: false,
	     url: '/addresses/:id/add',
	     views: {
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
	             only: ['user']
	           }
	       }
	   })
	   
	    .state('app.member.editAddress', {
	     title: 'Edytuj adres',
	     abstract: false,
	     url: '/addresses/:id/edit',
	     views: {
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
	             only: ['user']
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
  
  $scope.addresses = Context.all.addresses;
  
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
		OneAddress.address = $scope.address.address;
		OneAddress.city = $scope.address.city;
		OneAddress.zipCode = $scope.address.zipCode;
		OneAddress.state = $scope.address.state;
		OneAddress.country = $scope.address.country;
		OneAddress.registeredAddress = $scope.address.registeredAddress;
		OneAddress.mailAddress = $scope.address.mailAddress;
		OneAddress.user_id = $scope.address.user_id;
		
		OneAddress.put().then(function(response) {
			if($scope.address.mailAddress == true 
					&& $scope.address.registeredAddress == true) {
				for(var i=0; i < $scope.addresses.length; i++) {	
					if($scope.addresses[i].user_id == $scope.address.user_id 
							&&  (($scope.addresses[i].registeredAddress == false 
									&& $scope.addresses[i].mailAddress == true)
										|| ($scope.addresses[i].registeredAddress == true 
											&& $scope.addresses[i].mailAddress == false))) {
						$scope.deleteAddress($scope.addresses[i].id);
					}
				}
			}		
			Address.loadAddressesFromJson();
		  }, function(error) {
			  $scope.error = error.data; 					
		  });		  
  }
  
  $scope.deleteAddress = function(id) {
	      
	  OneAddress.id = id;
		  
	  OneAddress.remove().then(function(response){});
  }
	  
}]);
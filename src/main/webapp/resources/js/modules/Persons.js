var Persons = angular.module('inspigen.persons', ['ui.router', 'restangular','ngTable'])

.config(['$stateProvider', function ($stateProvider) {
	
	$stateProvider
	
	.state('app.admin.persons', {
		     title: 'Dane osobowe',
		     abstract: false,
		     url: '/persons',
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
		   
		 .state('app.admin.persons.add', {
	     title: 'Dodaj dane osobowe',
	     abstract: false,
	     url: '/:id/add',
	     views: {
	         'content@': {
	       	  templateUrl: 'partials/common/addPerson.html',
	       	  controller: function($stateParams, $scope, Person) {
	       		  $scope.person = {user_id:""};
	              $scope.person.user_id = $stateParams.id;
	          }      
	         }
	       },
	       data: {
	           permissions: {
	             only: ['admin']
	           }
	       }
	   })
	   
	    .state('app.admin.persons.edit', {
	     title: 'Edytuj dane osobowe',
	     abstract: false,
	     url: '/:id/edit',
	     views: {
	         'content@': {
	       	  templateUrl: 'partials/common/editPerson.html',
	       	  controller: function($stateParams, $scope, Person) {
	       		  $scope.person = {};
	              $scope.person.id = $stateParams.id;
	              $scope.person = Person.getPersonById($stateParams.id);
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
	   
	   .state('app.moderator.persons', {
		     title: 'Dane osobowe',
		     abstract: false,
		     url: '/persons',
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
		   
		 .state('app.moderator.addPerson', {
	     title: 'Dodaj dane osobowe',
	     abstract: false,
	     url: '/persons/:id/add',
	     views: {
	         'content@': {
	       	  templateUrl: 'partials/common/addPerson.html',
	       	  controller: function($stateParams, $scope, Person) {
	       		  $scope.person = {user_id:""};
	              $scope.person.user_id = $stateParams.id;
	          }      
	         }
	       },
	       data: {
	           permissions: {
	             only: ['moderator']
	           }
	       }
	   })
	   
	    .state('app.moderator.editPerson', {
	     title: 'Edytuj dane osobowe',
	     abstract: false,
	     url: '/persons/:id/edit',
	     views: {
	         'content@': {
	       	  templateUrl: 'partials/common/editPerson.html',
	       	  controller: function($stateParams, $scope, Person) {
	       		  $scope.person = {};
	              $scope.person.id = $stateParams.id;
	              $scope.person = Person.getPersonById($stateParams.id);
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
	   
	   .state('app.member.persons', {
		     title: 'Dane osobowe',
		     abstract: false,
		     url: '/persons',
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
		   
		 .state('app.member.addPerson', {
	     title: 'Dodaj dane osobowe',
	     abstract: false,
	     url: '/persons/:id/add',
	     views: {
	         'content@': {
	       	  templateUrl: 'partials/common/addPerson.html',
	       	  controller: function($stateParams, $scope, Person) {
	       		  $scope.person = {user_id:""};
	              $scope.person.user_id = $stateParams.id;
	          }      
	         }
	       },
	       data: {
	           permissions: {
	             only: ['user']
	           }
	       }
	   })
	   
	    .state('app.member.editPerson', {
	     title: 'Edytuj dane osobowe',
	     abstract: false,
	     url: '/persons/:id/edit',
	     views: {
	         'content@': {
	       	  templateUrl: 'partials/common/editPerson.html',
	       	  controller: function($stateParams, $scope, Person) {
	       		  $scope.person = {};
	              $scope.person.id = $stateParams.id;
	              $scope.person = Person.getPersonById($stateParams.id);
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

Persons.controller('PersonsController', ['$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 'Event', 'Participant','Location', 'Context', 'Restangular', 
                                     function($scope, $state, $stateParams, $filter, ngTableParams, User, Person, Event, Participant, Location, Context, Restangular) {
	
  $scope.all = Context.all;
  $scope.active = Context.active;
  $scope.activate = Context.activate;
  
  var AllPersons = Restangular.all('persons');
  var OnePerson = Restangular.one('persons');
  
  $scope.addPerson = function(person) {

	  		  AllPersons.post($scope.person).then(function(response) {
	  			  Person.loadPersonsFromJson();
			  }, function(error) {
				  $scope.error = error.data; 					
			  });		  
  }
  
  $scope.editPerson = function(person) {
	  
		OnePerson.id = $scope.person.id;
		OnePerson.firstName = $scope.person.firstName;
		OnePerson.lastName = $scope.person.lastName;
		OnePerson.pesel = $scope.person.pesel;
		OnePerson.phoneNumber = $scope.person.phoneNumber;
		OnePerson.user_id = $scope.person.user_id;
	 
		OnePerson.put().then(function(response) {
			Person.loadPersonsFromJson();
		  }, function(error) {
			  $scope.error = error.data; 					
		  });		  
  }
  
}]);


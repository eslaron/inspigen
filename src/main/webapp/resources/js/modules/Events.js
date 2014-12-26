var Events = angular.module('inspigen.events', ['ui.router', 'restangular','ngTable'])

.config(['$stateProvider', function ($stateProvider) {
	
	$stateProvider
	
	.state('user.admin.events', {
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
		       	  templateUrl: 'partials/common/events.html',
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
		   
		 .state('user.admin.events.add', {
	     title: 'Dodaj wydarzenie',
	     abstract: false,
	     url: '/add',
	     views: {
	         'navbar@': {
	       	  templateUrl: 'partials/admin/navbar.html' 
	         },
	         'sidebar@': {
	       	  templateUrl: 'partials/admin/sidebar.html'
	         },
	         'content@': {
	       	  templateUrl: 'partials/common/addEvent.html',
	       	  controller: 'EventsController'      
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

Events.controller('EventsController', ['$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 'Event', 'Participant','School', 'Context', 'Restangular',
                                     function($scope, $state, $stateParams, $filter, ngTableParams, User, Person, Event, Participant, School, Context, Restangular) {
	
  $scope.all = Context.all;
  $scope.active = Context.active;
  $scope.activate = Context.activate;
  
  var data = $scope.all.events;
 
  var Add = Restangular.all('events');
  
  
  $scope.users = User.getAllUsers();
  
  $scope.persons = Person.getAllPersons();
  
  $scope.schools = School.getAllSchools();
  
  $scope.coordinators = [];
  
	  for(var i = $scope.persons.length - 1; i >= 0; i--) {	
		  for(var j = $scope.users.length - 1; j >= 0; j--) {	
			  if($scope.users[j].role == "Koordynator") {
				  if($scope.users[j].id == $scope.persons[i].userId)
					  $scope.coordinators.push($scope.persons[i]);
			  }
		  }	
	  }	

  $scope.persons = Person.getAllPersons();
  
  $scope.addEvent = function(event) {
  	    
	  Add.post($scope.event).then(function(response){
	
		  $scope.messageStyle = "alert alert-success";
		  $scope.hideMessage = false;
		  $scope.message = "Wydarzenie dodane";			
	
	  }, function(error) {
		  $scope.error = error.data;
		 			
	  });
  }
 
}]);
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
		       	  templateUrl: 'partials/admin/events.html',
		       	  controller: 'EventsController'      
		         }
		       },
		       data: {
		           permissions: {
		             only: ['admin']
		           }
		       },
		       resolve: {
		    	   events: ['Event','Context', function(Event, Context) {  
			      		return  Event.loadEventsFromJson()
			    	    .then(function(newlyLoadedEvents){
			    	    	Context.all.events = Event.getAllEvents();
			    	    });
		    	   }],
		    	   participants: ['Participant','Context', function(Participant, Context) {  
			      		return  Participant.loadParticipantsFromJson()
			    	    .then(function(newlyLoadedParticipants){
			    	    	Context.all.participants = Participant.getAllParticipants();
			    	    });
		    	   }],
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
	       	  templateUrl: 'partials/admin/addEvent.html',
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

Events.controller('EventsController', ['$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 'Event', 'Participant', 'Restangular',
                                     function($scope, $state, $stateParams, $filter, ngTableParams, User, Person, Event, Participant, Context, Restangular) {
	
  $scope.all = Context.all;
  $scope.active = Context.active;
  $scope.activate = Context.activate;
  
  var data = $scope.all.events;
 
  $scope.users = User.getAllUsers();
  $scope.persons = Person.getAllPersons();
  
  $scope.addEvent = function(event) {
  
  for(var i = $scope.users.length - 1; i >= 0; i--) {	
	    if($scope.users[i].id == $scope.event.user_id)  
	    	alert($scope.users[i].username);
  	}
  }
 
}]);
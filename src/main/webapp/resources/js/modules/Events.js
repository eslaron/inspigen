var Events = angular.module('inspigen.events', ['ui.router', 'restangular','ngTable'])

.config(['$stateProvider', function ($stateProvider) {
	
	$stateProvider
	
	.state('app.admin.events', {
		     title: 'Wydarzenia',
		     abstract: false,
		     url: '/events',
		     views: {
		         'content@': {
		       	  templateUrl: 'partials/common/events.html',
		       	  controller: function($scope) {
		       		  $scope.event = {};      
		       	  }
		         }
		       },
		       data: {
		           permissions: {
		             only: ['admin']
		           }
		       }
		   }) 
		   
		.state('app.moderator.events', {
	    title: 'Wydarzenia',
	    abstract: false,
	    url: '/events',
	    views: {
	        'content@': {
	      	  templateUrl: 'partials/common/events.html',
	      	 controller: function($scope) {
	       		  $scope.event = {};      
	       	  }
	        }
	      },
	      data: {
	          permissions: {
	       	  only: ['moderator']
	          }
	      }
	   }) 
	   
	   .state('app.member.events', {
	    title: 'Wydarzenia',
	    abstract: false,
	    url: '/events',
	    views: {
	        'content@': {
	      	  templateUrl: 'partials/common/events.html',
	      	 controller: function($scope) {
	       		  $scope.event = {};      
	       	  }
	        }
	      },
	      data: {
	          permissions: {
	       	  only: ['user']
	          }
	      }
	   })  
		   
		 .state('app.admin.events.add', {
	     title: 'Dodaj wydarzenie',
	     abstract: false,
	     url: '/add',
	     views: {
	         'content@': {
	       	  templateUrl: 'partials/common/addEvent.html',
	       	  controller: function($scope) {
	       		  $scope.event = {};      
	       	  }    
	         }
	       },
	       data: {
	           permissions: {
	             only: ['admin']
	           }
	       }
	   })
	   
	    .state('app.moderator.addEvent', {
	     title: 'Dodaj wydarzenie',
	     abstract: false,
	     url: '/events/add',
	     views: {
	         'content@': {
	       	  templateUrl: 'partials/common/addEvent.html',
	       	  controller: function($scope) {
	       		  $scope.event = {};      
	       	  }    
	         }
	       },
	       data: {
	           permissions: {
	             only: ['moderator']
	           }
	       }
	   })
	   
	   .state('app.admin.events.edit', {
	     title: 'Edytuj wydarzenie',
	     abstract: false,
	     url: '/:id/edit',
	     views: {
	         'content@': {
	       	  templateUrl: 'partials/common/editEvent.html',
	       	  controller: function($stateParams, $scope, Event) {
	       		  $scope.event = {};
	              $scope.event.id = $stateParams.id;
	              $scope.event = Event.getEventById($stateParams.id);
	              $scope.isCollapsed = true;
	          }      
	         }
	       },
	       data: {
	           permissions: {
	             only: ['admin','mod']
	           }
	       }
	   })
	   
	    .state('app.moderator.editEvent', {
	     title: 'Edytuj wydarzenie',
	     abstract: false,
	     url: '/events/:id/edit',
	     views: {
	         'content@': {
	       	  templateUrl: 'partials/common/editEvent.html',
	       	  controller: function($stateParams, $scope, Event) {
	       		  $scope.event = {};
	              $scope.event.id = $stateParams.id;
	              $scope.event = Event.getEventById($stateParams.id);
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
	   
	 .state('app.admin.events.details', {
     title: 'Szczegóły wydarzenia',
     abstract: false,
     url: '/:id/details',
     views: {
         'content@': {
       	  templateUrl: 'partials/common/eventDetails.html',
       	  controller: function($stateParams, $scope, Event, Location, Participant) {
       		  $scope.event = {};
              $scope.event.id = $stateParams.id;
              $scope.event = Event.getEventById($stateParams.id);
             
              $scope.locations = Location.getAllLocations();
      
              for(var i = $scope.locations.length - 1; i >= 0; i--) {
  			    if($scope.locations[i].id == $scope.event.location_id) {
  			       $scope.location = $scope.locations[i];
  			    }
  			  }

              $scope.isCollapsed = true;
          } 
         },
       },
       data: {
           permissions: {
             only: ['admin']
           }
       }
   })
   
    .state('app.moderator.eventDetails', {
	     title: 'Szczegóły wydarzenia',
	     abstract: false,
	     url: '/events/:id/details',
	     views: {
	    	 'content@': {
	          	  templateUrl: 'partials/common/eventDetails.html',
	          	  controller: function($stateParams, $scope, Event, Location, Participant) {
	          		  $scope.event = {};
	                 $scope.event.id = $stateParams.id;
	                 $scope.event = Event.getEventById($stateParams.id);
	                
	                 $scope.locations = Location.getAllLocations();
	         
	                 for(var i = $scope.locations.length - 1; i >= 0; i--) {
	     			    if($scope.locations[i].id == $scope.event.location_id) {
	     			       $scope.location = $scope.locations[i];
	     			    }
	     			  }

	                 $scope.isCollapsed = true;
	             } 
	            },
	          },
	       data: {
	           permissions: {
	             only: ['moderator']
	           }
	       }
	   })
	   
	   .state('app.member.eventDetails', {
	     title: 'Szczegóły wydarzenia',
	     abstract: false,
	     url: '/events/:id/details',
	     views: {
	    	 'content@': {
	          	  templateUrl: 'partials/common/eventDetails.html',
	          	  controller: function($stateParams, $scope, Event, Location, Participant) {
	          		  $scope.event = {};
	                 $scope.event.id = $stateParams.id;
	                 $scope.event = Event.getEventById($stateParams.id);
	                
	                 $scope.locations = Location.getAllLocations();
	         
	                 for(var i = $scope.locations.length - 1; i >= 0; i--) {
	     			    if($scope.locations[i].id == $scope.event.location_id) {
	     			       $scope.location = $scope.locations[i];
	     			    }
	     			  }

	                 $scope.isCollapsed = true;
	             } 
	            },
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

Events.controller('EventsController', ['$rootScope','$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 
                                       'Event', 'Participant','Location', 'Context', 'Restangular', 
                                     function($rootScope, $scope, $state, $stateParams, $filter, ngTableParams, User, Person, Event, 
                                    		 Participant, Location, Context, Restangular) {
	
  $scope.all = Context.all;
  $scope.active = Context.active;
  $scope.activate = Context.activate;
  
  var data = $scope.all.events;

  $scope.loggedUser = User.getLoggedUserByUsername($rootScope.loggedUsername);
  $scope.loggedUserParticipating = User.getLoggedUserIsParticipating();
  $scope.loggedUsersEventId = User.getInEventWithId();
  $scope.participantApproved = false;
  
  var AllEvents = Restangular.all('events');
  var OneEvent = Restangular.one('events');
  var AllParticipants = Restangular.all('participants');
  var OneParticipant = Restangular.one('participants');
  
  $scope.users = User.getAllUsers();
  $scope.persons = Person.getAllPersons();
  $scope.locations = Location.getAllLocations();
  $scope.participants = Participant.getAllParticipants();
  
  $scope.duplicateParticipant = false;
  
  $scope.coordinators = [];
  $scope.eventCoordinator = {};
  $scope.eventParticipants = [];
  $scope.eventLocations = [];

  $scope.prepareCoordinatorList = function() {
	  for(var i = $scope.persons.length - 1; i >= 0; i--) {	
		  for(var j = $scope.users.length - 1; j >= 0; j--) {	
			  if($scope.users[j].role == "Koordynator") {
				  if($scope.users[j].id == $scope.persons[i].user_id)
					  $scope.coordinators.push($scope.persons[i]);
			  }
		  }	
	  }	
  }
  
  $scope.findEventCoordinator = function() {
	  for(var i = $scope.coordinators.length - 1; i >= 0; i--) {	
 		  if($scope.coordinators[i].user_id == $scope.event.user_id) {
 			  $scope.eventCoordinator = $scope.coordinators[i];
 		  }
	  }
  }
  
  $scope.prepareEventParticipantsList = function() {
	  
	  $scope.tempParticipant = {id:"", firstName:"", lastName:"", user_id:"", approved:""};
	  
	  for(var i = $scope.participants.length - 1; i >= 0; i--) {	
		  if($scope.participants[i].event_id == $scope.event.id) {
			  for(var j = $scope.persons.length - 1; j >= 0; j--) {
				  if($scope.persons[j].user_id == $scope.participants[i].user_id) {
					  
					  $scope.tempParticipant.id = $scope.persons[j].id;
					  $scope.tempParticipant.firstName = $scope.persons[j].firstName;
					  $scope.tempParticipant.lastName = $scope.persons[j].lastName;
					  $scope.tempParticipant.user_id = $scope.persons[j].user_id;
				  	  $scope.tempParticipant.approved = $scope.participants[i].approved;
					  
					  $scope.eventParticipants.push($scope.tempParticipant);
					  $scope.tempParticipant = {};
				  }		  				
			  }	  
		  }						  	  
	  }  
  }
  
  $scope.prepareCoordinatorList();
  $scope.findEventCoordinator();
  $scope.prepareEventParticipantsList();
  
  $scope.addEvent = function(event) {
  	    
	  AllEvents.post($scope.event).then(function(response){
	
		  $scope.messageStyle = "alert alert-success";
		  $scope.hideMessage = false;
		  $scope.message = "Wydarzenie dodane";		
		  
		  return  Event.loadEventsFromJson()
	  	    .then(function(newlyLoadedEvents){
	  	    	Context.all.events = Event.getAllEvents();
	  	    });
	
	  }, function(error) {
		  $scope.error = error.data;
		 			
	  });
  }
  
  $scope.editEvent = function(event) {
	    
	  OneEvent.id = $scope.event.id;
	  OneEvent.name = $scope.event.name;
	  OneEvent.type = $scope.event.type;
	  OneEvent.startDate = $scope.event.startDate;
	  OneEvent.endDate = $scope.event.endDate;
	  OneEvent.description = $scope.event.description;
	  OneEvent.location_id = $scope.event.location_id;
	  OneEvent.user_id = $scope.event.user_id;
	  	  
	  OneEvent.put().then(function(response){
		  
		  Event.loadEventsFromJson();
		  $scope.messageStyle = "alert alert-success";
		  $scope.hideMessage = false;
		  $scope.message = "Wydarzenie zaktualizowane";			
	
	  }, function(error) {
		  $scope.error = error.data;
		 			
	  });
  }
  
  $scope.deleteEvent = function(id) {
	    
	  OneEvent.id = id;
	  
	  OneEvent.remove().then(function(response){
		  $scope.messageStyle = "alert alert-success";
		  $scope.hideMessage = false;
		  $scope.message = "Wydarzenie zostało usunięta";
		  
		  for(var i = data.length - 1; i >= 0; i--) {
			    if(data[i].id == id) {
			       data.splice(i, 1);
			    }
			}
		  
		  $scope.tableParams.reload();
	  });
  }
  
  //Dodawanie uczesnitków do wydarzenia
  $scope.addEventParticipant = function(userId,eventId) {
	  	  
	  //Zerujemy informacje o duplikatach
	  $scope.duplicateParticipantMessage = '';
	  $scope.duplicateParticipant = false;
	  
	  //Przygotowujemy obiekt do wysłania na serwer.
	  $scope.participant = {};
	  $scope.participant.event_id = eventId;
	  $scope.participant.user_id = userId;
	  $scope.participant.eventRole = "uczestnik";
	  
	  if($rootScope.hasRole('ROLE_USER') == true)
		  $scope.participant.approved = false;
	  
	  if($rootScope.hasRole('ROLE_ADMIN') == true || $rootScope.hasRole('ROLE_MOD'))
		  $scope.participant.approved = true;
	      
	  //Zapisujemy do pamięci cache informację o tym, że zalogowany użytkownik dołączył do wydarzenia.
	  if(userId == $scope.loggedUser.id) {	  
		  $scope.loggedUserParticipating = User.setLoggedUserIsParticipating(true);
		  $scope.loggedUsersEventId = User.setInEventWithId(eventId);
	  } 
	  
	  //Blokujemy próbę dodania tej samej osoby.
	  for(var i = $scope.eventParticipants.length - 1; i >= 0; i--) {	
 		  if($scope.eventParticipants[i].user_id == userId) {
 			  $scope.duplicateParticipant = true;
 			  $scope.duplicateParticipantMessage = "Uczestnik został już dodany.";
 			  $scope.alreadyParticipating = "Zgłosiłeś się już do uczestnictwa.";
 		  }
	  }
 	  
	  //Blokujemy próbę dodania koordynatora do uczestników.
	  if($scope.eventCoordinator.user_id == userId) {
		  $scope.duplicateParticipant = true;
		  $scope.duplicateParticipantMessage = "Ten użytkownik koordynuje to wydarzenie. Wybierz kogoś innego.";
 	  }
 
	  //Jeżeli jest dodawany zupełnie nowy uczestnik, to zapisz do bazy.
	  if($scope.duplicateParticipant == false) {

		  AllParticipants.post($scope.participant).then(function(response){
		
			  $scope.messageStyle = "alert alert-success";
			  $scope.hideMessage = false;
			  $scope.message = "Uczestnik dodany";
			  
			  //Znajdź dane osobowe nowego uczestnika i dodaj je do listy uczestników.
			  for(var i = $scope.persons.length - 1; i >= 0; i--) {	
				  if($scope.persons[i].user_id == userId) {
					  
					  $scope.tempParticipant.id = $scope.persons[i].id;
					  $scope.tempParticipant.firstName = $scope.persons[i].firstName;
					  $scope.tempParticipant.lastName = $scope.persons[i].lastName;
					  $scope.tempParticipant.user_id = $scope.persons[i].user_id;
				  	  $scope.tempParticipant.approved = $scope.participant.approved;
					  
					  $scope.eventParticipants.push($scope.tempParticipant);
					  $scope.tempParticipant = {};
					  }	  
			  }	
			  			  		 
			  //Załaduj ponownie uczestników z bazy po dodaniu nowego rekordu.
			  Participant.loadParticipantsFromJson().then(function() {	

			  });
				  		  
		  }, function(error) {
			  $scope.error = error.data;
			 			
		  });
	  }
  }
  
  $scope.approveParticipant = function(eventId,userId) {
	      
	  //Znadź id rekordu z uczestnikiem
	  for(var i = $scope.participants.length - 1; i >= 0; i--) {	
		  if($scope.participants[i].user_id == userId) {
			  OneParticipant.id = $scope.participants[i].id;		
			  break;
		  }						  	  
	  }

	  OneParticipant.event_id = eventId;
	  OneParticipant.user_id = userId;
	  OneParticipant.eventRole = "uczestnik";
	  OneParticipant.approved = true;
	  	  
	  OneParticipant.put().then(function(response){

		  //Znajdź indeks użytkownika na liście uczestników
		  for(var j = $scope.eventParticipants.length - 1; j >= 0; j--) {
			  if($scope.eventParticipants[j].user_id == userId)
			  $scope.eventParticipants[j].approved = true;
		  }	
		  
	  }, function(error) {
		  $scope.error = error.data;
		 			
	  });
  }
  
 //Usuwanie uczestników wydarzenia
 $scope.deleteEventParticipant = function(userId) {

	  //Znajdź indeks użytkownika na liście uczestników
	  for(var j = $scope.eventParticipants.length - 1; j >= 0; j--) {
		  if($scope.eventParticipants[j].user_id == userId)
			  var deleteIndex = $scope.eventParticipants.indexOf($scope.eventParticipants[j]);
	  }	
		 
	  //Wypełnij na nowo listę uczestników znajdujących się w bazie.
	  $scope.participants = Participant.getAllParticipants();
	  
	  //Zapisujemy do pamięci cache informację o tym, że zalogowany użytkownik wypisał się z wydarzenia.
	  if(userId == $scope.loggedUser.id) {	  
		  $scope.loggedUserParticipating = User.setLoggedUserIsParticipating(false);
		  $scope.loggedUsersEventId = User.setInEventWithId(0);
	  } 
	  
	  //Znadź id rekordu z uczestnikiem
	  for(var i = $scope.participants.length - 1; i >= 0; i--) {	
		  if($scope.participants[i].user_id == userId) {
			  OneParticipant.id = $scope.participants[i].id;		
			  break;
		  }						  	  
	  }

	  //Usuwamy uczestnika z listy znajdującej się w pamięci cache
	  $scope.eventParticipants.splice(deleteIndex, 1);
	  
	  //Usuwamy uczestnika z bazy i czekamy na odpowiedź serwera.
	  OneParticipant.remove().then(function(response){
		  
		  $scope.messageStyle = "alert alert-success";
		  $scope.hideMessage = false;
		  $scope.message = "Uczestnik został usuniety";

	  });
  } 
 
 	$scope.cid = 0;
 
 	$scope.getConfirmDeleteId = function(id) {

 		$scope.cid = id;
 	}
 
 	$scope.tableParams = new ngTableParams({
     page: 1,            // show first page
     count: 10,          // count per page
     sorting: {
         id: 'asc'     // initial sorting
     }
 	}, {
     total: data.length, // length of data
     getData: function($defer, params) {
     	        	
     	var orderedData = params.sorting() ? $filter('orderBy')(data, params.orderBy()) : data;
     	var filteredData = params.filter() ? $filter('filter')(orderedData, params.filter()) : orderedData; 
     	
     	params.total(filteredData.length); // set total for recalc pagination
     	
         $defer.resolve(filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count()));          
     }
});
}]);
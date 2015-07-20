//Moduł obsługujący wydarzenia
var Events = angular.module('inspigen.events', ['ui.router', 'restangular','ngTable'])

//Konfiguracja
.config(['$stateProvider', function ($stateProvider) {
	
	//Routing stanów(widoków)
	
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
       	  controller: function($stateParams, $scope, Event, Location) {
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
	          	  controller: function($stateParams, $scope, Event, Location) {
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
	          	  controller: function($stateParams, $scope, Event, Location) {
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

//Kontroler wydarzeń
Events.controller('EventsController', ['$rootScope','$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 
                                       'Event','Location', 'Context', 'Restangular',
                                     function($rootScope, $scope, $state, $stateParams, $filter, ngTableParams, User, Person, Event, 
                                    		  Location, Context, Restangular) {
	
  $scope.all = Context.all;
  $scope.active = Context.active;
  $scope.activate = Context.activate;
  
  //Zmienna globalna dla listy wydarzeń
  var data = $scope.all.events;

  //Informacje o zalogowanym użytkowniku
  $scope.loggedUser = User.getLoggedUserByUsername($rootScope.loggedUsername);
  $scope.loggedUsersEventId = User.getInEventWithId();
  
  //Mapowania zasobów REST API
  var AllEvents = Restangular.all('events');
  var OneEvent = Restangular.one('events');
  
  //Ładowanie całych kolekcji do zmiennych
  $scope.users = User.getAllUsers();
  $scope.persons = Person.getAllPersons();
  $scope.locations = Location.getAllLocations();
  
  //Listy
  $scope.coordinators = [];
  $scope.eventLocations = [];
  
  //Koordynator wydarzenia
  $scope.eventCoordinator = {};

  //Funkcja przygotowujaca listę koordynatorów
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
  
  //Funkcja wyszukująca koordynatora wydarzenia
  $scope.findEventCoordinator = function() {
	  for(var i = $scope.coordinators.length - 1; i >= 0; i--) {	
 		  if($scope.coordinators[i].user_id == $scope.event.user_id) {
 			  $scope.eventCoordinator = $scope.coordinators[i];
 		  }
	  }
  }

  
  //Wywołanie funkcji przygotowujących listy
  $scope.prepareCoordinatorList();
  $scope.findEventCoordinator();
  
  //Funkcja dodająca wydarzenie
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
  
  //Funkcja aktualizująca wydarzenie
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
  
  //Funkcja usuwająca wydarzenie
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

 	//Potwierdzenie usunięcia wydarzenia
 	$scope.cid = 0;
 
 	$scope.getConfirmDeleteId = function(id) {

 		$scope.cid = id;
 	}
 
 	//Funkcja obsługująca tabelę z wydarzeniami
 	$scope.tableParams = new ngTableParams({
     page: 1,            
     count: 10,          
     sorting: {
         id: 'asc'     
     }
 	}, {
     total: data.length, 
     getData: function($defer, params) {
     	        	
     	var orderedData = params.sorting() ? $filter('orderBy')(data, params.orderBy()) : data;
     	var filteredData = params.filter() ? $filter('filter')(orderedData, params.filter()) : orderedData; 
     	
     	params.total(filteredData.length); 
     	
         $defer.resolve(filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count()));          
     }
});
}]);
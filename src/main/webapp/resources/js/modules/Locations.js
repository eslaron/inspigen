//Moduł obsługujący lokacje
var Locations = angular.module('inspigen.locations', ['ui.router', 'restangular','ngTable'])

//Konfiguracja
.config(['$stateProvider', function ($stateProvider) {
	
	//Routing stanów (widoków)
	
	$stateProvider
	
	.state('app.admin.locations', {
		     title: 'Miejsca wydarzeń',
		     abstract: false,
		     url: '/locations',
		     views: {
		         'navbar@': {
		       	  templateUrl: 'partials/admin/navbar.html' 
		         },
		         'sidebar@': {
		       	  templateUrl: 'partials/admin/sidebar.html'
		         },
		         'content@': {
		       	  templateUrl: 'partials/common/locations.html',
		       	  controller: 'LocationsController'      
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
		   
		.state('app.moderator.locations', {
	    title: 'Użytkownicy',
	    abstract: false,
	    url: '/locations',
	    views: {
	        'content@': {
	      	  templateUrl: 'partials/common/locations.html',
	      	  controller: 'LocationsController' 
	        },
	      },
	      data: {
	          permissions: {
	       	  only: ['moderator']
	          }
	      }
	   })
		   
		 .state('app.admin.locations.add', {
	     title: 'Dodaj miejsce',
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
	       	  templateUrl: 'partials/common/addLocation.html',
	       	  controller: 'LocationsController'      
	         }
	       },
	       data: {
	           permissions: {
	             only: ['admin']
	           }
	       }
	   })
	   
	    .state('app.moderator.addLocation', {
	    title: 'Użytkownicy',
	    abstract: false,
	    url: '/locations/add',
	    views: {
	        'content@': {
	      	  templateUrl: 'partials/common/addLocation.html',
	      	  controller: 'LocationsController' 
	        },
	      },
	      data: {
	          permissions: {
	       	  only: ['moderator']
	          }
	      }
	   })
	   
	   .state('app.admin.locations.edit', {
	     title: 'Edytuj miejsce',
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
	       	  templateUrl: 'partials/common/editLocation.html',
	       	  controller: function($stateParams, $scope, Location) {
	       		  $scope.location = {};
	              $scope.location.id = $stateParams.id;
	              $scope.location = Location.getLocationById($stateParams.id);
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
	   
	   	 .state('app.moderator.editLocation', {
	     title: 'Edytuj miejsce',
	     abstract: false,
	     url: '/locations/:id/edit',
	     views: {
	         'navbar@': {
	       	  templateUrl: 'partials/mod/navbar.html' 
	         },
	         'sidebar@': {
	       	  templateUrl: 'partials/mod/sidebar.html'
	         },
	         'content@': {
	       	  templateUrl: 'partials/common/editLocation.html',
	       	  controller: function($stateParams, $scope, Location) {
	       		  $scope.location = {};
	              $scope.location.id = $stateParams.id;
	              $scope.location = Location.getLocationById($stateParams.id);
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
	   
	   .state('app.admin.locations.details', {
     title: 'Szczegóły lokacji',
     abstract: false,
     url: '/:id/details',
     views: {
         'navbar@': {
       	  templateUrl: 'partials/admin/navbar.html' 
         },
         'sidebar@': {
       	  templateUrl: 'partials/admin/sidebar.html'
         },
         'content@': {
       	  templateUrl: 'partials/common/locationDetails.html',
       	  controller: function($stateParams, $scope, Location) {
       		  $scope.location = {};
              $scope.location.id = $stateParams.id;
              $scope.location = Location.getLocationById($stateParams.id);
                
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
   
   .state('app.moderator.locationDetails', {
     title: 'Szczegóły lokacji',
     abstract: false,
     url: '/locations/:id/details',
     views: {
         'content@': {
       	  templateUrl: 'partials/common/locationDetails.html',
       	  controller: function($stateParams, $scope, Location) {
       		  $scope.location = {};
              $scope.location.id = $stateParams.id;
              $scope.location = Location.getLocationById($stateParams.id);
                
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
}
           
]);

//KONTROLERY

//Kontroler lokacji
Locations.controller('LocationsController', ['$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 'Event','Location', 'Context', 'Restangular',
                                     function($scope, $state, $stateParams, $filter, ngTableParams, User, Person, Event, Location, Context, Restangular) {
	
  $scope.all = Context.all;
  $scope.active = Context.active;
  $scope.activate = Context.activate;
  
  //Zmienna globalna zawierająca listę lokacji
  var data = $scope.all.locations;
  
  //Mapowania zasobów dla REST API
  var AllLocations = Restangular.all('locations');
  var OneLocation = Restangular.one('locations');
  
  //Funkcja dodająca lokację
  $scope.addLocation = function(location) {
  
	  		AllLocations.post($scope.location).then(function(response) {	  
	  			 return  Location.loadLocationsFromJson()
		 	  	    .then(function(newlyLoadedLocations){
		 	  	    	Context.all.locations = Location.getAllLocations();
		 	  	    });
			  }, function(error) {
				  $scope.error = error.data; 					
			  });		  
  }
  
  //Funkcja aktualizując lokację
  $scope.editLocation = function(location) {
	  
		OneLocation.id = $scope.location.id;
		OneLocation.name = $scope.location.name;
		OneLocation.type = $scope.location.type;
		OneLocation.address = $scope.location.address;
		OneLocation.city = $scope.location.city;
		OneLocation.zipCode = $scope.location.zipCode;
		OneLocation.state = $scope.location.state;
		OneLocation.country = $scope.location.country;
		OneLocation.phoneNumber = $scope.location.phoneNumber;
		OneLocation.email = $scope.location.email ;

	 
		OneLocation.put().then(function(response) {
			Location.loadLocationsFromJson();
		  }, function(error) {
			  $scope.error = error.data; 					
		  });		  
  }
  
  //Funkcja usuwajaca lokację
  $scope.deleteLocation = function(id) {
	    
	  OneLocation.id = id;
	  
	  OneLocation.remove().then(function(response){
		  $scope.messageStyle = "alert alert-success";
		  $scope.hideMessage = false;
		  $scope.message = "Lokacja została usunięta";
		  
		  for(var i = data.length - 1; i >= 0; i--) {
			    if(data[i].id == id) {
			       data.splice(i, 1);
			    }
			}
		  
		  $scope.tableParams.reload();
	  });
  }
  
  //Potwierdzenie usuniecia lokacji
  $scope.cid = 0;
  
	$scope.getConfirmDeleteId = function(id) {

		$scope.cid = id;
	}
  
  //Funkcja obsługująca tabele z lokacjami
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
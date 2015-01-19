var Locations = angular.module('inspigen.locations', ['ui.router', 'restangular','ngTable'])

.config(['$stateProvider', function ($stateProvider) {
	
	$stateProvider
	
	.state('app.locations', {
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
		   
		 .state('app.locations.add', {
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
	   
	   .state('app.locations.edit', {
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
	   
	   .state('app.locations.details', {
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
}
           
]);

//KONTROLERY

Locations.controller('LocationsController', ['$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 'Event', 'Participant','Location', 'Context', 'Restangular',
                                     function($scope, $state, $stateParams, $filter, ngTableParams, User, Person, Event, Participant, Location, Context, Restangular) {
	
  $scope.all = Context.all;
  $scope.active = Context.active;
  $scope.activate = Context.activate;
  
  var data = $scope.all.locations;
  
  var AllLocations = Restangular.all('locations');
  var OneLocation = Restangular.one('locations');
  

  $scope.addLocation = function(location) {
  
	  		AllLocations.post($scope.location).then(function(response) {
	  			  Location.loadLocationsFromJson();
			  }, function(error) {
				  $scope.error = error.data; 					
			  });		  
  }
  
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
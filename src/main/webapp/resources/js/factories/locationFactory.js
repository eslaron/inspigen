//Fabryka dla zasobu locations
Locations.factory('Location', function($http, $q, Restangular) {
    
	//cache dla dla lokacji
    var locationCache = {};

    function Location(json){
      this.init(json);
    }
    
    Location.prototype.init = function(json) {
      var self = this;
      angular.forEach(json,function(value,key){
        self[key] = value;
      });
      //Add the new one to our locationCache
      locationCache[self.id] = self;
    };
    
    //Pobierz lokację po id
    Location.getLocationById = function(id){
      return locationCache[id]
    }
    
    //Pobierz wszystkie lokacje
    Location.getAllLocations = function(){
    	var array = [];
        angular.forEach(locationCache, function(Location){
          array.push(Location);
        })
        return array;
    }
    
    //Załaduj lokacje do pamięci Cache
    Location.loadLocationsFromJson = function(){
    	
    	return Restangular.all('locations').getList().then(function(response){
        return response.map(function(LocationJson){
          return new Location(LocationJson)
        })
      })
    }
    
    return Location;
  });
Locations.factory('Location', function($http, $q, Restangular) {
    
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
    
    Location.getLocationById = function(id){
      return locationCache[id]
    }
    
    Location.getAllLocations = function(){
    	var array = [];
        angular.forEach(locationCache, function(Location){
          array.push(Location);
        })
        return array;
    }
    
    Location.loadLocationsFromJson = function(){
    	
    	return Restangular.all('locations').getList().then(function(response){
        return response.map(function(LocationJson){
          return new Location(LocationJson)
        })
      })
    }
    
    return Location; //returns the constructor function - that's what will be called when we do "new Location(someJson);"
  });
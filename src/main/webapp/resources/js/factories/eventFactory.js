Events.factory('Event', function($http, $q, Restangular) {
    
    var eventCache = {};

    function Event(json){
      this.init(json);
    }
    
    Event.prototype.init = function(json) {
      var self = this;
      angular.forEach(json,function(value,key){
        self[key] = value;
      });
      //Add the new one to our EventCache
      eventCache[self.id] = self;
    };
    
    Event.getEventById = function(id){
      return eventCache[id]
    }
    
    Event.getAllEvents = function(){
    	var array = [];
        angular.forEach(eventCache, function(Event){
          array.push(Event);
        })
        return array;
    }
    
    Event.loadEventsFromJson = function(){
    	
    	return Restangular.all('events').getList().then(function(response){
        return response.map(function(EventJson){
          return new Event(EventJson)
        })
      })
    }
    
    return Event; //returns the constructor function - that's what will be called when we do "new Event(someJson);"
  });
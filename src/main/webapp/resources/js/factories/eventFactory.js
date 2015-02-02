//Fabryka dla zasobu events
Events.factory('Event', function($http, $q, Restangular) {
    
	//cache dla wydarzeń
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
    
    //Pobierz wydarzenie po id
    Event.getEventById = function(id){
      return eventCache[id]
    }
    
    //Pobierz wszystkie wydarzenia
    Event.getAllEvents = function(){
    	var array = [];
        angular.forEach(eventCache, function(Event){
          array.push(Event);
        })
        return array;
    }
    
    //Załąduj wydarzenia do pamięci Cache
    Event.loadEventsFromJson = function(){
    	
    	return Restangular.all('events').getList().then(function(response){
        return response.map(function(EventJson){
          return new Event(EventJson)
        })
      })
    }
    
    return Event;
  });
Users.factory('Person', function($http, $q, Restangular) {
    
    var personCache = {};

    function Person(json){
      this.init(json);
    }
    
    Person.prototype.init = function(json) {
      var self = this;
      angular.forEach(json,function(value,key){
        self[key] = value;
      });
      //Add the new one to our personCache
      personCache[self.id] = self;
    };
    
    Person.getUPersonById = function(id){
      return personCache[id]
    }
    
    Person.getPersonByUserId = function(id){
        return personCache[id]
      }
    
    Person.getAllPersons = function(){
    	var array = [];
        angular.forEach(personCache, function(Person){
          array.push(Person);
        })
        return array;
    }
    
    Person.loadPersonsFromJson = function(){
    	
    	return Restangular.all('persons').getList().then(function(response){
        return response.map(function(PersonJson){
          return new Person(PersonJson)
        })
      })
    }
    
    return Person; //returns the constructor function - that's what will be called when we do "new Person(someJson);"
  });
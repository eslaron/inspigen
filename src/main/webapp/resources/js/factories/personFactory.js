//Fabryka dla zasobu persons
Users.factory('Person', function($http, $q, Restangular) {
    
	//cache dla danych osobowych
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
    
    //Pobierz dane osobowe po id
    Person.getPersonById = function(id){
      return personCache[id]
    }
    
    //Pobierz wszystkie dane osobowe
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
    
    //Załąduj dane osobowe do pamięci Cache
    Person.loadPersonsFromJson = function(){
    	
    	return Restangular.all('persons').getList().then(function(response){
        return response.map(function(PersonJson){
          return new Person(PersonJson)
        })
      })
    }
    
    return Person;
  });
Schools.factory('School', function($http, $q, Restangular) {
    
    var schoolCache = {};

    function School(json){
      this.init(json);
    }
    
    School.prototype.init = function(json) {
      var self = this;
      angular.forEach(json,function(value,key){
        self[key] = value;
      });
      //Add the new one to our schoolCache
      schoolCache[self.id] = self;
    };
    
    School.getSchoolById = function(id){
      return schoolCache[id]
    }
    
    School.getAllSchools = function(){
    	var array = [];
        angular.forEach(schoolCache, function(School){
          array.push(School);
        })
        return array;
    }
    
    School.loadSchoolsFromJson = function(){
    	
    	return Restangular.all('schools').getList().then(function(response){
        return response.map(function(SchoolJson){
          return new School(SchoolJson)
        })
      })
    }
    
    return School; //returns the constructor function - that's what will be called when we do "new School(someJson);"
  });
Users.factory('User', function($http, $q, Restangular) {
    
    var userCache = {};

    function User(json){
      this.init(json);
    }
    
    User.prototype.init = function(json) {
      var self = this;
      angular.forEach(json,function(value,key){
        self[key] = value;
      });
      //Add the new one to our userCache
      userCache[self.id] = self;
    };
    
    User.getUserById = function(id){
      return userCache[id]
    }
    
    User.getAllUsers = function(){
    	var array = [];
        angular.forEach(userCache, function(User){
          array.push(User);
        })
        return array;
    }
    
    User.loadUsersFromJson = function(){
    	
    	return Restangular.all('users').getList().then(function(response){
        return response.map(function(UserJson){
          return new User(UserJson)
        })
      })
    }
    
    return User; //returns the constructor function - that's what will be called when we do "new User(someJson);"
  });
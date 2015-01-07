Users.factory('User', function($http, $q, $rootScope, Restangular) {
    
    var userCache = {};
    
    var participating = false;
    
    var inEventWithId = 0;

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
    
    User.getLoggedUserByUsername = function(username){
    	var users = User.getAllUsers();
    	var loggedUser = {};
    	
    	 for(var i = users.length - 1; i >= 0; i--) {	
			  if(users[i].username == username) {
				  loggedUser = users[i];
			  }
    	 }
        return loggedUser;
    }
    
    User.getLoggedUserIsParticipating = function(){
    	return participating;
    }
    
    User.setLoggedUserIsParticipating = function(bool){
    	participating = bool;
    	return participating;
    }
    
    User.getInEventWithId = function(){
    	return inEventWithId;
    }
    
    User.setInEventWithId = function(id){
    	inEventWithId = id;
    	return inEventWithId;
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
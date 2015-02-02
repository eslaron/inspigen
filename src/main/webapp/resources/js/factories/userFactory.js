//Fabryka dla zasobu users
Users.factory('User', function($http, $q, $rootScope, Restangular) {
    
	//cache dla użytkowników
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
    
    //Pobierz użytkownika po id
    User.getUserById = function(id){
      return userCache[id]
    }
    
    //Pobierz wszystkich użytkownikow
    User.getAllUsers = function(){
    	var array = [];
        angular.forEach(userCache, function(User){
          array.push(User);
        })
        return array;
    }
    
    //Pobieranie zalogowanego użytkownika na podstawie jego nazwy
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
    
    //Getter dla statusu uczestnictwa zalogowanego użytkownika
    User.getLoggedUserIsParticipating = function(){
    	return participating;
    }
    
    //Setter dla statusu uczestnictwa zalogowanego użytkownika
    User.setLoggedUserIsParticipating = function(bool){
    	participating = bool;
    	return participating;
    }
    
    //Getter dla statusu w wydarzeniu z id
    User.getInEventWithId = function(){
    	return inEventWithId;
    }
    
    //Getter dla statusu w wydarzeniu z id
    User.setInEventWithId = function(id){
    	inEventWithId = id;
    	return inEventWithId;
    }
    
    //Załąduj użytkownikow do pamięci Cache
    User.loadUsersFromJson = function(){
    	
    	return Restangular.all('users').getList().then(function(response){
        return response.map(function(UserJson){
          return new User(UserJson)
        })
      })
    }
    
    return User;
  });
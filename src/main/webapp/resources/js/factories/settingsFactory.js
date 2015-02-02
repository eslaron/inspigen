//Fabryka dla zasobu settings
Users.factory('Settings', function($http, $q, Restangular) {
    
	//cache dla ustawień
    var settingsCache = {};

    function Settings(json){
      this.init(json);
    }
    
    Settings.prototype.init = function(json) {
      var self = this;
      angular.forEach(json,function(value,key){
        self[key] = value;
      });
      settingsCache[self.id] = self;
    };
    
    //Pobierz ustawienia po id
    Settings.getSettingsById = function(id){
      return settingsCache[id]
    }
     
    //Pobierz wszystkie ustawienia
    Settings.getAllSettings = function(){
    	var array = [];
        angular.forEach(settingsCache, function(Settings){
          array.push(Settings);
        })
        return array;
    }
    
    //Załąduj ustawienia do pamięci Cache
    Settings.loadSettingsFromJson = function(){
    	
    	return Restangular.all('settings').getList().then(function(response){
            return response.map(function(SettingsJson){
              return new Settings(SettingsJson)
         })
       })
    }
    
    return Settings;
  });
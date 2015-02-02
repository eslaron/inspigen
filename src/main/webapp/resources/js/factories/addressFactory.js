//Fabryka dla zasobu addresses
Addresses.factory('Address', function($http, $q, Restangular) {
    
	//cache dla adresów
    var addressCache = {};

    function Address(json){
      this.init(json);
    }
    
    Address.prototype.init = function(json) {
      var self = this;
      angular.forEach(json,function(value,key){
        self[key] = value;
      });
      
      addressCache[self.id] = self;
    };
    
    //Pobierz adres po id
    Address.getAddressById = function(id){
      return addressCache[id]
    }
    
    //Pobierz wszystkie adresy
    Address.getAllAddresses = function(){
    	var array = [];
        angular.forEach(addressCache, function(Address){
          array.push(Address);
        })
        return array;
    }
    
    //Załaduj adresy do pamięci Cache
    Address.loadAddressesFromJson = function(){
    	
    	return Restangular.all('addresses').getList().then(function(response){
        return response.map(function(AddressJson){
          return new Address(AddressJson)
        })
      })
    }
    
    return Address; 
  });
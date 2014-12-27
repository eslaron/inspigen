Addresses.factory('Address', function($http, $q, Restangular) {
    
    var addressCache = {};

    function Address(json){
      this.init(json);
    }
    
    Address.prototype.init = function(json) {
      var self = this;
      angular.forEach(json,function(value,key){
        self[key] = value;
      });
      //Add the new one to our addressCache
      addressCache[self.id] = self;
    };
    
    Address.getAddressById = function(id){
      return addressCache[id]
    }
    
    Address.getAllAddresses = function(){
    	var array = [];
        angular.forEach(addressCache, function(Address){
          array.push(Address);
        })
        return array;
    }
    
    Address.loadAddressesFromJson = function(){
    	
    	return Restangular.all('addresses').getList().then(function(response){
        return response.map(function(AddressJson){
          return new Address(AddressJson)
        })
      })
    }
    
    return Address; //returns the constructor function - that's what will be called when we do "new Address(someJson);"
  });
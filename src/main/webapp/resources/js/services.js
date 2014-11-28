angular.module('inspigen.services', ['ngResource'])

.factory('LoginService', function($resource, Restangular) {

	return $resource(':action', {},
			{
				authenticate: {
					method: 'POST',
					params: {'action' : 'authenticate'},
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				}
			}
		);
})

.factory('utils', function () {
  return {
    // Util for finding an object by its 'id' property among an array
    findById: function findById(a, id) {
      for (var i = 0; i < a.length; i++) {
        if (a[i].id == id) return a[i];
      }
      return null;
    },

    // Util for returning a random key from a collection that also isn't the current key
    newRandomKey: function newRandomKey(coll, key, currentKey){
      var randKey;
      do {
        randKey = coll[Math.floor(coll.length * Math.random())][key];
      } while (randKey == currentKey);
      return randKey;
    }
  };
})

.factory('users', ['utils', 'Restangular', function (utils, Restangular, $scope) {
  
	$scope.Users = RestangularProvider.all('users'); 

	var users = $scope.Users.getList()
	.then(function(response) {
		return response.data.users;
	});
	
	var factory = {};
	  factory.all = function () {
	    return users;
	};
	  
	factory.get = function (id) {
	  return users.then(function(){
	      return utils.findById(users, id);
	    })
	};
  return factory;
}]);


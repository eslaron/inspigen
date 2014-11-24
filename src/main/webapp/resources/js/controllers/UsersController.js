App.controller('UsersController', function($scope, $state, $stateParams, Restangular, $filter, ngTableParams) {
	
	$scope.limit = 10;
	$scope.entitySize = 0;
	$scope.currentPage = 1;
	$scope.selectedPage = 0;
	
	$scope.User = Restangular.all('users');            
	$scope.UserPage = Restangular.one('users?page='+$scope.currentPage+'&size='+$scope.limit);
	$scope.UserFirstPage = Restangular.one('users/firstPage?size='+$scope.limit);
	$scope.UserLastPage = Restangular.one('users/lastPage?size='+$scope.limit);
	$scope.UserEntitySize = Restangular.one('users/entitySize');
	
	$scope.userList = [{id:"", username:"", password:"", email:"", role: "", enabled:"", accountNonLocked:"", accountNonExpired:"", credentialsNonExpired:"",
		passwordToken:"", activationToken:"", passwordTokenExpiration:"", activationTokenExpiration:"", failedLogins: "", lastLoginAttempt:""}];
	
	$scope.pageCount = function() {
		return  Math.round(($scope.entitySize / $scope.limit))+1;
	}
	
	$scope.UserEntitySize.get().
	then(function(response){
		$scope.entitySize = response;
			$scope.UserFirstPage.getList()
			.then(function(response) {
				$scope.userList = response;
			});
	});
	
	$scope.tableParams = new ngTableParams({ 
		count: 0, // hides pager
        sorting: {
            name: 'desc'     
        }
    }, {
    	counts: [],
    	 
        getData: function($defer, params) {
        	
        var orderedData = params.sorting() ? $scope.userList = $filter('orderBy')($scope.userList, params.orderBy()) :  $scope.userList;
        
        $defer.resolve(orderedData);
        }
    });	
	
	$scope.getPage = function() {
		$scope.currentPage = $scope.selectedPage;
		Restangular.one('users?page='+$scope.currentPage+'&size='+$scope.limit).getList()
		.then(function(response) {
			$scope.userList = response;
	});			
			$scope.tableParams = new ngTableParams({ 
				count: 0, // hides pager
		        sorting: {
		            name: 'desc'     
		        }
		    }, {
		    	counts: [],
		    	 
		        getData: function($defer, params) {
		        	
		        var orderedData = params.sorting() ? $scope.userList = $filter('orderBy')($scope.userList, params.orderBy()) :  $scope.userList;
		        
		        $defer.resolve(orderedData);
		        }
		    });		
	};
	
	$scope.hideEditPanel = true;
    $scope.editRow =  function(id,hide) {   	
    	$scope.hideEditPanel = hide;
    	
    } 

    $scope.isCollapsed = true;
    
});
App.controller('IndexController', function($scope, $state, $stateParams) {
	
	$scope.tabs = [
	               { title:'Użytkownicy', content:'Dynamic content 1' },
	               { title:'Ustawienia', content:'Dynamic content 2', disabled: true }
	             ];

});
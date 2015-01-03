var Persons = angular.module('inspigen.persons', ['ui.router', 'restangular','ngTable'])

.config(['$stateProvider', function ($stateProvider) {
	
	$stateProvider
	
	.state('user.admin.persons', {
		     title: 'Dane osobowe',
		     abstract: false,
		     url: '/persons',
		     views: {
		         'navbar@': {
		       	  templateUrl: 'partials/admin/navbar.html' 
		         },
		         'sidebar@': {
		       	  templateUrl: 'partials/admin/sidebar.html'
		         },
		         'content@': {
		       	  templateUrl: 'partials/common/persons.html',
		       	  controller: 'PersonsController'      
		         }
		       },
		       data: {
		           permissions: {
		             only: ['admin']
		           }
		       },
		       resolve: {

		   	   }
		   }) 
		   
		 .state('user.admin.persons.add', {
	     title: 'Dodaj dane osobowe',
	     abstract: false,
	     url: '/add',
	     views: {
	         'navbar@': {
	       	  templateUrl: 'partials/admin/navbar.html' 
	         },
	         'sidebar@': {
	       	  templateUrl: 'partials/admin/sidebar.html'
	         },
	         'content@': {
	       	  templateUrl: 'partials/common/addPerson.html',
	       	  controller: 'PersonsController'      
	         }
	       },
	       data: {
	           permissions: {
	             only: ['admin']
	           }
	       }
	   })
	   
	    .state('user.admin.persons.edit', {
	     title: 'Edytuj dane osobowe',
	     abstract: false,
	     url: '/:id/edit',
	     views: {
	         'navbar@': {
	       	  templateUrl: 'partials/admin/navbar.html' 
	         },
	         'sidebar@': {
	       	  templateUrl: 'partials/admin/sidebar.html'
	         },
	         'content@': {
	       	  templateUrl: 'partials/common/editPerson.html',
	       	  controller: function($stateParams, $scope, Person) {
	              $scope.person.id = $stateParams.id;
	              $scope.person = Person.getPersonById($stateParams.id);
	              $scope.isCollapsed = true;
	          }    
	         }
	       },
	       data: {
	           permissions: {
	             only: ['admin']
	           }
	       }
	   })
}
           
]);

//KONTROLERY

Persons.controller('PersonsController', ['$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 'Event', 'Participant','Location', 'Context', 'Restangular', 
                                     function($scope, $state, $stateParams, $filter, ngTableParams, User, Person, Event, Participant, Location, Context, Restangular) {
	
  $scope.all = Context.all;
  $scope.active = Context.active;
  $scope.activate = Context.activate;

 
}]);

Persons.controller('AttachmentController', ['$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 'Event', 
                                            'Participant','Location', 'Context', 'Restangular', '$http',
                                         function($scope, $state, $stateParams, $filter, ngTableParams, User, Person, Event, 
                                        		 Participant, Location, Context, Restangular, $http) {
      $scope.all = Context.all;
      $scope.active = Context.active;
      $scope.activate = Context.activate;

      $scope.Attachment = {fileName:"", fileType: "", file:""};
      
      $scope.addAttachment = function(data) {
    	  
 	  	 $scope.hideMessage = true;

 			  var Add = Restangular.all('attachments');
 			  var AddFileInfo = Restangular.all('attachments/fileInfo');

 			 AddFileInfo.post($scope.Attachment).then(function(response){
 
 				 Add.post($scope.data).then(function(response){
 					 
 				 },function(error) {
 	 				  $scope.error = error.data; 					
 	 			 });
 								  		  
 			  }, function(error) {
 				  $scope.error = error.data; 					
 			  });
 		  }
      
      function handleFileSelect(evt) {
    	  
    	    var files = evt.target.files; // FileList object

    	    // files is a FileList of File objects. List some properties.
    	    var output = [];
    	    for (var i = 0, f; f = files[i]; i++) {
    	    	
    	    	 $scope.Attachment.fileName = f.name;
     			 $scope.Attachment.fileType = f.type;

    	    output.push('<li><strong>', escape(f.name), '</strong> (', f.type || 'n/a', ') - ',
    	                  f.size, ' bytes</li>');
    	    }
    	    document.getElementById('list').innerHTML = '<ul>' + output.join('') + '</ul>';
      }

    	  	document.getElementById('files').addEventListener('change', handleFileSelect, false);

}]);
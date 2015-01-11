var Attachments = angular.module('inspigen.attachments', ['ui.router', 'restangular','ngTable'])

.config(['$stateProvider', function ($stateProvider) {
	
	$stateProvider
	
	.state('user.admin.attachments', {
        url: '/attachments',
   	 abstract: true,
   	 template: '<div ui-view></div>', 
   	 data: {
            permissions: {
            	only: ['user','admin','moderator']
            }
        }
   })
}
           
]);

//KONTROLERY

Attachments.controller('UserAttachmentsController', ['$scope', '$state', '$stateParams', 'Restangular',
                                                   function($scope, $state, $stateParams, Restangular) {
	//Pobierz zdjecie użytkownika
	$scope.getAttachmentByUserId();	
}]);


Attachments.controller('EventAttachmentsController', ['$scope', '$state', '$stateParams', 'Restangular',
                                                     function($scope, $state, $stateParams, Restangular) {
	
	//$scope.getEventAttachmentByEventId($scope.event.id);
}]);

Attachments.controller('AttachmentsController', ['$scope', '$window','$document', '$base64', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 'Settings', 'Context', 'Restangular',
                                     function($scope, $window, $document, $base64, $state, $stateParams, $filter, ngTableParams, User, Person, Settings, Context, Restangular) {
	
	  $scope.Attachment = {fileName:"", fileType: "", file:"", user_id:"", event_id:""};
	  $scope.eventAttachments = [];
	  $scope.attachment = '';
	  $scope.noFile = '';
	  
	  var AllAttachments = Restangular.all('attachments');
	  var OneAttachment = Restangular.one('attachments');
	  var UsersAttachment = Restangular.one('attachments/user');
	  var AttachmentWithId = Restangular.one('attachments', $scope.user.id);
	  
	  	  //Dodająca nowy załącznik
		  $scope.addAttachment = function(userId,file) {

			  
			 
			  
			  if($scope.attachment == null) $scope.noFile = 'Wybierz plik';
			  
			  if($scope.attachment != null) {
				  
				  var blob = new Blob([$scope.attachment.file], {type: $scope.attachment.fileType});
	
				  $scope.Attachment.fileName = $scope.attachment.fileName;
				  $scope.Attachment.fileType = $scope.attachment.fileType;
				  $scope.Attachment.file = btoa($scope.attachment.file);
				  $scope.Attachment.blobUrl = URL.createObjectURL(blob);
				  $scope.Attachment.user_id = userId;
				  	  	
				  if($scope.event != undefined)
					  $scope.Attachment.event_id = $scope.event.id;
					  					  
				  AllAttachments.post($scope.Attachment).then(function(response){
						 
						 AttachmentWithId.get()
						  	.then(function(result) {
						  		$scope.attachment = result; 	
						  		$scope.noFile = '';
						  });
						 
					 },function(error) {
			 				  $scope.error = error.data; 					
			 		 });
			  }
		  }
		  
		  /*$scope.findEventAttachmentsInfo = function(eventId) {
			  
			  AllAttachments.getList().then(function(results) {
				  		  
				  for(var i = results.length - 1; i >= 0; i--) {	
			 		  if(results[i].event_id == eventId) {
			 			 $scope.eventAttachments.push(results[i]);
			 		  }
				  }
			  });		 
		  }*/
		  
		 //Funkcja pobierająca załącznik na podstawie id użytkownika
		 $scope.getAttachmentByUserId = function() {
				  
				  AttachmentWithId.get()
				  	.then(function(result) {
				  		$scope.attachment = result;
				  });
		 }
		  
		  $scope.getEventAttachmentByEventId = function(eventId) {
			  
			  Restangular.one('attachments/event', eventId).getList().then(function(results) {
		
					 for(var i=0; i< results.length; i++){
						 $scope.eventAttachments.push(results[i]); 
					 }
			  });		 
		  }
		 		  
		  if($scope.event != undefined) {		  
		  	  $scope.getEventAttachmentByEventId($scope.event.id);
		  }

		  
		  //Funkcja aktualizująca załącznik na podstawie id użytkownika
		  $scope.updateAttachmentByUserId = function(data) {
			  
			  if($scope.data == null) $scope.noFile = 'Wybierz plik';
			  
			  if($scope.data != null) {
	
				  OneAttachment.fileName = $scope.Attachment.fileName;
				  OneAttachment.fileType = $scope.Attachment.fileType;
				  OneAttachment.file = btoa($scope.data);
				  OneAttachment.user_id = $scope.user.id;
				  
				  OneAttachment.put().then(function(response){
						 
					  	AttachmentWithId.get()
						  	.then(function(result) {
						  		$scope.attachment = result; 
						  		$scope.noFile = '';
						  });
						 
					 },function(error) {
			 				  $scope.error = error.data; 					
			 		 });
			  }
		  }
	  
		  //Funkcja usuwająca załącznik o wybranym id
		  $scope.deleteAttachment = function(id) {
			  
			  OneAttachment.id = id;
			  
			  OneAttachment.remove().then(function(response){
				  
				  AttachmentWithId.get()
				  	.then(function(result) {
				  		$scope.attachment = result; 	
				  });
				 
			  },function(error) {
					  $scope.error = error.data; 					
			  });
		  }
	  	  
		 //Funkcja usuwająca załącznik na podstawie id użytkownika
		 $scope.deleteAttachmentByUserId = function() {
			  
			  UsersAttachment.id = $scope.user.id;
			  
			  UsersAttachment.remove().then(function(response){
				  
				  AttachmentWithId.get()
				  	.then(function(result) {
				  		$scope.attachment = result; 	
				  });
				 
			  },function(error) {
					  $scope.error = error.data; 					
			  });
		 }
}]);

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
	 $scope.attachment = '';
	//Pobierz zdjecie użytkownika
	$scope.getPhotoAttachmentByUserId();	
}]);

Attachments.controller('AttachmentsController', ['$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 'Settings', 'Context', 'Restangular',
                                     function($scope, $state, $stateParams, $filter, ngTableParams, User, Person, Settings, Context, Restangular) {
	  $scope.AddAttachment = {};
	  $scope.attachment = '';
	  $scope.userPhoto = '';
	  $scope.noFile = '';
	  $scope.eventAttachments = [];
	  $scope.loadingAttachments = false;

		  
	  var AllAttachments = Restangular.all('attachments');
	  var OneAttachment = Restangular.one('attachments');
	  var AttachmentWithId = Restangular.one('attachments', $scope.user.id);
	  var UsersAttachmentWithId = Restangular.one('attachments/user', $scope.user.id);
	  
	  	  //Dodająca nowy załącznik
		  $scope.addAttachment = function(userId,file) {
		  
			  if($scope.attachment == null) $scope.noFile = 'Wybierz plik';
			  
			  if($scope.attachment != null) {
				  
				  $scope.loadingAttachments = true;
				  
				  var blob = new Blob([$scope.attachment.file], {type: $scope.attachment.fileType});
	
				  $scope.AddAttachment.fileName = $scope.attachment.fileName;
				  $scope.AddAttachment.fileType = $scope.attachment.fileType;
				  $scope.AddAttachment.file = btoa($scope.attachment.file);
				  $scope.AddAttachment.blobUrl = URL.createObjectURL(blob);
				  $scope.AddAttachment.user_id = userId;
				  	  	
				  if($scope.event != undefined)
					  $scope.AddAttachment.event_id = $scope.event.id;
					
				  AllAttachments.post($scope.AddAttachment).then(function(response){
					  
					  $scope.noFile = '';
					
					  if($scope.event != undefined) {
						  $scope.eventAttachments = [];
						  $scope.getEventAttachmentByEventId($scope.event.id);	
						  $scope.loadingAttachments = false;
					  } 
						 
					  		  
					  if($scope.event == undefined)
						  $scope.getPhotoAttachmentByUserId();
						 
					  $scope.attachment = '';
					  
					 },function(error) {
			 				  $scope.error = error.data; 					
			 		 });
			  }
		  }
	  
		 //Funkcja pobierająca załącznik na podstawie id użytkownika przy zerowym event_id
		 $scope.getPhotoAttachmentByUserId = function() {
			 
			 UsersAttachmentWithId.get().then(function(result) {
				  		$scope.userPhoto = result;
			});
		 }
		 
		 //Funkcja pobierająca wszystkie załączniki należące do jednego wydarzenia
		 $scope.getEventAttachmentByEventId = function(eventId) {
			  
			 $scope.loadingAttachments = true;
			 
			  Restangular.one('attachments/event', eventId).getList().then(function(results) {
		
				  for(var i=0;i<results.length;i++) {					 
					  $scope.eventAttachments.push(results[i]);	
				  }
				  
				  $scope.loadingAttachments = false;
			  });		 
		 }
		 		  
		  if($scope.event != undefined) {		  
		  	  $scope.getEventAttachmentByEventId($scope.event.id);
		  }
		  	  
		  //Funkcja aktualizująca załącznik na podstawie id użytkownika
		  $scope.updateAttachmentByUserId = function(data) {
			  
			  if($scope.attachment == null) $scope.noFile = 'Wybierz plik';
			  
			  if($scope.attachment != null) {
	
				  var blob = new Blob([$scope.attachment.file], {type: $scope.attachment.fileType});
				  
				  OneAttachment.fileName = $scope.attachment.fileName;
				  OneAttachment.fileType =  $scope.attachment.fileType;
				  OneAttachment.file = btoa($scope.attachment.file);
				  OneAttachment.blobUrl =  URL.createObjectURL(blob);
				  OneAttachment.user_id = $scope.user.id;
				  
				  OneAttachment.put().then(function(response){
						 
					  $scope.getPhotoAttachmentByUserId();
						 
					 },function(error) {
			 				  $scope.error = error.data; 					
			 		 });
			  }
		  }
	  
		  //Funkcja usuwająca załącznik o wybranym id
		  $scope.deleteAttachment = function(id) {
			  
			  $scope.loadingAttachments = true;
			  
			  OneAttachment.id = id;
			  
			  OneAttachment.remove().then(function(response){
				  
				  $scope.eventAttachments = [];
				  $scope.getEventAttachmentByEventId($scope.event.id);
				  $scope.loadingAttachments = true;
			  },function(error) {
					  $scope.error = error.data; 					
			  });
		  }
	  	  
		 //Funkcja usuwająca załącznik na podstawie id użytkownika
		 $scope.deletePhotoAttachmentByUserId = function() {
			  
			 UsersAttachmentWithId.id = $scope.user.id;
			  
			 UsersAttachmentWithId.remove().then(function(response){
				 
				 $scope.getPhotoAttachmentByUserId();
				 
			  },function(error) {
					  $scope.error = error.data; 					
			  });
		 }
}]);

var Attachments = angular.module('inspigen.attachments', ['ui.router', 'restangular','ngTable'])

.config(['$stateProvider', function ($stateProvider) {
	
	
}
           
]);

//KONTROLERY

Attachments.controller('AttachmentsController', ['$scope', '$state', '$stateParams', '$filter', 'ngTableParams', 'User', 'Person', 'Settings', 'Context', 'Restangular',
                                     function($scope, $state, $stateParams, $filter, ngTableParams, User, Person, Settings, Context, Restangular) {
	
	  $scope.Attachment = {fileName:"", fileType: "", file:"", user_id:""};
	  
	  $scope.image = '';
	  
	  $scope.noFile = '';

	  var AllAttachments = Restangular.all('attachments');
	  var OneAttachment = Restangular.one('attachments');
	  var AttachmentWithId = Restangular.one('attachments', $scope.user.id);
  
	  $scope.addAttachment = function(data) {
		  
		  if($scope.data == null) $scope.noFile = 'Wybierz plik';
		  
		  if($scope.data != null) {
			  
			  $scope.Attachment.file = btoa($scope.data);
			  $scope.Attachment.user_id = $scope.user.id;
			  	  	
			  AllAttachments.post($scope.Attachment).then(function(response){
					 
					 AttachmentWithId.get()
					  	.then(function(result) {
					  		$scope.image = result; 	
					  		$scope.noFile = '';
					  });
					 
				 },function(error) {
		 				  $scope.error = error.data; 					
		 		 });
		  }
	  }
	  
	  $scope.getAttachmentByUserId = function() {
		  
		  AttachmentWithId.get()
		  	.then(function(result) {
		  		$scope.image = result;
		  });
	  }
	  
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
					  		$scope.image = result; 
					  		$scope.noFile = '';
					  });
					 
				 },function(error) {
		 				  $scope.error = error.data; 					
		 		 });
		  }
	  }
	  
	  $scope.deleteAttachmentByUserId = function() {
		  
		  OneAttachment.id = $scope.user.id;
		  
		  OneAttachment.remove().then(function(response){
			  
			  AttachmentWithId.get()
			  	.then(function(result) {
			  		$scope.image = result; 	
			  });
			 
		  },function(error) {
				  $scope.error = error.data; 					
		  });
	  }
    
	$scope.getAttachmentByUserId();

	function handleFileSelect(evt) {
		  
	    var files = evt.target.files; // FileList object

	    // files is a FileList of File objects. List some properties.
	    var output = [];
	    for (var i = 0, f; f = files[i]; i++) {
	    	
	    	 $scope.Attachment.fileName = f.name;
 			 $scope.Attachment.fileType = f.type;
 
 			 /*output.push('<li><strong>', escape(f.name), '</strong> (', f.type || 'n/a', ') - ',
	                  f.size, ' bytes</li>');*/
	    }
	    	//document.getElementById('list').innerHTML = '<ul>' + output.join('') + '</ul>';
	}
	  		document.getElementById('files').addEventListener('change', handleFileSelect, false);
}]);

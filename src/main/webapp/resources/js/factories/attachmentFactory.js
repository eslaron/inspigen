Attachments.factory('Attachment', function($http, $q, Restangular) {
    
    var attachmentCache = {};

    function Attachment(json){
      this.init(json);
    }
    
    Attachment.prototype.init = function(json) {
      var self = this;
      angular.forEach(json,function(value,key){
        self[key] = value;
      });
      //Add the new one to our attachmentCache
      attachmentCache[self.id] = self;
    };
    
    Attachment.getAttachmentById = function(id){
      return attachmentCache[id]
    }
    
    Attachment.getAllAttachments = function(){
    	var array = [];
        angular.forEach(attachmentCache, function(Attachment){
          array.push(Attachment);
        })
        return array;
    }
    
    Attachment.loadAttachmentByIdFromJson = function(id){
    	
    	alert("It was resolved");
    	/*return Restangular.all('attachments/user', id).get().then(function(response){
        return response.map(function(AttachmentJson){
          return new Attachment(AttachmentJson)
        })
      })*/
    }
    return Attachment; //returns the constructor function - that's what will be called when we do "new Attachment(someJson);"
  });
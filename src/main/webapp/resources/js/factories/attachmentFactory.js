//Fabryka dla zasobu attachments
Attachments.factory('Attachment', function($http, $q, Restangular) {
    
	//cache dla załączników
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
    
    //Pobierz załącznik po id
    Attachment.getAttachmentById = function(id){
      return attachmentCache[id]
    }
    
    //Pobierz wszystkie załączniki
    Attachment.getAllAttachments = function(){
    	var array = [];
        angular.forEach(attachmentCache, function(Attachment){
          array.push(Attachment);
        })
        return array;
    }
    
    //Załąduj załączniki do pamięci Cache
    Attachment.loadAttachmentByIdFromJson = function(id){
    	
    }
    return Attachment;
  });
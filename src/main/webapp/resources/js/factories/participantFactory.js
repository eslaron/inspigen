//Fabryka dla zasobu participants
Participants.factory('Participant', function($http, $q, Restangular) {
    
	//cache dla adresów dla uczestników
    var participantCache = {};

    function Participant(json){
      this.init(json);
    }
    
    Participant.prototype.init = function(json) {
      var self = this;
      angular.forEach(json,function(value,key){
        self[key] = value;
      });
      
      participantCache[self.id] = self;
    };
    
    //Pobierz uczestnika po id
    Participant.getParticipantById = function(id){
      return participantCache[id]
    }
    
    //Pobierz wszystkich uczestników
    Participant.getAllParticipants = function(){
    	var array = [];
        angular.forEach(participantCache, function(Participant){
          array.push(Participant);
        })
        return array;
    }
    
    //Załąduj uczestników do pamięci Cache
    Participant.loadParticipantsFromJson = function(){
    	
    	return Restangular.all('participants').getList().then(function(response){
        return response.map(function(ParticipantJson){
          return new Participant(ParticipantJson)
        })
      })
    }
    
    return Participant;
  });
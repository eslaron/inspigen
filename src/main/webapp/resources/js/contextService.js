Users.service('Context', function($q, User, Person, Address, Event, Participant, Location, Attachment) {
	
//Serwis pozwalający ustalać i nadawać kontekst obiektów z kolekcji pobieranych z REST API
	
  var context = this;
  
  //Aktywny
  this.active = {
    'user' :{},
    'person': {},
    'address': {},
    'event': {},
    'participant': {},
    'location': {},
    'attachment': {}
  };
  
  //Wszystkie
  this.all = {
    'user': [],
  	'person': [],
  	'address': [],
  	'event': [],
  	'participant': [],
  	'location': [],
  	'attachment': []
  };

  //Aktywuj
  this.activate = {
    'user' : function(user){
      context.active.user = user;
      return user;
    },
    'person' : function(person){
        context.active.person = person;
        return person;
    },
    'address' : function(address){
        context.active.address = address;
        return address;
    },
    'event' : function(event){
        context.active.event = event;
        return event;
    },
    'participant' : function(participant){
        context.active.participant = participant;
        return participant;
    },
    'location' : function(location){
        context.active.location = location;
        return location;
    },
    'attachment' : function(attachments){
        context.active.attachments = attachments;
        return attachments;
     }
  }
});
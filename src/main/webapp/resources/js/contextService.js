Users.service('Context', function($q, User, Person, Event, Participant, Location, Settings) {
	
  var context = this;
  
  this.active = {
    'user' :{},
    'person': {},
    'event': {},
    'participant': {},
    'location': {},
    'settings': {}
  };
  this.all = {
    'user': [],
  	'person': [],
  	'event': [],
  	'participant': [],
  	'location': [],
  	'settings': []
  };

  this.activate = {
    'user' : function(user){
      context.active.user = user;
      return user;
    },
    'person' : function(person){
        context.active.person = person;
        return person;
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
    'settings' : function(settings){
        context.active.settings = settings;
        return settings;
      }
  }
});
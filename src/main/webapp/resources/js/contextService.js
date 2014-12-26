Users.service('Context', function($q, User, Person, Event, Participant, School, Settings) {
	
  var context = this;
  
  this.active = {
    'user' :{},
    'person': {},
    'event': {},
    'participant': {},
    'school': {},
    'settings': {}
  };
  this.all = {
    'user': [],
  	'person': [],
  	'event': [],
  	'participant': [],
  	'school': [],
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
    'school' : function(school){
        context.active.school = school;
        return school;
    },
    'settings' : function(settings){
        context.active.settings = settings;
        return settings;
      }
  }
});
Users.service('Context', function($q, User, Person, Settings) {
  var context = this;
  
  this.active = {
    'user' :{},
    'person': {},
    'settings': {}
  };
  this.all = {
    'user': [],
  	'person': [],
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
    'settings' : function(settings){
        context.active.settings = settings;
        return settings;
      }
  }
});
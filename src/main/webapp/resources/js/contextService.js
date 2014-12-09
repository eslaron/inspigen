Users.service('Context', function($q, User, Person) {
  var context = this;
  
  this.active = {
    'user' :{},
    'person': {}
  };
  this.all = {
    'user': [],
  	'person': []
  };

  this.activate = {
    'user' : function(user){
      context.active.user = user;
      return user;
    },
    'person' : function(person){
        context.active.person = person;
        return person;
      }
  }
});
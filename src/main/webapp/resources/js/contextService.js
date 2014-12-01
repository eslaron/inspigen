Users.service('Context', function($q, User) {
  var context = this;
  
  this.active = {
    'user' :{}
  };
  this.all = {
    'user': []
  };

  this.activate = {
    'user' : function(user){
      context.active.user = user;
      return user;
    }
  }

});
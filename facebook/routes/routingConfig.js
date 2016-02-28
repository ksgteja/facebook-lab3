var soap = require('soap');
var baseURL = "http://localhost:8080/faceboook-ws/services";

function facebookRouteConfig(app) {
    
    this.app = app;
    this.routeTable = [];
    this.init();
}


facebookRouteConfig.prototype.init = function () {
    
    var self = this;
    
    this.addRoutes();
    this.processRoutes();


}


facebookRouteConfig.prototype.processRoutes = function () {
    
    var self = this;
    
    self.routeTable.forEach(function (route) {
        
        if (route.requestType == 'get') {
            
         
            self.app.get(route.requestUrl, route.callbackFunction);
        }
        else if (route.requestType == 'post') {
            
            
            self.app.post(route.requestUrl, route.callbackFunction);
        }
        
        
        else if (route.requestType == 'put'){
            
            self.app.put(route.requestUrl, route.callbackFunction);
            
        }
        
        
    
    });
}


facebookRouteConfig.prototype.addRoutes = function () {
    
    var self = this;
    
    self.routeTable.push({
        
        requestType : 'get',
        requestUrl : '/',
        callbackFunction : function (request, response) {
            
            response.render('index.ejs');
        }
    });
    
self.routeTable.push({
        
        requestType : 'get',
        requestUrl : '/homepage',
        callbackFunction : function (request, response) {
            
        	console.log(request.session.email);
        	if(request.session.email)
        	{
        		response.header('Cache-Control', 'no-cache, private, no-store, must-revalidate, max-stale=0, post-check=0, pre-check=0');
        		response.render('homePage.ejs',{username: request.session.name});
        	}
        	else
        	{
        		
        		response.redirect('/');
        	}
        }
    });

    self.routeTable.push({
        
        requestType : 'post',
        requestUrl : '/authenticate',
        callbackFunction : function (request, response) {
        	
        	
        	var option = {
        			ignoredNamespaces : true	
        		};
        	 var url = baseURL+"/User?wsdl";
        	  var args = {email : request.body.credentials.email,
  			 		password : request.body.credentials.password};
        	  soap.createClient(url,option, function(err, client) {
        	      client.validate(args, function(err, result) {
        	    	  result = JSON.parse(result.validateReturn);
        	    	  console.log(result);
        	    	  console.log(result.status);
        	    	  console.log(result.email);
        	    	  console.log(result.first_name);
        	    	  if(result.status === 200){
        	    		  	request.session.email = result.email;
           					request.session.userid = Number(result.userId);
           					request.session.name = result.first_name;
           					response.send({statusCode:200});
        	    	  }
        	    	  else{
        	    		  	response.send({statusCode:401});
        	    	  }
        	      });
        	  });
        }
        	 
       
    });
    
    
    self.routeTable.push({
	     
	     requestType : 'get',
	     requestUrl : '/getGroupsById',
	     callbackFunction : function (request, response) {
	    	 
	    	 msg_payload = {id : request.session.userid, handlerType : "getGroups"};
 	  
 		 
 		 mq_client.make_request('groups_queue',msg_payload, function(err, data){
 			 
 			 response.send(data);
 			 
 		 });
	     }
	 });

    
    self.routeTable.push({
	     
	     requestType : 'get',
	     requestUrl : '/getnewsFeedData',
	     callbackFunction : function (request, response) {
	    	 
	    	 var option = {
	        			ignoredNamespaces : true	
	        		};
	        	 var url = baseURL+"/User?wsdl";
	        	  var args = {userId : request.session.userid, userName : request.session.name};
	        	  soap.createClient(url,option, function(err, client) {
	        	      client.getnewsFeedData(args, function(err, result) {
	        	    	  result = JSON.parse(result.getnewsFeedDataReturn);
	        	    
	        	    	  if(result.status === 200){
	        	    		  	response.send({statusCode : 200, works : result.about});
	        	    	  }
	        	    	  else{
	        	    		  	response.send({statusCode:404, message : result.message});
	        	    	  }
	        	      });
	        	  });
	    	
	     }
	 });
    
    

    self.routeTable.push({
	     
	     requestType : 'get',
	     requestUrl : '/getWorkInfo',
	     callbackFunction : function (request, response) {
	    	 
	    	 var option = {
	        			ignoredNamespaces : true	
	        		};
	        	 var url = baseURL+"/User?wsdl";
	        	  var args = {userId : request.session.userid};
	        	  soap.createClient(url,option, function(err, client) {
	        	      client.getWorkInfo(args, function(err, result) {
	        	    	  result = JSON.parse(result.getWorkInfoReturn);
	        	    
	        	    	  if(result.status === 200){
	        	    		  	response.send({statusCode : 200, works : result.about});
	        	    	  }
	        	    	  else{
	        	    		  	response.send({statusCode:404, message : result.message});
	        	    	  }
	        	      });
	        	  });
	    	 
	     }
	 }); 
    
    self.routeTable.push({
	     
	     requestType : 'get',
	     requestUrl : '/getContactInfo',
	     callbackFunction : function (request, response) {
	    	 
	    	 var option = {
	        			ignoredNamespaces : true	
	        		};
	        	 var url = baseURL+"/User?wsdl";
	        	  var args = {userId : request.session.userid};
	        	  soap.createClient(url,option, function(err, client) {
	        	      client.getContactInfo(args, function(err, result) {
	        	    	  result = JSON.parse(result.getContactInfoReturn);
	        	    	  console.log(result);
	        	    	  console.log(result.status);
	        	    	  console.log(result.email);
	        	    	  console.log(result.first_name);
	        	    	  if(result.status === 200){
	        	    		  	request.session.email = result.email;
	           					request.session.userid = Number(result.userId);
	           					request.session.name = result.first_name;
	           					response.send({statusCode:200});
	        	    	  }
	        	    	  else{
	        	    		  	response.send({statusCode:401});
	        	    	  }
	        	      });
	        	  });
	     }
	 }); 
    
    
 self.routeTable.push({ 
        
        requestType : 'post',
        requestUrl : '/signup',
        callbackFunction : function (request, response) {
        	
        	dob = request.body.dob.slice(0,10);
        	firstName = request.body.firstName;
        	lastName = request.body.lastName;
        	email = request.body.email;
        	password = request.body.password;
        	gender = request.body.gender;
        	
        	var option = {
        			ignoredNamespaces : true	
        		};
        	 var url = baseURL+"/User?wsdl";
        	  var args = {firstName : firstName, lastName : lastName, email : email, password : password, gender : gender, dob : dob};
        	  soap.createClient(url,option, function(err, client) {
        	      client.signup(args, function(err, result) {
        	    	  result = JSON.parse(result.signupReturn);
        	    	  console.log("Get Signup");
        	    	  console.log(result.status); 
        	    	  if(result.status == 200)
        	    		  response.send({statusCode : 200, message : "Successfully Signed Up"});
        	    	  else
        	    		  response.send({statusCode : 404, message : result.message});
        	      });
        	  });
        	
        	
        }
    });
  
 self.routeTable.push({
     
     requestType : 'get',
     requestUrl : '/partials/:name',
     callbackFunction : function (request, response) {
    	 var name = request.params.name;
         response.render('partials/'+name);
     }
 });
 

self.routeTable.push({
    
    requestType : 'get',
    requestUrl : '/getFriends',
    callbackFunction : function (request, response) {

    	
    	var option = {
    			ignoredNamespaces : true	
    		};
    	 var url = baseURL+"/User?wsdl";
    	  var args = {userId : request.session.userid};
    	  soap.createClient(url,option, function(err, client) {
    	      client.getFriends(args, function(err, result) {
    	    	  result = JSON.parse(result.getFriendsReturn);
    	    	  console.log("Get Friends");
    	    	  console.log(result.friends); 
    	    	  if(result.status == 200)
    	    		  response.send({statusCode : 200, friends : result.friends});
    	    	  else
    	    		  response.send({statusCode : 404, message : result.message});
    	      });
    	  });
        
    }
});
//changed
self.routeTable.push({
requestType : 'get',
requestUrl : '/getAddFriends',
callbackFunction : function (request, response) {
	
	var option = {
			ignoredNamespaces : true	
		};
	 var url = baseURL+"/User?wsdl";
	  var args = {userId  : request.session.userid};
	  soap.createClient(url,option, function(err, client) {
	      client.getAddFriends(args, function(err, result) {
	    	  result = JSON.parse(result.getAddFriendsReturn);
	    	  console.log("Get Add Frineds");
	    	  console.log(result.friends); 
	    	  response.send(result.friends);
	      });
	  });
	
    
} 
});


self.routeTable.push({
	requestType : 'post',
	requestUrl : '/approveRequest/:senderId',
	callbackFunction : function (request, response) {
		var senderId = request.params.senderId;
		var option = {
    			ignoredNamespaces : true	
    		};
    	 var url = baseURL+"/User?wsdl";
    	  var args = {userName : request.session.name, userId : request.session.userid, senderId : senderId};
    	  soap.createClient(url,option, function(err, client) {
    	      client.approveRequest(args, function(err, result) {
    	    	  result = JSON.parse(result.approveRequestReturn);
    	    	  console.log(result); 
    	    	  response.send(result)
    	      });
    	  });
	}
	});

self.routeTable.push({
	requestType : 'post',
	requestUrl : '/rejectRequest/:sender',
	callbackFunction : function (request, response) {
		var senderId = request.params.sender;
		var option = {
    			ignoredNamespaces : true	
    		};
    	 var url = baseURL+"/User?wsdl";
    	  var args = {userId : request.session.userid, senderId : senderId};
    	  soap.createClient(url,option, function(err, client) {
    	      client.rejectRequest(args, function(err, result) {
    	    	  result = JSON.parse(result.rejectRequestReturn);
    	    	  console.log(result); 
    	    	  response.send(result)
    	      });
    	  });
		
	    
	}
	});

self.routeTable.push({
    
    requestType : 'post',
    requestUrl : '/sendFriendRequest/:id',
    callbackFunction : function (request, response) {
    	
    	var option = {
    			ignoredNamespaces : true	
    		};
    	 var url = baseURL+"/User?wsdl";
    	  var args = {senderId : request.session.userid, recieverId : request.params.id};
    	  soap.createClient(url,option, function(err, client) {
    	      client.sendFriendRequest(args, function(err, result) {
    	    	  result = JSON.parse(result.sendFriendRequestReturn);
    	    	  
    	    	  console.log(result);
    	    	  
    	    	  
    	    	  if(result.status === 200){
    	    		  	response.send({statusCode : 200, message : result.message});
    	    	  }
    	    	  else{
    	    		  	response.send({statusCode:401, message : result.message});
    	    	  }
    	      });
    	  });
        
    }
});

self.routeTable.push({
    
    requestType : 'get',
    requestUrl : '/getFriendRequests',
    callbackFunction : function (request, response) {
    	
    	var option = {
    			ignoredNamespaces : true	
    		};
    	 var url = baseURL+"/User?wsdl";
    	  var args = {userId : request.session.userid};
    	  soap.createClient(url,option, function(err, client) {
    	      client.getFriendRequests(args, function(err, result) {
    	    	  result = JSON.parse(result.getFriendRequestsReturn);
    	    	  
    	    	  console.log(result);
    	    	  
    	    	  
    	    	  if(result.status === 200){
    	    		  	response.send(result.friends);
    	    	  }
    	    	  else{
    	    		  	response.send({statusCode:401});
    	    	  }
    	      });
    	  });
    }
});

 
 self.routeTable.push({
     
     requestType : 'get',
     requestUrl : '/logout',
     callbackFunction : function (request, response) {
    	 request.session.destroy();
         response.json({"value" : "success"});
     }
 });

    
}

module.exports = facebookRouteConfig;
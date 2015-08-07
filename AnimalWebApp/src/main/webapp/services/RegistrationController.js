var animalAppControllers = angular.module('RegistrationController', []);

animalApp.controller('RegistrationController', function($scope) {

	var root = 'http://localhost:8080/oauth/webapi/login/';
	var root2 = 'http://localhost:8080/webapi/users/user';
	
	$('#usualReg').click(function() {

		console.log("ping");
			
		var userLogin=$("#userLogin").val(); 
		var userName=$("#userName").val(); 
		var userSurname=$("#userSurname").val(); 
		var userEmail=$("#userEmail").val(); 
		var password=CryptoJS.MD5($("#password").val()); 
		var userPhone=$("#userPhone").val(); 
		var userAdress=$("#userAdress").val(); 
		var organizationName=$("#userCompany").val(); 
		var organizationInfo=$("#userCompanyInfo").val(); 
		var userRole = "user";
		
		console.log(password);		
		
		var d = new Date();
		var month = d.getMonth()+1;
		var day = d.getDate();
		var regDate = d.getFullYear() + '-' + (month<10 ? '0' : '') + month + '-' +    (day<10 ? '0' : '') + day;
		console.log(regDate);

		// var userJson = ('{"id" :' + petId +', "type" : ' + petType + ', "owner":{"name":' + petOwName + ', "id" : ' + petOwId + ', "adress" : ' + petOwAd + '}, "size" : ' + petSize + '}' );

		var userJson = ('{"active": true, "email":"' + userEmail + '", "name" : "' + userName + '", "password" : "'+ password + '", "surname" : "' +
					userSurname + '", "phone" : "'+ userPhone	+ '", "organizationName" : "'+	organizationName + '", "userAdress" : "'+ userAdress+'", "organizationInfo" : "' +
					organizationInfo +'", "userRole" : {"id": 3}, "userType" : {"id": 1},'+ ' "registrationDate": "' + regDate +'", "socialLogin": "'+ userLogin +'"}'); 
			
		console.log(userJson);
			
		

			
		$.ajax({			
			method: 'POST',
			contentType: 'application/json',
			dataType: 'json',
			url: root2,
			data: userJson			
			}).then(function(data) {
				var str = JSON.stringify(data, null, '\t');
				console.log(str);					
	        });			
	});
	
	//OAuth 	
	$('.btn-facebook').click(function() {
		console.log("ping");
	   window.location = root + "facebook";
	}); 
	
	$('.btn-google').click(function() {
		console.log("ping");
	   window.location = root + "google";
	}); 
	
	$('.btn-twitter').click(function() {
		console.log("ping");
	   window.location = root + "twitter";
	});
	
});


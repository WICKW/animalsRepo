var animalAppControllers = angular.module('UserProfileController', []);

animalApp.controller('UserProfileController', function($scope) {
	
//	$scope.IsHidden = true;
//    $scope.showPopup = function () {
//        //show div.
//        $scope.IsHidden =  false;
//    }
//    
//    $scope.closePopup = function () {
//    	 //hide div.
//        $scope.IsHidden =  true;
//    }
	
	
		$('#editProfile').click(function() {
			console.log("ping");
			$('#editUser').css("display", "block");
		});
		
		$('#cancelReg').click(function() {
			console.log("ping");
			$('#editUser').css("display", "none");
		});
		
		
		$('#updateReg').click(function() {

			console.log("ping");
			
			var userId = 25; // It's a STUB! Real user Id we should take from SESSION 
				
			var socialLogin=$("#userLogin").val(); 
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
					organizationInfo +'", "userRole" : {"id": 3}, "userType" : {"id": 1},'+ ' "registrationDate": "' + regDate +'", "socialLogin": "'+ socialLogin + '", "id": "'+ userId + '"}');  
				
			console.log(userJson);
			
			var root2 = 'http://localhost:8080/webapi/users/user';

				
				$.ajax({			
					method: 'PUT',
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
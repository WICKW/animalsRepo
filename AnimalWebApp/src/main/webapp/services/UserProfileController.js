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
				
			var userLogin=$("#userLogin").val(); 
			var userName=$("#userName").val(); 
			var userSurname=$("#userSurname").val(); 
			var userEmail=$("#userEmail").val(); 
			var password=$("#password").val(); 
			var userPhone=$("#userPhone").val(); 
			var userAdress=$("#userAdress").val(); 
			var userCompany=$("#userCompany").val(); 
			var userCompanyInfo=$("#userCompanyInfo").val(); 
			var userRole = "user";			

			// var userJson = ('{"id" :' + petId +', "type" : ' + petType + ', "owner":{"name":' + petOwName + ', "id" : ' + petOwId + ', "adress" : ' + petOwAd + '}, "size" : ' + petSize + '}' );

			var userJson = ('{"active": true, "address": {"id": 30}, "email":"' + userEmail + '", "name" : "' + userName + '", "password" : "'+ password + '", "surname" : "' +
						userSurname + '", "phone" : "'+ userPhone	+ '", "userCompany" : "'+	userCompany + '", "userAdress" : "'+ userAdress+'", "userCompanyInfo" : "' +
						userCompanyInfo+'","userRole": {"id": 3},"userType": {"id": 1},'+ '"id": "'+ userId + '"}'); 
				
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
//						str = prepareJson(str);
						console.log(str);
						// $('#result').html(str);
						// highlightResult();
		         	});			
		});
		
	
	
	
	
});
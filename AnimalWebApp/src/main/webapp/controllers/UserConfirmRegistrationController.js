//created by 41X
var animalAppControllers = angular.module('UserConfirmRegistrationController', []);

animalApp.controller('UserConfirmRegistrationController', ['$scope', '$location', '$route', 'userAccount', 'localStorageService', 
                                                           function($scope, $location, $route, userAccount, localStorageService) {
	
	if($location.search().username && $location.search().code){
		
		var userLogin = $location.search().username;		
		var code = $location.search().code;
		
		
		userAccount.confirmRegistration(userLogin,code).then(
				function(result){
					localStorageService.cookie.set("accessToken",result.accessToken,0.065); 	        	
		        	localStorageService.set("accessToken", result.accessToken);
		        	localStorageService.set("userId", result.userId);
		        	localStorageService.set("userName", result.socialLogin);
		        	localStorageService.set("userRole", result.userRole);
		        	localStorageService.set("userRoleId", result.userRoleId);
		        	
			        $location.path("/ua/user/profile");	
			        $route.reload();					
				},
				function(error){
					console.log(error);					
					$scope.errorRegistrationConfirmMessage = error;					
				}
			);
	}
	
		
        
}]);
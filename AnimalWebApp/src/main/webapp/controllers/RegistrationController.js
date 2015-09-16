//created by 41X
var animalAppControllers = angular.module('RegistrationController', []);

animalApp.controller('RegistrationController', ['$scope', '$location', '$route', 'currentDate', 'AuthenticationService',
                                                'hashPassword', 'OauthAuthenticationService',
                                               function($scope, $location, $route, currentDate, AuthenticationService,
                                            		   hashPassword, OauthAuthenticationService) {
	
		$scope.submitRegForm=function(){
			
			$scope.errorConfirmMessage=false;        		
			$scope.fields.active = false; 
			$scope.fields.userRole = [{"id":3,"role":"гість"}]; //userRole=гість
			$scope.fields.userType = {"id":1,"type":"власник"};	//userType=власник		
			
			$scope.fields.registrationDate = currentDate;			
							
			$scope.fields.password=hashPassword($scope.password); 
			AuthenticationService.registerUser($scope.fields).then(
				function(result){
					if(result.userId==0){
				        $scope.errorRegistrationMessage1=true;
				        console.log("Registration error. SocialLogin is already exist");	
					} else if (result.userId==1){	
				        $location.path("/ua/user/confirmRegistration");	
						$route.reload();						        
				    } else {
				        console.log("error");
				        $scope.errorRegistrationMessage3=true;
				    }			
				}
			);    
	        
		}; 
		
		$scope.RegisterGoogle=function(){		
						
			OauthAuthenticationService.loginGoogle();		
			
		}
		
		$scope.RegisterFacebook=function(){		
			
			OauthAuthenticationService.loginFacebook();		
			
		}
		
		$scope.RegisterTwitter=function(){		
			
			OauthAuthenticationService.loginTwitter();		
			
		}
        
}]);


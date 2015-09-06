//created by 41X
var animalAppControllers = angular.module('AnimalsDetailedUserController', []);

animalApp.controller('AnimalsDetailedUserController', ['$scope', 'userData', 'localStorageService', '$route', '$location', '$routeParams',
                                               function($scope, userData, localStorageService, $route, $location, $routeParams) {
		
	//initialize loading spinner
    var targetContent = document.getElementById('loading-block');    
    new Spinner(opts).spin(targetContent);   
    $scope.contentLoading = 0;
	
    var animalId = $routeParams.animalId;       //animal id
    console.log(animalId);
    //$scope.animal = AnimalsAdminValues.animal;  //animal
    $scope.animalImage = undefined;

	$scope.contentLoading++;
	userData.getAnimal(animalId).then(
			function(result){
				$scope.animal=result;
				$scope.animalImage = "resources/img/noimg.png";
				console.log(result.image);
                if (result.image != undefined) {
                    if (result.image.length > 0) {
                        $scope.animalImage = result.image;
                    }
                }
				$scope.contentLoading--;
			},
			function(error){				
				console.log(error)
				$scope.contentLoading--;
			}
		);

		
	
	
}]);
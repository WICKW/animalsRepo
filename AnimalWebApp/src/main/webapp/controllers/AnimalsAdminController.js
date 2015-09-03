angular.module('AnimalsAdminController', ['AnimalsAdminModule', 'nya.bootstrap.select', 'DPController', 'AnimalsAdminValues', 'LocalStorageModule'])
    .controller('AnimalsAdminController', ['$scope', '$http', 'AnimalsAdminService', 'AnimalsAdminValues', 'localStorageService',
        function($scope, $http, AnimalsAdminService, AnimalsAdminValues, localStorageService) {

            AnimalsAdminService.rolesAllowed("модератор");

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 4;

            $scope.filter = AnimalsAdminValues.filter;            //filter
            $scope.totalItems = AnimalsAdminValues.totalItems;    //table rows count
            $scope.animals = AnimalsAdminValues.animals;          //animal instance

            /**
             * @return count of rows for pagination.
             */
            AnimalsAdminService.getPagesCount()
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return list of animals.
             */
            AnimalsAdminService.getAnimals()
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return next page.
             */
            $scope.pageChanged = function() {
                AnimalsAdminService.getAnimals();
            };

            /**
             * @return list of animals with given count of rows.
             */
            $scope.countChanged = function(count) {
                $scope.filter.limit = count;
                AnimalsAdminService.getAnimals();
            };

            /**
             * @param id
             * sending message to Twitter.
             */
            $scope.sendTwitter = function (id) {
                AnimalsAdminService.sendTwitter(id);
            };

            /**
             * @param id
             * sending message to Facebook.
             */
            $scope.sendFacebook = function (id) {
                AnimalsAdminService.sendFacebook(id);
            };
    }])
    .controller('AnimalsFilterAdminController', ['$scope', '$filter', 'AnimalsAdminService', 'AnimalsAdminValues',
        function($scope, $filter, AnimalsAdminService, AnimalsAdminValues) {

            $scope.filter = AnimalsAdminValues.filter;                  //filter
            $scope.animalTypes = AnimalsAdminValues.animalTypes;        //list of animal types
            $scope.animalServices = AnimalsAdminValues.animalServices;  //list of animal services

            /**
             * @return list of animal types.
             */
            AnimalsAdminService.getAnimalTypes()
                .finally(function() {
                    $scope.$parent.contentLoading--;
                });

            /**
             * @return list of animal types.
             */
            AnimalsAdminService.getAnimalServices()
                .finally(function() {
                    $scope.$parent.contentLoading--;
                });

            /**
             * @return list of animal breeds according to animal type.
             */
            $scope.getAnimalBreeds = function() {
                $scope.filterAnimalBreedFlag = true;
                AnimalsAdminService.getAnimalBreeds($scope.filter.animal.type.id)
                    .then(function(data) {
                        $scope.animalBreeds = data;
                    })
                    .finally(function() {
                        $scope.filterAnimalBreedFlag = false;
                    });
            }

            /**
             * reset filter values.
             */
            $scope.reset = function() {
                $scope.filter.animal.transpNumber = undefined;
                $scope.filter.animal.service = undefined;
                $scope.filter.animal.type = undefined;
                $scope.filter.animal.breed = undefined;
                $scope.filter.animal.sex = undefined;
                $scope.filter.animal.dateOfRegister = undefined;
            }

            /**
             * @return list of animals according to filter values.
             */
            $scope.submit = function(isValid) {
                if(!isValid){
                    return;
                }

                $scope.filter.animal.dateOfRegister = $filter('date')($scope.filter.animal.dateOfRegister, 'yyyy-MM-dd');

                AnimalsAdminService.getPagesCount();
                AnimalsAdminService.getAnimals();
            };
    }]);
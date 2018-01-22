(function () {
  'use strict';

  angular
    .module('cdb2')
    .controller('GeoAreaDetailController', GeoAreaDetailController);

  GeoAreaDetailController.$inject = ['$log','$translate','$uibModalInstance','toastr', 'area', 'MunicipalityAPIService', 'GeoAreaAPIService', 'NgTableParams', 'lodash'];

  function GeoAreaDetailController($log,$translate,$uibModalInstance, toastr, area, MunicipalityAPIService, GeoAreaAPIService, NgTableParams, lodash) {
    var vm = this;
    vm.area = area;
    vm.citiesToDisplay = area.municipalities.slice();
    vm.tableParamsEdit = new NgTableParams({}, {dataset: vm.citiesToDisplay});
    vm.selectedForRemoval = [];
    vm.selectedForAddition = [];

    MunicipalityAPIService.getAll().$promise.then(function(success) {
      vm.municipalities = success;
      vm.tableParams = new NgTableParams({}, {dataset: success});
    });

    vm.toggleRemoval = function(cityId){
      var position = vm.selectedForRemoval.indexOf(cityId);
      if(position < 0){
        vm.selectedForRemoval.push(cityId);
      }else{
        vm.selectedForRemoval.splice(position, 1);
      }
    };

    vm.toggleAddition = function(cityId){
      var position = vm.selectedForAddition.indexOf(cityId);
      if(position < 0){
        vm.selectedForAddition.push(cityId);
      }else{
        vm.selectedForAddition.splice(position, 1);
      }
    };

    // search cities in vm.municipalities for every id
    // Add  cities found to citiesToDisplay
    // empty selectedForAddition
    vm.addSelected = function(){
      var cities = [];
      vm.selectedForAddition.forEach(function(id){
          var city = lodash.find(vm.municipalities, {'id': id});
          cities.push(city);
          vm.area.zipCodes += ', ' + city.postalCode;
      });

      addCitiesToDisplay(cities);
      vm.tableParamsEdit.reload();
      vm.selectedForAddition = [];

    };

    function addCitiesToDisplay(cities){
      cities.forEach(function(city){
        if(!lodash.find(vm.citiesToDisplay, {'id': city.id})){
          vm.citiesToDisplay.push(city);
        }
      });
    }

    vm.save = function () {
      vm.area.municipalities = vm.citiesToDisplay;
     GeoAreaAPIService
       .save(vm.area)
       .$promise
       .then(function () {
         $uibModalInstance.close();
         toastr.success($translate.instant("global.message.savesuccess"));
       })
       .catch(function (err) {
         $log.error(err);
         // vm.errorsFromServer = $translate.instant(err.data.message);
       })
       .finally(function () {
         // vm.submitted = false;
       });
   };

    vm.cancel = function(){
      $uibModalInstance.close();
    };
  }

})();

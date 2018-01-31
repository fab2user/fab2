(function () {
  'use strict';

  angular
    .module('cdb2')
    .controller('GeoAreaDetailController', GeoAreaDetailController);

  GeoAreaDetailController.$inject = ['$log','$translate','$uibModalInstance','toastr', 'area', 'MunicipalityAPIService', 'GeoAreaAPIService', 'NgTableParams', 'lodash'];

  function GeoAreaDetailController($log,$translate,$uibModalInstance, toastr, area, MunicipalityAPIService, GeoAreaAPIService, NgTableParams, lodash) {
    var vm = this;
    vm.area = area;
    vm.citiesToDisplay = area.municipalities.slice() || [];
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

    vm.toggleAddition = function(city){
      var position = vm.selectedForAddition.indexOf(city);
      if(position < 0){
        vm.selectedForAddition.push(city);
      }else{
        vm.selectedForAddition.splice(position, 1);
      }
    };

    // Add  cities found to citiesToDisplay
    // empty selectedForAddition
    // FIXME: Make sure we can't add a city already present in the area
    vm.addSelected = function(){
      addCitiesToDisplay(vm.selectedForAddition);
      vm.tableParamsEdit.reload();
      vm.selectedForAddition = [];
    };

    vm.removeSelected = function(){
      removeCitiesToDisplay(vm.selectedForRemoval);
      vm.tableParamsEdit.reload();
      vm.selectedForRemoval = [];
    };

    function addCitiesToDisplay(cities){
      cities.forEach(function(city){
        if(!lodash.find(vm.citiesToDisplay, {'id': city.id})){
          vm.citiesToDisplay.push(city);
          vm.area.zipCodes += ', ' + city.postalCode;
        }
      });
    }

    function removeCitiesToDisplay(cities){
      var zipsToRemove = lodash.map(cities, 'postalCode');
      lodash.remove(vm.citiesToDisplay, function(cityToDisplay){
        return zipsToRemove.indexOf(cityToDisplay.postalCode) > -1;
      });

      vm.area.zipCodes = filterZipList(vm.area.zipCodes, zipsToRemove);

    }

    vm.addFiltered = function(){
      // TODO: Add confirm
      $log.info('Filtered cities: ', vm.tableParams);
      vm.tableParams.data.forEach(function(city){
        vm.toggleAddition(city);
      });
      vm.addSelected();
    };

    vm.removeFiltered = function(){
      vm.tableParamsEdit.data.forEach(function(city){
        vm.toggleRemoval(city);
      });
      vm.removeSelected();
    };

    function filterZipList(zipList, zips){
      var cleanZips = lodash.map(zipList.split(','), function(zip){
        return zip.trim();
      });
      zips.forEach(function(zip){
        cleanZips = lodash.without(cleanZips, zip);
      });
      return cleanZips.join(', ');
    }

    vm.save = function (valid) {
      if (vm.citiesToDisplay.length < 1) {
        toastr.error('Error', 'At least one geo area must be selected !');
        return;
      }
      if(valid){
        vm.submitted=true;
      vm.area.municipalities = vm.citiesToDisplay;
     GeoAreaAPIService
       .save({},vm.area)
       .$promise
       .then(function () {
         $uibModalInstance.close();
         toastr.success($translate.instant("global.toastr.save.success"));
       })
       .catch(function (err) {
         $log.error(err);
         vm.errorsFromServer = $translate.instant(err.data.message);
       })
       .finally(function () {
         vm.submitted = false;
       });
      }
   };

    vm.cancel = function(){
      $uibModalInstance.close();
    };
  }

})();

(function () {
  'use strict';

  angular
    .module('cdb2')
    .controller('GeoAreaDetailController', GeoAreaDetailController);

  GeoAreaDetailController.$inject = ['$uibModalInstance', 'area', 'MunicipalityAPIService', 'NgTableParams'];

  function GeoAreaDetailController($uibModalInstance, area, MunicipalityAPIService, NgTableParams) {
    var vm = this;
    vm.area = area;
    var citiesToDisplay = area.slice();
    vm.tableParamsEdit = new NgTableParams({}, {dataset: area.municipalities});
    vm.selectedForRemoval = [];
    vm.selectedForAddition = [];

    MunicipalityAPIService.getAll().$promise.then(function(success) {
      vm.municipalities = success;
      vm.tableParams = new NgTableParams({}, {dataset: success});
    });

    vm.selectForRemoval = function(cityId){

    };

    vm.cancel = function(){
      $uibModalInstance.close();
    };
  }

})();

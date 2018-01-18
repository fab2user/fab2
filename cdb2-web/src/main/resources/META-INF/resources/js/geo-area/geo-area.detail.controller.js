(function () {
  'use strict';

  angular
    .module('cdb2')
    .controller('GeoAreaDetailController', GeoAreaDetailController);

  GeoAreaDetailController.$inject = ['$uibModalInstance', 'area', 'MunicipalityAPIService', 'NgTableParams'];

  function GeoAreaDetailController($uibModalInstance, area, MunicipalityAPIService, NgTableParams) {
    var vm = this;
    vm.area = area;

    MunicipalityAPIService.getAll().$promise.then(function(success) {
      vm.municipalities = success;
      vm.tableParams = new NgTableParams({}, {dataset: success});
    });
  }

})();

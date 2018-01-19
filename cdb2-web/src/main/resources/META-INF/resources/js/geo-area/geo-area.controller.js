(function () {
  'use strict';

  angular
    .module('cdb2')
    .controller('GeoAreaController', GeoAreaController);

  GeoAreaController.$inject = ['$uibModal', 'GeoAreaAPIService', 'NgTableParams', 'lodash'];

  function GeoAreaController($uibModal, GeoAreaAPIService, NgTableParams, lodash) {
    var vm = this;

    GeoAreaAPIService.getAll().$promise.then(function(success) {
      vm.tableParams = new NgTableParams({}, {dataset: success});
    });

    vm.edit = function(){
      loadModal(vm.selectedArea);
    };

    function loadModal(selectedArea) {
      var modalInstance = $uibModal.open({
        templateUrl: '/js/geo-area/area-edit.html',
        controller: 'GeoAreaDetailController as areaDetailCtrl',
        windowClass: 'modal-hg',
        backdrop: 'static',
        resolve: {
          area: selectedArea
        }
      });

      modalInstance.result.then(
        function () {
          // vm.tableParams.reload();
        });
    }
  }

})();

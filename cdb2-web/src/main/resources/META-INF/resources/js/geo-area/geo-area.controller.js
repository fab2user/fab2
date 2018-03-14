(function () {
  'use strict';

  angular
    .module('cdb2')
    .controller('GeoAreaController', GeoAreaController);

  GeoAreaController.$inject = ['$uibModal', '$translate', 'GeoAreaAPIService', 'NgTableParams', 'toastr'];

  function GeoAreaController($uibModal, $translate, GeoAreaAPIService, NgTableParams, toastr) {
    var vm = this;

    fetchGeoAreas();

    vm.edit = function(){
      loadModal(vm.selectedArea);
    };

    vm.new = function(){
      // We pass an empty area object
      loadModal({'municipalities':[]});
    };

    vm.remove = function(){
      GeoAreaAPIService
        .delete({id: vm.selectedArea.id})
        .$promise
        .then(function(){
          toastr.success($translate.instant('global.toastr.delete.success'));
          fetchGeoAreas();
        });
    };

    function loadModal(selectedArea) {
      var modalInstance = $uibModal.open({
        templateUrl: '/js/geo-area/area-edit.html',
        controller: 'GeoAreaDetailController as areaDetailCtrl',
        windowClass: 'modal-hg',
        backdrop: 'static',
        resolve: {
          area: selectedArea // if area is empty, we're in insertion mode
        }
      });

      modalInstance.result.then(
        function () {
          fetchGeoAreas();
          // vm.tableParams.reload();
        });
    }

    function fetchGeoAreas(){
      GeoAreaAPIService.getAll().$promise.then(function(success) {
        vm.tableParams = new NgTableParams({}, {dataset: success});
      });
    }
  }
})();

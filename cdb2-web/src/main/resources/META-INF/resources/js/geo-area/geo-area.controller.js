(function() {
  'use strict';

  angular.module('cdb2').controller('GeoAreaController', GeoAreaController);

  GeoAreaController.$inject = [
    '$uibModal',
    '$translate',
    '$rootScope',
    'GeoAreaAPIService',
    'NgTableParams',
    'toastr'
  ];

  function GeoAreaController(
    $uibModal,
    $translate,
    $rootScope,
    GeoAreaAPIService,
    NgTableParams,
    toastr
  ) {
    var vm = this;

    fetchGeoAreas();

    $rootScope.fabStatus['currentMenu'] = $translate.instant('area.list.title');
    $rootScope.helpPage['currentPage'] = 'helpGeoArea.html';
    
    vm.areaDetailsTable = new NgTableParams(
      {},
      {
        dataSet: []
      }
    );

    vm.edit = function() {
      loadModal(vm.selectedArea);
    };

    vm.new = function() {
      // We pass an empty area object
      loadModal({ municipalities: [] });
    };

    vm.remove = function() {
      GeoAreaAPIService.delete({ id: vm.selectedArea.id }).$promise.then(
        function() {
          toastr.success($translate.instant('global.toastr.delete.success'));
          fetchGeoAreas();
        }
      );
    };

    vm.selectArea = function(area) {
      vm.selectedArea = area;
      vm.areaDetailsTable.settings({ dataset: area.municipalities });
      vm.areaDetailsTable.totalDataSet = area.municipalities.length;
      vm.areaDetailsTable.tableTitle = "DETAILS OF AREA : " + area.name;
      vm.total = area.municipalities.length;
    };

    vm.resetSearch = function() {
      vm.tableParams.filter({});
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

      modalInstance.result.then(function() {
        fetchGeoAreas();
        // vm.tableParams.reload();
      });
    }

    function fetchGeoAreas() {
      GeoAreaAPIService.getAll().$promise.then(function(data) {
          vm.total = data.length;
          vm.tableParams = new NgTableParams({}, {
            dataset: data
          });
        vm.tableParams.totalDataSet = data.length;   // Add the total resultset size to the table params.
        vm.tableParams.tableTitle = 'LIST OF GEOGRAPHICAL AREAS';
      });
    }
  }
})();

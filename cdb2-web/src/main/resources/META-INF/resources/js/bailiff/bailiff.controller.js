(function() {
  'use strict';

  angular.module('cdb2').controller('BailiffController', BailiffController);

  BailiffController.$inject = [
    '$log',
    '$http',
    '$translate',
    '$uibModal',
    'NgTableParams',
    'BailiffAPIService',
    'toastr',
    'MunicipalityAPIService'
  ];

  function BailiffController($log, $http, $translate, $uibModal, NgTableParams, BailiffAPIService, toastr, MunicipalityAPIService) {
    var vm = this;
    vm.selectedBailiff = {};

    fetchBailiffs();
    vm.title = $translate.instant('bailiff.list.municipality');

    vm.new = function() {
      loadModal({});
    };

    vm.edit = function() {
      loadModal(vm.selectedBailiff);
    };

    function loadModal(bailiff) {
      var modalInstance = $uibModal.open({
        templateUrl: '/js/bailiff/bailiff.edit.html',
        controller: 'BailiffEditController as bailiffEditCtrl',
        windowClass: 'modal-hg',
        backdrop: 'static',
        resolve: {
          bailiff: bailiff,
          cities: function() {
            return MunicipalityAPIService.getAll();
          },
          competences: function() {
            return BailiffAPIService.getCompetences({id: vm.selectedBailiff.id, collectionAction: 'competences'});
          }
        }
      });

      modalInstance.result.then(function() {
        fetchBailiffs();
        // vm.tableParams.reload();
      });
    }

    function fetchBailiffs() {
      BailiffAPIService.getAll().$promise.then(function(data) {
        vm.tableParams = new NgTableParams({}, {dataset: data});
      });
    }

  }
})();

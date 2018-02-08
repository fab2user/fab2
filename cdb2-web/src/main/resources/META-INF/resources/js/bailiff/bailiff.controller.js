(function() {
  'use strict';

  angular.module('cdb2').controller('BailiffController', BailiffController);

  BailiffController.$inject = [
    '$log',
    '$translate',
    '$uibModal',
    'NgTableParams',
    'BailiffAPIService',
    'toastr',
    'MunicipalityAPIService'
  ];

  function BailiffController($log, $translate, $uibModal, NgTableParams, BailiffAPIService, toastr, MunicipalityAPIService) {
    var vm = this;
    vm.deleted = false; //Flag to indicate if we want to display also soft deleted records. Default is false.
    vm.selectedBailiff = {};

    vm.fetchBailiffs = function() {
      BailiffAPIService.getAll({deleted: vm.deleted}).$promise.then(function(data) {
        vm.tableParams = new NgTableParams({}, {dataset: data});
      });
    };


    vm.fetchBailiffs();
    vm.title = $translate.instant('bailiff.list.municipality');

    vm.new = function() {
      loadModal({});
    };

    vm.edit = function() {
      loadModal(vm.selectedBailiff);
    };

    vm.delete = function(){
      BailiffAPIService.delete({id: vm.selectedBailiff.id}).then(function(){
        toastr.success($translate.instant('global.toastr.delete.success'));
        vm.fetchBailiffs();
      });
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
            //Only when edition, not when creation
            if(bailiff.id){
              return BailiffAPIService.getCompetences({id: bailiff.id, collectionAction: 'competences'});
            }
          }
        }
      });

      modalInstance.result.then(function() {
        vm.fetchBailiffs();
      });
    }

  }
})();

(function() {
  'use strict';

  angular.module('cdb2').controller('BailiffController', BailiffController);

  BailiffController.$inject = [
    '$log',
    '$translate',
    '$uibModal',
    '$http',
    'FileSaver',
    'Blob',
    'NgTableParams',
    'BailiffAPIService',
    'toastr',
    'MunicipalityAPIService',
    'SERVER'
  ];

  function BailiffController($log, $translate, $uibModal, $http, FileSaver, Blob, NgTableParams, BailiffAPIService, toastr, MunicipalityAPIService, SERVER) {
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
      BailiffAPIService.delete({id: vm.selectedBailiff.id}).$promise.then(function(){
        toastr.success($translate.instant('global.toastr.delete.success'));
        vm.fetchBailiffs();
      });
    };

    vm.cancelUpload = function(){
      vm.importFile = null;
    };

    vm.export = function(){
      $http.get(SERVER.API + '/bailiff/export', {responseType: 'arraybuffer'}).then(function(success){
        var blob = new Blob([success.data]);
            FileSaver.saveAs(blob, 'export_bailiffs.xls');
      });
    };

    vm.sendImportFile = function(){
      $log.info('Upload called', vm.importFile);
      var formData = new FormData();
      formData.append('file', vm.importFile);
      formData.append('filetype', 'text');
      $http.post(SERVER.API + '/bailiff/import', formData, {
        transformRequest: angular.identity,
        headers: {
          'Content-Type': undefined
        }
      }).then(function () {
        $log.debug('Update successfully submitted');
        toastr.success($translate.instant('bailiff.import.transmitted'));
        vm.importFile = null;
        vm.fetchBailiffs();
      })
      .finally(function () {
        vm.submitted = false;
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

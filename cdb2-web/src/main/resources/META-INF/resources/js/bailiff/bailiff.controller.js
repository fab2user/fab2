(function() {
  'use strict';

  angular.module('cdb2').controller('BailiffController', BailiffController);

  BailiffController.$inject = [
    '$scope',
    '$log',
    '$translate',
    '$uibModal',
    '$http',
    '$interval',
    '$rootScope',
    'lodash',
    'FileSaver',
    'Blob',
    'NgTableParams',
    'BailiffAPIService',
    'toastr',
    'MunicipalityAPIService',
    'SERVER',
    'EVENT',
    'STATUS'
  ];

  function BailiffController(
    $scope,
    $log,
    $translate,
    $uibModal,
    $http,
    $interval,
    $rootScope,
    lodash,
    FileSaver,
    Blob,
    NgTableParams,
    BailiffAPIService,
    toastr,
    MunicipalityAPIService,
    SERVER,
    EVENT,
    STATUS
  ) {
    var vm = this;
    vm.deleted = false; //Flag to indicate if we want to display also soft deleted records. Default is false.
    vm.selectedBailiff = {};

    vm.fetchBailiffs = function() {
      BailiffAPIService.getAll({ deleted: vm.deleted }).$promise.then(function(
        data
      ) {
        vm.total = data.length;
        vm.tableParams = new NgTableParams({}, { dataset: data });
      });
    };

    vm.fetchBailiffs();
    vm.title = $translate.instant('bailiff.list.municipality');

    vm.new = function() {
      loadModal({});
    };

    vm.edit = function(bailiff) {
      vm.selectedBailiff = bailiff;
      loadModal(vm.selectedBailiff);
    };

    vm.delete = function(bailiff) {
      vm.selectedBailiff = bailiff;
      vm.delete();
    };

    // Don't remove : it's used in bailiff edit !
    vm.delete = function() {
      BailiffAPIService.delete({ id: vm.selectedBailiff.id }).$promise.then(
        function() {
          toastr.success($translate.instant('global.toastr.delete.success'));
          vm.fetchBailiffs();
        }
      );
    };

    vm.cancelUpload = function() {
      vm.importFile = null;
    };

    vm.export = function() {
      $http
        .get(SERVER.API + '/bailiff/export', { responseType: 'arraybuffer' })
        .then(function(success) {
          var blob = new Blob([success.data]);
          FileSaver.saveAs(blob, 'export_bailiffs.xls');
        });
    };

    vm.sendImportFile = function() {
      $log.info('Upload called', vm.importFile);
      var formData = new FormData();
      formData.append('file', vm.importFile);
      formData.append('filetype', 'text');
      $http
        .post(SERVER.API + '/bailiff/import', formData, {
          transformRequest: angular.identity,
          headers: {
            'Content-Type': undefined
          }
        })
        .then(function(success) {
          $log.debug('Update successfully submitted');
          // toastr.info($translate.instant('bailiff.import.inprogress'));
          vm.importFile = null;

          $rootScope.$broadcast(EVENT.BAILIFF_IMPORT, success.data);
          // start polling
          vm.startPolling(success.data.id);
        })
        .finally(function() {
          vm.submitted = false;
        });
    };

    vm.polling = undefined;

    vm.startPolling = function(taskCode) {
      if (angular.isDefined(vm.polling)) return;
      vm.polling = $interval(
        function() {
          $http.get(SERVER.API + '/task/' + taskCode).then(function(success) {
            if (success.data.status === STATUS.OK) {
              vm.fetchBailiffs();
            }
            if (
              success.data.status === STATUS.OK ||
              success.data.status === STATUS.ERROR
            ) {
              $rootScope.$broadcast(EVENT.BAILIFF_IMPORT, success.data);
              vm.endPolling();
            }
          });
        },
        10000,
        5
      );
    };

    vm.endPolling = function() {
      if (angular.isDefined(vm.polling)) {
        $interval.cancel(vm.polling);
        vm.polling = undefined;
      }
    };

    vm.resetSearch = function() {
      vm.tableParams.filter({});
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
            if (bailiff.id) {
              return BailiffAPIService.getCompetences({
                id: bailiff.id,
                collectionAction: 'competences'
              });
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

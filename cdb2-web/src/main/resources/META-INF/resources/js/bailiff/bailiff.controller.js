(function () {
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
    '$localForage',
    'lodash',
    'FileSaver',
    'Blob',
    'NgTableParams',
    'BailiffAPIService',
    'toastr',
    'MunicipalityAPIService',
    'InstrumentAPIService',
    'CompetenceForSelectAPIService',
    'GeoAreaAPIService',
    'SettingsAPIService',
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
    $localForage,
    lodash,
    FileSaver,
    Blob,
    NgTableParams,
    BailiffAPIService,
    toastr,
    MunicipalityAPIService,
    InstrumentAPIService,
    CompetenceForSelectAPIService,
    GeoAreaAPIService,
    SettingsAPIService,
    SERVER,
    EVENT,
    STATUS
  ) {
    var vm = this;

    vm.deleted = false; //Flag to indicate if we want to display also soft deleted records. Default is false.
    vm.selectedBailiff = {};

    $rootScope.fabStatus['currentMenu'] = $translate.instant('bailiff.list.title');

    vm.fetchBailiffs = function () {
      BailiffAPIService.getAll({
        deleted: vm.deleted
      }).$promise.then(function (
        data
      ) {
        vm.total = data.length;
        vm.tableParams = new NgTableParams({}, {
          dataset: data
        });
        vm.tableParams.totalDataSet = data.length;   // Add the total resultset size to the table params.
        vm.tableParams.tableTitle = 'List of competent Bailiffs / Enforcement authorities';
      });
    };

    vm.fetchBailiffs();
    vm.title = $translate.instant('bailiff.list.municipality');

    vm.new = function () {
      loadModal({});
    };

    vm.edit = function (bailiff) {
      vm.selectedBailiff = bailiff;
      loadModal(vm.selectedBailiff);
    };

    vm.delete = function (bailiff) {
      vm.selectedBailiff = bailiff;
      vm.delete();
    };

    vm.delete = function () {
      BailiffAPIService.delete({
        id: vm.selectedBailiff.id
      }).$promise.then(
        function () {
          toastr.success($translate.instant('global.toastr.delete.success'));
          vm.fetchBailiffs();
        }
      );
    };

    vm.cancelUpload = function () {
      vm.importFile = null;
    };

    vm.export = function () {
      $http
        .get(SERVER.API + '/bailiff/export', {
          responseType: 'arraybuffer'
        })
        .then(function (success) {
          var blob = new Blob([success.data]);
          FileSaver.saveAs(blob, 'export_bailiffs.xls');
        });
    };

    vm.importTemplate = function () {
      $http
        .get(SERVER.API + '/bailiff/template', {
          responseType: 'arraybuffer'
        })
        .then(function (success) {
          var blob = new Blob([success.data]);
          FileSaver.saveAs(blob, 'bailiffs_import_template.xlsx');
        });
    };

    vm.sendImportFile = function () {
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
        .then(function (success) {
          $log.debug('Update successfully submitted');
          // toastr.info($translate.instant('bailiff.import.inprogress'));
          vm.importFile = null;

          $rootScope.$broadcast(EVENT.BAILIFF_IMPORT, success.data);
          // start polling
          vm.startPolling(success.data.id);
        })
        .finally(function () {
          vm.submitted = false;
        });
    };

    vm.polling = undefined;

    vm.startPolling = function (taskCode) {
      if (angular.isDefined(vm.polling)) return;
      vm.polling = $interval(
        function () {
          $http.get(SERVER.API + '/task/' + taskCode).then(function (success) {
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

    vm.endPolling = function () {
      if (angular.isDefined(vm.polling)) {
        $interval.cancel(vm.polling);
        vm.polling = undefined;
      }
    };

    vm.resetSearch = function () {
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
          nationalIdPrefix: $localForage.getItem('nationalIdPrefix'),
          cities: function () {
            return MunicipalityAPIService.getAll();
          },
          // competences: function() {
          //   //TODO: we can probably remove this in the new competence/instrument m.o.
          //   //Only when edition, not when creation
          //   if (bailiff.id) {
          //     return BailiffAPIService.getCompetences({
          //       id: bailiff.id,
          //       collectionAction: 'competences'
          //     });
          //   }
          // },
          competences: function () {
            return CompetenceForSelectAPIService.getAll();
          },
          areas: function () {
            return GeoAreaAPIService.getAllSimple();
          }
        }
      });

      modalInstance.result.then(function () {
        vm.fetchBailiffs();
      });
    }
  }
})();
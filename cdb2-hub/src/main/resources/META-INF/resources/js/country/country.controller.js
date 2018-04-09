(function() {
  'use strict';

  angular.module('hub').controller('CountryController', CountryController);

  CountryController.$inject = [
    '$log',
    '$interval',
    '$http',
    '$rootScope',
    '$uibModal',
    '$translate',
    'NgTableParams',
    'toastr',
    'CountryAPIService',
    'StatusAPIService',
    'SyncService',
    'EVENT',
    'SERVER',
    'STATUS'
  ];

  function CountryController(
    $log,
    $interval,
    $http,
    $rootScope,
    $uibModal,
    $translate,
    NgTableParams,
    toastr,
    CountryAPIService,
    StatusAPIService,
    SyncService,
    EVENT,
    SERVER,
    STATUS
  ) {
    var vm = this;
    fetchCountries();

    vm.statusDetails = {
      templateUrl: '/js/country/status.details.html',
      title: $translate.instant('status.label.title')
    };

    vm.edit = function(country) {
      var modalInstance = $uibModal.open({
        templateUrl: '/js/country/country.edit.html',
        controller: 'CountryEditController as countryEditCtrl',
        windowClass: 'modal-md',
        backdrop: 'static',
        resolve: {
          country: country
        }
      });
      modalInstance.result.then(function() {
        fetchCountries();
      });
    };

    vm.delete = function(country) {
      CountryAPIService.delete({ id: country.id }).$promise.then(function() {
        toastr.success($translate.instant('global.toastr.delete.success'));
        fetchCountries();
      });
    };

    vm.sync = function(country) {
      $log.info('Sync started');
      country.lastSync = '';
      country.lastSyncStatus = '';
      SyncService.sync(country).then(function(success) {
        success.data.countryName = success.data.country.name; //Copy country name in property expected status directive
        $rootScope.$broadcast(EVENT.XML_EXPORT, success.data);
        // start polling
        vm.startPolling(success.data.id);
      });
    };

    vm.polling = undefined;

    vm.startPolling = function(taskId) {
      if (angular.isDefined(vm.polling)) return;
      vm.polling = $interval(
        function() {
          $http.get(SERVER.API + '/task/' + taskId).then(function(success) {
            // Refresh status on the screen
            $rootScope.$broadcast(EVENT.XML_EXPORT, success.data);
            if (
              success.data.status === STATUS.OK ||
              success.data.status === STATUS.ERROR
            ) {
              vm.endPolling();
            }
          });
        },
        50000,
        5
      );
    };

    vm.endPolling = function() {
      if (angular.isDefined(vm.polling)) {
        $interval.cancel(vm.polling);
        vm.polling = undefined;
        fetchCountries();
      }
    };

    vm.resetSearch = function() {
      vm.tableParams.filter({});
    };

    function fetchCountries() {
      CountryAPIService.getAll().$promise.then(function(success) {
        vm.tableParams = new NgTableParams({}, { dataset: success });
      });
    }
  }
})();

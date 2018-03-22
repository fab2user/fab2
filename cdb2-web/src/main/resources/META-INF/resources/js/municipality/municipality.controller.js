(function() {
  'use strict';

  angular
    .module('cdb2')
    .controller('MunicipalityController', MunicipalityController);

  MunicipalityController.$inject = [
    '$log',
    '$http',
    '$translate',
    'MunicipalityAPIService',
    'toastr',
    'NgTableParams',
    'SERVER'
  ];

  function MunicipalityController(
    $log,
    $http,
    $translate,
    MunicipalityAPIService,
    toastr,
    NgTableParams,
    SERVER
  ) {
    var vm = this;
    vm.searchParams = {};
    $log.debug('Entering municipality controller');
    MunicipalityAPIService.getAll().$promise.then(function(success) {
      $log.debug('Success: ', success);
      vm.municipalities = success;
      vm.tableParams = new NgTableParams({}, { dataset: success });
      vm.total = success.length;
    });

    vm.search = function() {
      $log.debug('search clicked :', vm.searchParams);
    };

    vm.importData = function() {
      $http({ method: 'GET', url: SERVER.API + '/municipality/import' }).then(
        function(success) {
          toastr.success($translate.instant('municipality.import.success'));
        },
        function(error) {
          $log.error('error during import');
          toastr.error($translate.instant('municipality.import.error'));
        }
      );
    };
  }
})();

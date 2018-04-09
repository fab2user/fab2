(function() {
  'use strict';

  angular
    .module('cdb2')
    .controller('MunicipalityController', MunicipalityController);

  MunicipalityController.$inject = [
    '$scope',
    '$log',
    '$http',
    '$translate',
    '$cacheFactory',
    'MunicipalityAPIService',
    'toastr',
    'NgTableParams',
    'SERVER',
    'EVENT'
  ];

  function MunicipalityController(
    $scope,
    $log,
    $http,
    $translate,
    $cacheFactory,
    MunicipalityAPIService,
    toastr,
    NgTableParams,
    SERVER,
    EVENT
  ) {
    var vm = this;
    vm.searchParams = {};
    fetch();

    vm.search = function() {
      $log.debug('search clicked :', vm.searchParams);
    };

    vm.importData = function() {
      $http({ method: 'GET', url: SERVER.API + '/municipality/import' });
    };

    vm.resetSearch = function() {
      vm.tableParams.filter({});
    };

    function fetch() {
      MunicipalityAPIService.getAll().$promise.then(function(success) {
        $log.debug('Success: ', success);
        vm.municipalities = success;
        vm.tableParams = new NgTableParams({}, { dataset: success });
        vm.total = success.length;
      });
    }

    $scope.$on(EVENT.GEONAME_IMPORT, function($event, statusObj) {
      $log.debug('statusObj: ', statusObj);
      if (statusObj.status === 'OK') {
        $cacheFactory.get('geoCache').removeAll();
        fetch();
      }
    });
  }
})();

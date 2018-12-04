(function() {
  'use strict';

  angular
    .module('cdb2')
    .controller('MunicipalityController', MunicipalityController);

  MunicipalityController.$inject = [
    '$scope',
    '$rootScope',
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
    $rootScope,
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

    $rootScope.fabStatus['currentMenu'] = $translate.instant('municipality.list.title');
    
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
       vm.tableParams.totalDataSet = success.length;   // Add the total resultset size to the table params.
        vm.tableParams.tableTitle = 'List of ZIP code';
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

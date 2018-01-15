(function() {
  'use strict';

  angular.module('cdb2').controller('MunicipalityController', MunicipalityController);

  MunicipalityController.$inject = ['$log', 'MunicipalityAPIService'];

  function MunicipalityController($log, MunicipalityAPIService) {
    var vm = this;
    vm.searchParams = {};
    $log.debug('Entering municipality controller');
    MunicipalityAPIService.getAll().$promise.then(function(success) {
      $log.debug('Success: ', success);
      vm.municipalities = success;
    });

    vm.search = function(){
      $log.debug('search clicked');
    }
  };
})();

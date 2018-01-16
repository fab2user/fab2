(function() {
  'use strict';

  angular.module('cdb2').controller('BailiffController', BailiffController);

  BailiffController.$inject = ['$log', '$http', '$translate', 'BailiffAPIService', 'toastr'];

  function BailiffController($log, $http, $translate, BailiffAPIService, toastr) {
    var vm = this;
    vm.searchParams = {};
    BailiffAPIService.getAll().$promise.then(function(data) {
      vm.bailiffs = data;
    });

    vm.search = function() {
      $log.debug('search clicked :', vm.searchParams);
    }
  };
})();

(function() {
  'use strict';

  angular.module('cdb2').controller('BailiffController', BailiffController);

  BailiffController.$inject = ['$log', '$http', '$translate', 'BailiffAPIService', 'toastr'];

  function BailiffController($log, $http, $translate, BailiffAPIService, toastr) {
    var vm = this;
    
    BailiffAPIService.getAll().$promise.then(function(data) {
      vm.bailiffs = data;
    });

    vm.search = function() {
      BailiffAPIService.search(vm.searchParams).$promise.then(function(data) {
        vm.bailiffs = data;
      });
    };
  }
})();

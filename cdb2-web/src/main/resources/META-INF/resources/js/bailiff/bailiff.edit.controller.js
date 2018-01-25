(function() {
  'use strict';

  angular
    .module('cdb2')
    .controller('BailiffEditController', BailiffEditController);

  BailiffEditController.$inject = ['$log','$translate','$uibModalInstance', 'bailiff', 'cities'];

  function BailiffEditController($log,$translate,$uibModalInstance, bailiff, cities) {
    var vm = this;
    vm.modalInstance = $uibModalInstance;
    vm.bailiff = bailiff;
    vm.cities = cities;
    vm.errorsFromServer = null;
  }
})();

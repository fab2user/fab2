(function () {
  'use strict';

  angular
    .module('cdb2')
    .controller('HelpController', HelpController);

  HelpController.$inject = [
    '$log',
    '$translate',
    '$uibModalInstance',
    '$uibModal'  ];

  function HelpController(
    $log,
    $translate,
    $uibModalInstance,
    $uibModal
  ) {
    var vm = this;
    vm.modalInstance = $uibModalInstance;
  }
})();
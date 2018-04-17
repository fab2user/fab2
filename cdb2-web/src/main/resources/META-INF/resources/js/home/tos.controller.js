(function() {
  'use strict';

  angular.module('cdb2').controller('TosController', TosController);

  TosController.$inject = ['$log', '$uibModalInstance', '$cookies'];

  function TosController($log, $uibModalInstance, $cookies) {
    var vm = this;
    vm.modalInstance = $uibModalInstance;
    vm.accept = function() {
      $cookies.put('fab-tos', 'ok');
      vm.modalInstance.close();
    };
  }
})();

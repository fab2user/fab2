(function() {
  'use strict';

  angular.module('cdb2').controller('TosController', TosController);

  TosController.$inject = ['$log', '$uibModalInstance', '$cookies', 'moment'];

  function TosController($log, $uibModalInstance, $cookies, moment) {
    var vm = this;
    vm.modalInstance = $uibModalInstance;
    vm.accept = function() {
      var expireDate = moment('2050-01-01').toDate();
      $cookies.put('fab-tos', 'ok', { expires: expireDate });
      vm.modalInstance.close();
    };
  }
})();

(function() {
  'use strict';

  angular.module('hub').controller('HeadController', HeadController);

  HeadController.$inject = [
    '$scope',
    '$sessionStorage',
    '$log',
    'STORE',
    'EVENT'
  ];

  function HeadController($scope, $sessionStorage, $log, STORE, EVENT) {
    var vm = this;

    loadCurrentUser();

    $scope.$on(EVENT.LOGGED_IN, function() {
      $log.debug('Listener in HeadController called');
      loadCurrentUser();
    });

    function loadCurrentUser() {
      vm.currentUser = $sessionStorage[STORE.USER];
      $log.debug(
        "Current user '" +
          vm.currentUser +
          "' loaded in HeadController from key: " +
          STORE.USER
      );
    }
  }
})();

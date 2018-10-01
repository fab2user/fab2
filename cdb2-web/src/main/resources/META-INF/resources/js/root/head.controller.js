(function () {
  'use strict';

  angular.module('cdb2').controller('HeadController', HeadController);

  HeadController.$inject = [
    '$rootScope',
    '$scope',
    '$log',
    'STORE',
    'EVENT',
    'AuthService'
  ];

  function HeadController(
    $rootScope,
    $scope,
    $log,
    STORE,
    EVENT,
    AuthService
  ) {
    var vm = this;

    $rootScope.$watch('fabStatus', function(newVal, oldVal){
      vm.currentMenu = newVal.currentMenu;
    });

    loadCurrentUser();

    $scope.$on(EVENT.LOGGED_IN, function () {
      $log.debug('Listener in HeadController called');
      loadCurrentUser();
    });

    function loadCurrentUser() {
      vm.currentUser = AuthService.currentUser();
      $log.debug(
        "Current user '" +
        vm.currentUser +
        "' loaded in HeadController from key: " +
        STORE.USER
      );
    }

    // vm.logout = function() {
    //   AuthService.logout();
    // };
  }
})();

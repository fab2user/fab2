(function () {
  'use strict';

  angular.module('cdb2').controller('HeadController', HeadController);

  HeadController.$inject = [
    '$rootScope',
    '$scope',
    '$log',
    '$uibModal',
    'STORE',
    'EVENT',
    'AuthService'
  ];

  function HeadController(
    $rootScope,
    $scope,
    $log,
    $uibModal,
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

     function logout() {
         $log.debug(
                 "Current user '" +
                 vm.currentUser +
                 "' is going to be logged out! "
               );
         AuthService.logout();
     };
     
     vm.showHelp = function() {
         var modalInstance = $uibModal.open({
           templateUrl: '/js/help/help.html',
           windowClass: 'modal-hg',
           backdrop: 'static',
           controller: 'HelpController as helpCtrl',
         });
         modalInstance.result.then(function () {
         });
       }
  }
})();

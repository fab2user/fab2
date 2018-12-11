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
    
    vm.helpPage = $rootScope.helpPage['currentPage'];
    $rootScope.$watch('helpPage', function(newVal, oldVal){
        vm.helpPage = newVal.currentPage;
        if (vm.helpPage) {
            vm.existHelpPage = true;
        }
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
         var helpPage = '/js/help/' + vm.helpPage;
         var modalInstance = $uibModal.open({
           windowClass: 'modal-hg',
           backdrop: 'static',
           controller: 'HelpController as helpCtrl',
           templateUrl: helpPage
         });
         modalInstance.result.then(function () {
         });
       }
  }
})();
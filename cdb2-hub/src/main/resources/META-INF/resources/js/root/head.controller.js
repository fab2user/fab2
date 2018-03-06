(function () {
  'use strict';

  angular
    .module('hub')
    .controller('HeadController', HeadController);

  HeadController.$inject = ['$scope', '$sessionStorage', '$log', 'STORE', 'EVENT'];

  function HeadController($scope, $sessionStorage, $log, STORE, EVENT) {
    var vm = this;

    vm.displayStatusNotif = false;

    loadCurrentUser();

    $scope.$on(EVENT.LOGGED_IN, function(){
      $log.debug('Listener in HeadController called');
      loadCurrentUser();
    });

    $scope.$on(EVENT.XML_EXPORT, function(event, args){
      vm.displayStatusNotif = true;
      vm.statusMessage = args.status;
    });

    function loadCurrentUser(){
        vm.currentUser = $sessionStorage[STORE.USER];
        $log.debug('Current user \'' + vm.currentUser + '\' loaded in HeadController from key: ' + STORE.USER);
    } 
  }

})();
(function () {
  'use strict';

  angular
    .module('cdb2')
    .controller('HeadController', HeadController);

  HeadController.$inject = ['$scope', '$localForage', '$log', 'STORE', 'EVENT'];

  function HeadController($scope, $localForage, $log, STORE, EVENT) {
    var vm = this;

    loadCurrentUser();

    $scope.$on(EVENT.LOGGED_IN, function(){
      $log.debug('Listener in HeadController called');
      loadCurrentUser();
    });

    function loadCurrentUser(){
      $localForage.getItem(STORE.USER).then(function (currentUser) {
        vm.currentUser = currentUser;
        $log.debug('Current user \'' + currentUser + '\' loaded in HeadController from key: ' + STORE.USER);
      });
    } 
  }

})();
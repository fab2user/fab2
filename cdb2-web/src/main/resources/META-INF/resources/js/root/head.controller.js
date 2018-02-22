(function () {
  'use strict';

  angular
    .module('cdb2')
    .controller('HeadController', HeadController);

  HeadController.$inject = ['$scope', '$log', '$localForage', 'STORE', 'EVENT', 'AuthService'];

  function HeadController($scope, $log, $localForage, STORE, EVENT, AuthService) {
    var vm = this;

    $localForage.getItem('country').then(function(data){
      vm.country = data;
    });

    loadCurrentUser();

    $scope.$on(EVENT.LOGGED_IN, function(){
      $log.debug('Listener in HeadController called');
      loadCurrentUser();
    });

    function loadCurrentUser(){
        vm.currentUser = AuthService.currentUser();
        $log.debug('Current user \'' + vm.currentUser + '\' loaded in HeadController from key: ' + STORE.USER);
    } 
  }

})();
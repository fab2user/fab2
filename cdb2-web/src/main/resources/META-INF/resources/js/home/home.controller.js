(function() {
  'use strict';

  angular.module('cdb2').controller('HomeController', HomeController);

  HomeController.$inject = ['$state', '$sessionStorage', 'STORE'];

  function HomeController($state, $sessionStorage, STORE) {
    if ($sessionStorage[STORE.AUTHENTICATED] !== true) {
      $state.go('root.login');
    }
  }
})();

(function() {
  'use strict';

  angular.module('cdb2').controller('HomeController', HomeController);

  HomeController.$inject = ['$state', '$sessionStorage', 'STORE', '$rootScope'];

  function HomeController($state, $sessionStorage, STORE, $rootScope) {
    $rootScope.fabStatus['currentMenu'] = 'What is find a bailiff 2 project ?';
    $rootScope.helpPage['currentPage'] = undefined;
    if ($sessionStorage[STORE.AUTHENTICATED] !== true) {
      $state.go('root.login');
    }
  }
})();

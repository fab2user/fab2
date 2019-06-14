(function() {
  'use strict';

  angular.module('cdb2').controller('MenuController', MenuController);

  MenuController.$inject = [
    '$scope',
    '$log',
    '$localForage',
    'STORE',
    'EVENT',
    'AuthService'
  ];

  function MenuController(
    $scope,
    $log,
    $localForage,
    STORE,
    EVENT,
    AuthService
  ) {
    var vm = this;

 
  }
})();

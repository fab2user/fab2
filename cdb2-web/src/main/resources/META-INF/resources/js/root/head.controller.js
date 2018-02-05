(function () {
  'use strict';

  angular
    .module('cdb2')
    .controller('HeadController', HeadController);

  HeadController.$inject = ['$scope', '$localForage', 'STORE', 'EVENT'];

  function HeadController($scope, $localForage, STORE, EVENT) {
    var vm = this;

    loadCurrentUser();

    $scope.$on(EVENT.LOGGED_IN, function(){
      loadCurrentUser();
    });

    function loadCurrentUser(){
      $localForage.getItem(STORE.USER).then(function (currentUser) {
        vm.currentUser = currentUser;
      });
    } 
  }

})();
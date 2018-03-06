(function () {
  'use strict';

  angular
    .module('hub')
    .controller('StatusController', StatusController);

  StatusController.$inject = [];

  function StatusController() {
    var vm = this;


    vm.popup1 = {
      opened: false
    };

    vm.open1 = function () {
      vm.popup1.opened = true;
    };
  }

})();
(function () {
  'use strict';

  angular
    .module('hub')
    .controller('LoginController', LoginController);

  LoginController.$inject = ['AuthService', 'PreviousState'];

  function LoginController(AuthService, PreviousState) {
    var vm = this;
    vm.credentials = {};

    vm.login = function () {
      AuthService.login(vm.credentials.username, vm.credentials.password, PreviousState);
    };

    vm.logout = function(){
      AuthService.logout();

    };


  }
})();
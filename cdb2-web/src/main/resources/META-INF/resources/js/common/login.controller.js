(function () {
  'use strict';

  angular
    .module('cdb2')
    .controller('LoginController', LoginController);

  LoginController.$inject = ['AuthService'];

  function LoginController(AuthService) {
    var vm = this;
    vm.credentials = {};

    vm.login = function () {

      AuthService.login(vm.credentials.username, vm.credentials.password);
      //FIXME: service should return a promise and then update a var with username, which will be used to set username on right top of screen

    };

    vm.logout = function(){
      AuthService.logout();

    };


  }
})();
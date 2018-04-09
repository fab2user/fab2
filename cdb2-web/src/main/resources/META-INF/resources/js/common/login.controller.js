(function() {
  'use strict';

  angular.module('cdb2').controller('LoginController', LoginController);

  LoginController.$inject = ['$log', 'AuthService', 'PreviousState'];

  function LoginController($log, AuthService, PreviousState) {
    var vm = this;
    vm.credentials = {};

    vm.login = function(valid) {
      if (valid) {
        AuthService.login(
          vm.credentials.username,
          vm.credentials.password,
          PreviousState
        ).catch(function(err) {
          $log.error(err);
          vm.loginForm.password.$setValidity('badCredentials', false);
          return false;
        });
      }
    };

    vm.resetCredentialsError = function() {
      vm.loginForm.password.$setValidity('badCredentials', true);
    };

    vm.logout = function() {
      AuthService.logout();
    };
  }
})();

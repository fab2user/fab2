(function() {
  'use strict';

  angular.module('cdb2').controller('LoginController', LoginController);

  LoginController.$inject = [
    '$log',
    '$translate',
    'AuthService',
    'PreviousState',
    'toastr',
    '$state'
  ];

  function LoginController(
    $log,
    $translate,
    AuthService,
    PreviousState,
    toastr,
    $state
  ) {
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
          if (err.indexOf('TOS') > -1) {
            toastr.error($translate.instant('tos.error'));
          } else {
            vm.loginForm.password.$setValidity('badCredentials', false);
          }
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

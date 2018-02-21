(function () {
  'use strict';

  angular
    .module('cdb2')
    .run(runBlock);

  runBlock.$inject = ['$log', '$rootScope', '$state', 'AuthService'];

  function runBlock($log, $rootScope, $state, AuthService) {

    $rootScope.$on('$stateChangeStart', function (ev, to, toParams, from, fromParams, options) {
      $rootScope.previousState = from.name;
      $rootScope.previousParams = fromParams;
      $log.debug('Previous state:' + $rootScope.previousState);
      if (!AuthService.currentUser()) {
        if (to.name !== 'root.login') {
          ev.preventDefault();
          $state.go('root.login');
        }
      }

    });
  }
})();
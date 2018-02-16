(function () {
  'use strict';

  angular
    .module('cdb2')
    .run(runBlock);

  runBlock.$inject = ['$localForage', '$log', '$rootScope', '$state'];

  function runBlock($localForage, $log, $rootScope, $state) {

    $rootScope.$on('$stateChangeStart', function (ev, to, toParams, from, fromParams, options) {
      $rootScope.previousState = from.name;
      $rootScope.previousParams = fromParams;
      $log.debug('Previous state:' + $rootScope.previousState);
      // TODO: I guess the glimpse of content we have when we click an unauthorized link is caused by the time the localForage promise needs to be resolved. Try using ngSessionStorage.
      $localForage
        .getItem('authenticated')
        .then(function (authenticated) {
          if (!authenticated) {
            ev.preventDefault();
            // $state.go('root.login');
          }
        })
        .catch(function (err) {
          $log.error(err);
          // ev.preventDefault();
          if(to.name !== 'root.login')
          // $state.go('root.login');
          ev.preventDefault();
        });
    });
  }
})();
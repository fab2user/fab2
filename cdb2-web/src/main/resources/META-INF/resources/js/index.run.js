(function () {
    'use strict';

    angular
        .module('cdb2')
        .run(runBlock);

    runBlock.$inject = [ '$localForage', '$log', '$rootScope', '$state'];

    function runBlock( $localForage, $log, $rootScope, $state) {

        $rootScope.$on('$stateChangeStart', function (ev, to, toParams, from, fromParams, options) {
            $rootScope.previousState = from.name;
            $rootScope.previousParams = fromParams;
            $log.debug('Previous state:' + $rootScope.previousState);
            $localForage
            .getItem('authenticated')
            .then(function (authenticated) {
              if (!authenticated) {
                $state.go('root.login');
              }
            })
            .catch(function (err) {
              $log.error(err);
              $state.go('root.login');
            });
        });
    }
})();
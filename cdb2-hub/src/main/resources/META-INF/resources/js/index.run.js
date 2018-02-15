(function () {
    'use strict';

    angular
        .module('hub')
        .run(runBlock);

    runBlock.$inject = ['$sessionStorage', '$transitions', 'STORE'];

    function runBlock($sessionStorage, $transitions, STORE) {

        $transitions.onBefore({}, function (transition) {
            if (transition.to().name === 'root.login') {
                return true;
            }
            if ($sessionStorage[STORE.AUTHENTICATED]) {
                return true;
            } else {
                return transition.router.stateService.target('root.login');
            }

        });
    }
})();
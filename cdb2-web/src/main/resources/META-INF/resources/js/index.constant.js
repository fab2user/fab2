(function () {
    'use strict';

    angular
        .module('cdb2')
        .constant('SERVER', {
            'ROOT': '',
            'API': 'api'
        })
        .constant('STORE', {
            'USER' :'connected_user',
            'AUTHENTICATED': 'authenticated'
        })
        .constant('EVENT', {
            'LOGGED_IN': 1
        });
})();
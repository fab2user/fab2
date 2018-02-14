(function () {
    'use strict';

    angular
        .module('hub')
        .constant('SERVER', {
            'ROOT': 'http://localhost:8181',
            'API': 'http://localhost:8181/api'
        })
        .constant('STORE', {
            'USER' :'connected_user',
            'AUTHENTICATED': 'authenticated'
        })
        .constant('EVENT', {
            'LOGGED_IN': 1
        });
})();
(function () {
  'use strict';

  angular
    .module('hub')
    .constant('SERVER', {
      'ROOT': 'http://localhost:8181',
      'API': 'http://localhost:8181/api'
    })
    .constant('STORE', {
      'USER': 'hub_connected_user',
      'AUTHENTICATED': 'hub_authenticated'
    })
    .constant('EVENT', {
      'LOGGED_IN': 1,
      'XML_IMPORT': 2
    })
    .constant('STATUS', {
      'OK': 'OK',
      'ERROR': 'ERROR'
    });
})();
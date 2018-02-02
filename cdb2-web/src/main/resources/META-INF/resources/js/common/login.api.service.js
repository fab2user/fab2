(function () {
  'use strict';

  angular
    .module('cdb2')
    .factory('LoginAPIService', LoginAPIService);

    LoginAPIService.$inject = ['$resource'];

  function LoginAPIService($resource) {
    return $resource('http://localhost:8080/user', {}, {
      getAll: {
        isArray: true
      },
      search: {
        isArray: true
      }
    });
  }

})();

(function() {
  'use strict';

  angular.module('cdb2').factory('LanguageAPIService', LanguageAPIService);

  LanguageAPIService.$inject = ['$resource'];

  function LanguageAPIService($resource) {
    return $resource('http://localhost:8080/api/language/:id', {}, {
      getAll: {
        isArray: true
      }
    });
  }

})();

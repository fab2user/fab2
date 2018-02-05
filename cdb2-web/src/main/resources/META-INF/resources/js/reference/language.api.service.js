(function() {
  'use strict';

  angular.module('cdb2').factory('LanguageAPIService', LanguageAPIService);

  LanguageAPIService.$inject = ['$resource', 'SERVER'];

  function LanguageAPIService($resource, SERVER) {
    return $resource(SERVER.API + '/language/:id', {}, {
      getAll: {
        isArray: true
      }
    });
  }

})();

(function() {
  'use strict';

  angular.module('cdb2').factory('LangAPIService', LangAPIService);

  LangAPIService.$inject = ['$resource', 'SERVER'];

  function LangAPIService($resource, SERVER) {
    return $resource(SERVER.API + '/language', {}, {
      getAll: {
        isArray: true
      }
    });
  }

})();

(function() {
  'use strict';

  angular.module('hub').factory('ReferenceAPIService', ReferenceAPIService);

  ReferenceAPIService.$inject = ['$resource', 'SERVER'];

  function ReferenceAPIService($resource, SERVER) {
    return $resource(
      SERVER.API + '/reference',
      {},
      {
        get: {}
      }
    );
  }
})();

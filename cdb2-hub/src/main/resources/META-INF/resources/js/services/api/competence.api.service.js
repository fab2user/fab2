(function() {
  'use strict';

  angular.module('hub').factory('CompetenceAPIService', CompetenceAPIService);

  CompetenceAPIService.$inject = ['$resource', 'SERVER'];

  function CompetenceAPIService($resource, SERVER) {
    return $resource(
      SERVER.API + '/reference/:action',
      {},
      {
        get: {}
      }
    );
  }
})();

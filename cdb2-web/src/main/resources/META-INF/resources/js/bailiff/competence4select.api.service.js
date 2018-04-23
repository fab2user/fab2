(function() {
  'use strict';

  angular
    .module('cdb2')
    .factory('CompetenceForSelectAPIService', CompetenceForSelectAPIService);

  CompetenceForSelectAPIService.$inject = ['$resource', 'SERVER'];

  function CompetenceForSelectAPIService($resource, SERVER) {
    return $resource(
      SERVER.API + '/competenceForSelect/:id',
      {},
      {
        getAll: {
          isArray: true
        }
      }
    );
  }
})();

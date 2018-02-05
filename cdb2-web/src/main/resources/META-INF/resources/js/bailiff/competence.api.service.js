(function() {
  'use strict';

  angular.module('cdb2').factory('CompetenceAPIService', CompetenceAPIService);

  CompetenceAPIService.$inject = ['$resource', 'SERVER'];

  function CompetenceAPIService($resource, SERVER) {
    return $resource(SERVER.API + '/competence/:id', {}, {
      getAll: {
        isArray: true
      }
    });
  }

})();

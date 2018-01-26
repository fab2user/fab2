(function() {
  'use strict';

  angular.module('cdb2').factory('CompetenceAPIService', CompetenceAPIService);

  CompetenceAPIService.$inject = ['$resource'];

  function CompetenceAPIService($resource) {
    return $resource('http://localhost:8080/api/competence/:id', {}, {
      getAll: {
        isArray: true
      }
    });
  }

})();

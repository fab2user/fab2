(function() {
  'use strict';

  angular.module('cdb2').factory('BailiffAPIService', BailiffAPIService);

  BailiffAPIService.$inject = ['$resource'];

  function BailiffAPIService($resource) {
    return $resource('http://localhost:8080/api/bailiff/:id/:collectionAction', {}, {
      getAll: {
        isArray: true
      },
      getCompetences: {
        params: {
          id: 'id',
          collectionAction: 'collectionAction'
        },
        isArray: true
      }
    });
  }

})();

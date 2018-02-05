(function() {
  'use strict';

  angular.module('cdb2').factory('BailiffAPIService', BailiffAPIService);

  BailiffAPIService.$inject = ['$resource', 'SERVER'];

  function BailiffAPIService($resource, SERVER) {
    return $resource(SERVER.API + '/bailiff/:id/:collectionAction', {}, {
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

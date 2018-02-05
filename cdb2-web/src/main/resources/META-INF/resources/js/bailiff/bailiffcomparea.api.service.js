(function() {
  'use strict';

  angular.module('cdb2').factory('BailiffCompAreaAPIService', BailiffCompAreaAPIService);

  BailiffCompAreaAPIService.$inject = ['$resource', 'SERVER'];

  function BailiffCompAreaAPIService($resource, SERVER) {
    return $resource(SERVER.API + '/bailiffcomparea/:id', {}, {
      getAll: {
        isArray: true
      },
      getAllForBailiff: {
        isArray :true,
        url: SERVER.API + '/bailiff/:bailiffId/competences'
      }
    });
  }

})();

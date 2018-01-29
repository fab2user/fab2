(function() {
  'use strict';

  angular.module('cdb2').factory('BailiffCompAreaAPIService', BailiffCompAreaAPIService);

  BailiffCompAreaAPIService.$inject = ['$resource'];

  function BailiffCompAreaAPIService($resource) {
    return $resource('http://localhost:8080/api/bailiffcomparea/:id', {}, {
      getAll: {
        isArray: true
      },
      getAllForBailiff: {
        isArray :true,
        url: 'http://localhost:8080/api/bailiff/:bailiffId/competences'
      }
    });
  }

})();

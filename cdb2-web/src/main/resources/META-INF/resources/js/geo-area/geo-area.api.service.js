(function() {
  'use strict';

  angular.module('cdb2').factory('GeoAreaAPIService', GeoAreaAPIService);

  GeoAreaAPIService.$inject = ['$resource', 'SERVER'];

  function GeoAreaAPIService($resource, SERVER) {
    // return $resource('http://localhost:8080/api/geo-area/:id', {id: '@id'}, {
    return $resource(
      SERVER.API + '/geo-area/:id',
      {},
      {
        getAll: {
          isArray: true
        },
        getAllSimple: {
          isArray: true,
          url: SERVER.API + '/geo-area/simple'
        },
        search: {
          isArray: true
        }
      }
    );
  }
})();

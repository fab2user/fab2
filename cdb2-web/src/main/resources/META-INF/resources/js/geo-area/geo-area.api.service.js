(function () {
  'use strict';

  angular
    .module('cdb2')
    .factory('GeoAreaAPIService', GeoAreaAPIService);

  GeoAreaAPIService.$inject = ['$resource'];

  function GeoAreaAPIService($resource) {
    return $resource('http://localhost:8080/api/geo-area/:id', {}, {
      getAll: {
        isArray: true
      },
      search: {
        isArray: true
      }
    });
  }

})();

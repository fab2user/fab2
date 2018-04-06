(function() {
  'use strict';

  angular
    .module('cdb2')
    .factory('MunicipalityAPIService', MunicipalityAPIService);

  MunicipalityAPIService.$inject = ['$resource', '$cacheFactory', 'SERVER'];

  function MunicipalityAPIService($resource, $cacheFactory, SERVER) {
    return $resource(
      SERVER.API + '/municipality/:id',
      {},
      {
        getAll: {
          isArray: true,
          cache: $cacheFactory.get('geoCache')
        },
        search: {
          isArray: true
        },
        updateDB: {
          url: SERVER.API + '/municipality/update',
          method: 'POST',
          isArray: true
        }
      }
    );
  }
})();

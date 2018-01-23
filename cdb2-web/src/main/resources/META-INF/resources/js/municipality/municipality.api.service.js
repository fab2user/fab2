(function () {
  'use strict';

  angular
    .module('cdb2')
    .factory('MunicipalityAPIService', MunicipalityAPIService);

  MunicipalityAPIService.$inject = ['$resource'];

  function MunicipalityAPIService($resource) {
    return $resource('http://localhost:8080/api/municipality/:id', {}, {
      getAll: {
        isArray: true,
        cache: true //TODO: Check if localforage would be of any value here
      },
      search: {
        isArray: true
      }
    });
  }

})();

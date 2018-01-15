(function () {
  'use strict';

  angular
    .module('cdb2')
    .factory('MunicipalityAPIService', MunicipalityAPIService);

  MunicipalityAPIService.$inject = ['$resource'];

  function MunicipalityAPIService($resource) {
    return $resource('http://localhost:8080/api/municipality/:id', {}, {
      getAll: {
        isArray: true
      },
      search: {
        isArray: true
      }
    });
  }

})();

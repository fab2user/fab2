(function() {
  'use strict';

  angular.module('cdb2').factory('InstrumentAPIService', InstrumentAPIService);

  InstrumentAPIService.$inject = ['$resource'];

  function InstrumentAPIService($resource) {
    return $resource('http://localhost:8080/api/instrument/:id', {}, {
      getAll: {
        isArray: true
      }
    });
  }

})();

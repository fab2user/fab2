(function() {
  'use strict';

  angular.module('cdb2').factory('ReferenceAPIService', ReferenceAPIService);

  ReferenceAPIService.$inject = ['$resource'];

  function ReferenceAPIService($resource) {
    return $resource('http://localhost:8080/api/competence/:id', {}, {
      getAllCompetence: {
        isArray: true
      },
      getAllInstrument: {
        url:'http://localhost:8080/api/instrument',
        isArray: true
      },
      getAllLanguage: {
        url:'http://localhost:8080/api/language',
        isArray: true
      }
    });
  }

})();

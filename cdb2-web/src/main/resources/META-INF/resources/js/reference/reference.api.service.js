(function() {
  'use strict';

  angular.module('cdb2').factory('ReferenceAPIService', ReferenceAPIService);

  ReferenceAPIService.$inject = ['$resource', 'SERVER'];

  function ReferenceAPIService($resource, SERVER) {
    return $resource(SERVER.API + '/competence/:id', {}, {
      getAllCompetence: {
        isArray: true
      },
      getAllInstrument: {
        url: SERVER.API + '/instrument',
        isArray: true
      },
      getAllLanguage: {
        url: SERVER.API + '/language',
        isArray: true
      }
    });
  }

})();

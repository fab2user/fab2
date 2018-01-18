(function() {
  'use strict';

  angular.module('cdb2').controller('ReferenceController', ReferenceController);

  ReferenceController.$inject = ['$log', '$http', '$translate', 'ReferenceAPIService', 'toastr'];

  function ReferenceController($log, $http, $translate, ReferenceAPIService, toastr) {
    var vm = this;
    ReferenceAPIService.getAllCompetence().$promise.then(function(data) {
      vm.competences = data;
    });
    ReferenceAPIService.getAllInstrument().$promise.then(function(data) {
      vm.instruments = data;
    });
    ReferenceAPIService.getAllLanguage().$promise.then(function(data) {
      vm.languages = data;
    });
  }
})();

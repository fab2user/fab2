(function() {
  'use strict';

  angular.module('cdb2').controller('BailiffEditController', BailiffEditController);

  BailiffEditController.$inject = [
    '$log',
    '$translate',
    '$uibModalInstance',
    'toastr',
    'BailiffAPIService',
    'bailiff',
    'cities',
    'competences'
  ];

  function BailiffEditController($log, $translate, $uibModalInstance, toastr, BailiffAPIService, bailiff, cities, competences) {
    var vm = this;
    vm.modalInstance = $uibModalInstance;
    vm.bailiff = bailiff;
    vm.competences = competences;
    // Only purpose of initialCity is to display correct municipality in angucomplete when editing existing bailiff
    vm.bailiff.initialCity = {
      postalCode: vm.bailiff.postalCode,
      name: vm.bailiff.city
    };
    vm.cities = cities;
    vm.errorsFromServer = null;

    vm.save = function(isValid) {
      vm.errorsFromServer = null;
      if (isValid) {
        vm.submitted = true;
        var serializedBailiff = serializeBailiff();

        BailiffAPIService.save({}, serializedBailiff).$promise.then(function() {
          $uibModalInstance.close('save');
          toastr.success($translate.instant('global.toastr.save.success'));
        }).catch(function(err) {
          $log.error(err);
          vm.errorsFromServer = $translate.instant(err.data.message);
        }). finally(function() {
          vm.submitted = false;
        });
      }
    };

    function serializeBailiff() {
      var bailiff = Object.assign({}, vm.bailiff);
      if (vm.bailiff.municipality.originalObject.id) {
        bailiff.municipalityId = vm.bailiff.municipality.originalObject.id;
      }
      bailiff.municipality = vm.bailiff.municipality.originalObject.name;
      return bailiff;
    }
  }
})();

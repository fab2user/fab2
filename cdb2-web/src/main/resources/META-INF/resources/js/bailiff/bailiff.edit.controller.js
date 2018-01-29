(function() {
  'use strict';

  angular.module('cdb2').controller('BailiffEditController', BailiffEditController);

  BailiffEditController.$inject = [
    '$log',
    '$translate',
    '$uibModalInstance',
    '$uibModal',
    'toastr',
    'NgTableParams',
    'BailiffAPIService',
    'BailiffCompAreaAPIService',
    'bailiff',
    'cities',
    'competences'
  ];

  function BailiffEditController($log, $translate, $uibModalInstance, $uibModal, toastr, NgTableParams, BailiffAPIService, BailiffCompAreaAPIService, bailiff, cities, competences) {
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

    loadBailiffCompArea();

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

    function loadBailiffCompArea(){
      BailiffCompAreaAPIService.getAllForBailiff({bailiffId: vm.bailiff.id})
      .$promise
      .then(function(success){
        vm.tableParams = new NgTableParams({}, {dataset: success});
      });
    }

    vm.addCompetence = function(){
      var modalInstance = $uibModal.open({
        templateUrl: '/js/bailiff/competence.edit.html',
        controller: 'CompetenceEditController as competenceEditCtrl',
        windowClass: 'modal-hg',
        backdrop: 'static',
        resolve: {
          bailiff: vm.bailiff,
          competence: {areas:[]}
        }
      });
    };

  }
})();

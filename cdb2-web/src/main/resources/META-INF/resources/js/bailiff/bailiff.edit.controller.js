(function () {
  'use strict';

  angular.module('cdb2').controller('BailiffEditController', BailiffEditController);

  BailiffEditController.$inject = [
    '$log',
    '$translate',
    '$uibModalInstance',
    '$uibModal',
    'lodash',
    'toastr',
    'NgTableParams',
    'BailiffAPIService',
    'BailiffCompAreaAPIService',
    'bailiff',
    'cities',
    'competences'
  ];

  function BailiffEditController($log, $translate, $uibModalInstance, $uibModal, lodash, toastr, NgTableParams, BailiffAPIService, BailiffCompAreaAPIService, bailiff, cities, competences) {
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

    if (vm.bailiff.id) {
      loadBailiffCompArea();
    }

    vm.save = function (isValid) {
      vm.errorsFromServer = null;
      if (isValid) {
        vm.submitted = true;
        var serializedBailiff = serializeBailiff();

        BailiffAPIService.save({}, serializedBailiff).$promise.then(function () {
          toastr.success($translate.instant('global.toastr.save.success'));
        }).catch(function (err) {
          $log.error(err);
          vm.errorsFromServer = $translate.instant(err.data.message);
        }).finally(function () {
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

    function loadBailiffCompArea() {
      BailiffCompAreaAPIService.getAllForBailiff({
        bailiffId: vm.bailiff.id
      }).$promise.then(function (success) {
        //Build area names list, to be displayed in smart table
        var model = success;
        model = buildAreasList(model);
        vm.tableParams = new NgTableParams({}, {
          dataset: model
        });
      });
    }

    vm.addCompetence = function () {
      newEdit({
        areas: []
      });
    };

    vm.editCompetence = function () {
      newEdit(vm.selectedCompetence);
    };

    vm.removeCompetence = function () {
      vm.errorsFromServer = null;
      vm.submitted = true;

      BailiffCompAreaAPIService.delete({
        id: vm.selectedCompetence.id
      }).$promise.then(function () {
        $uibModalInstance.close('delete');
        toastr.success($translate.instant('global.toastr.delete.success'));
      }).catch(function (err) {
        $log.error(err);
        vm.errorsFromServer = $translate.instant(err.data.message);
      }).finally(function () {
        vm.submitted = false;
      });
    };

    function buildAreasList(model) {
      return lodash.map(model, function (record) {
        var areaNames = lodash.map(record.areas, 'name');
        record.areaNames = areaNames.join(', ');
        return record;
      });
    }

    function newEdit(competence) {
      var modalInstance = $uibModal.open({
        templateUrl: '/js/bailiff/competence.edit.html',
        controller: 'CompetenceEditController as competenceEditCtrl',
        windowClass: 'modal-hg',
        backdrop: 'static',
        resolve: {
          bailiff: vm.bailiff,
          bailiffCompArea: competence
        }
      });

      modalInstance.result.then(function () {
        loadBailiffCompArea();
      });
    }

  }
})();
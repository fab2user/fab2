(function() {
  'use strict';

  angular.module('cdb2').controller('CompetenceEditController', CompetenceEditController);

  CompetenceEditController.$inject = [
    '$log',
    '$translate',
    '$uibModalInstance',
    '$uibModal',
    'lodash',
    'NgTableParams',
    'toastr',
    'InstrumentAPIService',
    'CompetenceAPIService',
    'GeoAreaAPIService',
    'BailiffCompAreaAPIService',
    'bailiff',
    'bailiffCompArea'
  ];

  function CompetenceEditController($log, $translate, $uibModalInstance, $uibModal, lodash, NgTableParams, toastr, InstrumentAPIService, CompetenceAPIService, GeoAreaAPIService, BailiffCompAreaAPIService, bailiff, bailiffCompArea) {
    var vm = this;
    vm.modalInstance = $uibModalInstance;
    vm.bailiff = bailiff;
    vm.bailiffCompArea = bailiffCompArea;
    vm.areasToDisplay = bailiffCompArea.areas.slice() || [];
    vm.tableParamsEdit = new NgTableParams({}, {dataset: vm.areasToDisplay});
    vm.selectedForRemoval = [];
    vm.selectedForAddition = [];

    if (vm.bailiffCompArea.competence) {
      vm.instrument = vm.bailiffCompArea.competence.instrument;

    }
    InstrumentAPIService.getAll().$promise.then(function(data) {
      vm.instruments = data;
    });

    CompetenceAPIService.getAll().$promise.then(function(data) {
      vm.allCompetences = data;
      loadCompetences();
      vm.competence = vm.bailiffCompArea.competence;
    });

    function loadCompetences() {
      $log.info('loadCompetences called');

      vm.competences = lodash.filter(vm.allCompetences, function(competence) {
        return competence.instrument.id === vm.instrument.id;
      });
    }

    vm.reloadCompetences = function() {
      loadCompetences();
    };

    GeoAreaAPIService.getAll().$promise.then(function(success) {
      vm.areas = success;
      vm.tableParams = new NgTableParams({}, {dataset: success});
    });

    vm.toggleRemoval = function(areaId) {
      var position = vm.selectedForRemoval.indexOf(areaId);
      if (position < 0) {
        vm.selectedForRemoval.push(areaId);
      } else {
        vm.selectedForRemoval.splice(position, 1);
      }
    };

    vm.toggleAddition = function(area) {
      var position = vm.selectedForAddition.indexOf(area);
      if (position < 0) {
        vm.selectedForAddition.push(area);
      } else {
        vm.selectedForAddition.splice(position, 1);
      }
    };

    vm.addSelected = function() {
      addAreasToDisplay(vm.selectedForAddition);
      vm.tableParamsEdit.reload();
      vm.selectedForAddition = [];
    };

    vm.removeSelected = function() {
      removeAreasToDisplay(vm.selectedForRemoval);
      vm.tableParamsEdit.reload();
      vm.selectedForRemoval = [];
    };

    function addAreasToDisplay(areas) {
      areas.forEach(function(area) {
        if (!lodash.find(vm.areasToDisplay, {'id': area.id})) {
          vm.areasToDisplay.push(area);
        }
      });
    }

    function removeAreasToDisplay(areas) {
      var idsToRemove = lodash.map(areas, 'id');
      lodash.remove(vm.areasToDisplay, function(areaToDisplay) {
        return idsToRemove.indexOf(areaToDisplay.id) > -1;
      });
    }

    vm.addFiltered = function() {
      // TODO Add confirm
      $log.info('Filtered areas: ', vm.tableParams);
      vm.tableParams.data.forEach(function(area) {
        vm.toggleAddition(area);
      });
      vm.addSelected();
    };

    vm.removeFiltered = function() {
      vm.tableParamsEdit.data.forEach(function(area) {
        vm.toggleRemoval(area);
      });
      vm.removeSelected();
    };

    vm.save = function(valid) {
      if (vm.areasToDisplay.length < 1) {
        toastr.error('Error', 'At least one geo area must be selected !');
        return;
      }
      if (valid) {
        var dto = buildDTO();
        BailiffCompAreaAPIService.save({}, dto).$promise.then(function() {
          $uibModalInstance.close();
          toastr.success($translate.instant("global.toastr.save.success"));
        }).catch(function(err) {
          $log.error(err);
          vm.errorsFromServer = $translate.instant(err.data.message);
        }). finally(function() {
          vm.submitted = false;
        });
      }
    };

    function buildDTO() {
      var areas = [];
      vm.areasToDisplay.forEach(function(area) {
        areas.push({id: area.id});
      });
      return {
        id: vm.bailiffCompArea.id,
        bailiff: vm.bailiff,
        instrument: {
          id: vm.instrument.id
        },
        competence: {
          id: vm.competence.id
        },
        areas: areas
      };

    }

  }
})();
